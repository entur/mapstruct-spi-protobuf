package io.github.dnalchemist.mapstruct.spi.protobuf;

/*-
 * #%L
 * protobuf-spi-impl
 * %%
 * Copyright (C) 2019 - 2020 Entur
 * %%
 * Licensed under the EUPL, Version 1.1 or – as soon they will be
 * approved by the European Commission - subsequent versions of the
 * EUPL (the "Licence");
 * 
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl5
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 * #L%
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.MappingConstants;
import org.mapstruct.ap.spi.DefaultEnumMappingStrategy;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableMap;

public class ProtobufEnumMappingStrategy extends DefaultEnumMappingStrategy {

	private static final String DEFAULT_ENUM_POSTFIX = "UNSPECIFIED";
	private static final String UNPARSABLE_ENUM_CONSTANT_UNRECOGNIZED = "UNRECOGNIZED";
	private static final String PROTOBUF_ENUM_INTERFACE = "com.google.protobuf.ProtocolMessageEnum";
	private static final String PROTOBUF_LITE_ENUM_INTERFACE = "com.google.protobuf.Internal.EnumLite";
	private static final HashMap<TypeElement, Boolean> KNOWN_ENUMS = new HashMap<>();

	private Map<String, String> enumPostfixOverrides = null;

	/**
	 * The enum constant postfix used as default value in protobuf, ie for enum "Cake" the default constant should be CAKE_UNSPECIFIED = 0; This is the
	 * recommended style according to Googles style guide https://developers.google.com/protocol-buffers/docs/style#enums. If you use some other pattern in your
	 * protobuf files you can pass in "mapstructSpi.enumPostfixOverrides" as a compilerArg.
	 */
	private String getEnumPostfix(TypeElement enumType) {
		if (enumPostfixOverrides == null) {
			initEnumPostfixOverrides();
		}

		String enumTypeName = enumType.getQualifiedName().toString();
		Optional<String> override = enumPostfixOverrides.keySet().stream().filter(enumTypeName::startsWith).map(enumPostfixOverrides::get).findAny();

		return override.orElse(DEFAULT_ENUM_POSTFIX);
	}

	private void initEnumPostfixOverrides() {
		if (ProcessingEnvOptionsHolder.containsKey(ProcessingEnvOptionsHolder.ENUM_POSTFIX_OVERRIDES)) {
			String[] postfixOverrides = ProcessingEnvOptionsHolder.getOption(ProcessingEnvOptionsHolder.ENUM_POSTFIX_OVERRIDES).split(",");

			enumPostfixOverrides = Arrays.stream(postfixOverrides)
					.map(override -> override.split("=", 2))
					.collect(Collectors.toMap(args -> args[0].trim(), args -> args[1].trim()));
		} else {
			enumPostfixOverrides = ImmutableMap.of();
		}
	}

	// @Override
	public boolean isMapEnumConstantToNull(TypeElement enumType, String sourceEnumValue) {
		if (isProtobufEnum(enumType)) {
			if (UNPARSABLE_ENUM_CONSTANT_UNRECOGNIZED.equals(sourceEnumValue)) {
				return true;
			}

			String trimmedEnumValue = removeEnumNamePrefixFromConstant(enumType, sourceEnumValue);
			if (getEnumPostfix(enumType).equals(trimmedEnumValue)) {
				return true;
			}

		}
		return false;
	}

	@Override
	public String getDefaultNullEnumConstant(TypeElement enumType) {
		boolean isProtobufEnum = isProtobufEnum(enumType);

		if (isProtobufEnum) {
			return addEnumNamePrefixToConstant(enumType, getEnumPostfix(enumType));
		} else {
			return null;
		}

	}

	@Override
	public String getEnumConstant(TypeElement enumType, String sourceEnumValue) {
		boolean isProtobufEnum = isProtobufEnum(enumType);

		if (isProtobufEnum) {
			if (isMapEnumConstantToNull(enumType, sourceEnumValue)) {
				return MappingConstants.NULL;
			} else if (sourceEnumValue == null) {
				return getDefaultNullEnumConstant(enumType);
			}

			return removeEnumNamePrefixFromConstant(enumType, sourceEnumValue);
		} else {
			return sourceEnumValue;

		}
	}

	private String addEnumNamePrefixToConstant(TypeElement enumType, String constant) {
		String enumName = enumType.getSimpleName().toString();
		String prefix = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, enumName);

		String constructedValue = String.format("%s_%s", prefix, constant);
		return constructedValue;
	}

	private String removeEnumNamePrefixFromConstant(TypeElement enumType, String sourceEnumValue) {
		String enumName = enumType.getSimpleName().toString();
		String prefix = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, enumName);

		String trimmedValue = sourceEnumValue.replace(prefix + "_", "");
		return trimmedValue;
	}

	private boolean isProtobufEnum(TypeElement enumType) {
		Boolean isProtobufEnum = KNOWN_ENUMS.get(enumType);
		if (isProtobufEnum == null) {
			List<? extends TypeMirror> interfaces = enumType.getInterfaces();
			isProtobufEnum = Boolean.FALSE;
			for (TypeMirror implementedInterface : interfaces) {
				String implementedInterfaceName = implementedInterface.toString();
				if (PROTOBUF_ENUM_INTERFACE.equals(implementedInterfaceName) || PROTOBUF_LITE_ENUM_INTERFACE.equals(implementedInterfaceName)) {
					isProtobufEnum = Boolean.TRUE;
					break;
				}
			}

			KNOWN_ENUMS.put(enumType, isProtobufEnum);
		}

		return isProtobufEnum;
	}
}

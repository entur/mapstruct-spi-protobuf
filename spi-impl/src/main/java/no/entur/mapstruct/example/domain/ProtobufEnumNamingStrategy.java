package no.entur.mapstruct.example.domain;

import java.util.HashMap;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.MappingConstants;
import org.mapstruct.ap.spi.EnumNamingStrategy;

import com.google.common.base.CaseFormat;

public class ProtobufEnumNamingStrategy implements EnumNamingStrategy {

	protected static final String DEFAULT_ENUM_CONSTANT;
	private static final String PROTOBUF_ENUM_INTERFACE = "com.google.protobuf.ProtocolMessageEnum";
	private static final String PROTOBUF_LITE_ENUM_INTERFACE = "com.google.protobuf.Internal.EnumLite";
	private static final HashMap<TypeElement, Boolean> knownEnums = new HashMap<>();

	static {
		DEFAULT_ENUM_CONSTANT = getDefaultEnumConstant();
	}

	/**
	 * The enum constant postfix used as default value in protobuf, ie for enum "Cake" the default constant should be CAKE_UNSPECIFIED = 0; This is the
	 * recommended style according to Googles style guide https://developers.google.com/protocol-buffers/docs/style#enums . If you use some other pattern in
	 * your protobuf files you can simply subclass this class and override this method.
	 */
	protected static String getDefaultEnumConstant() {
		return "UNSPECIFIED";
	}

	// @Override
	public boolean isMapEnumConstantToNull(TypeElement enumType, String sourceEnumValue) {
		if (isProtobufEnum(enumType)) {
			if ("UNRECOGNIZED".equals(sourceEnumValue)) {
				return true;
			}

			String trimmedEnumValue = removeEnumNamePrefixFromConstant(enumType, sourceEnumValue);
			if (DEFAULT_ENUM_CONSTANT.equals(trimmedEnumValue)) {
				return true;
			}

		}
		return false;
	}

	// @Override
	public String getDefaultEnumConstant(TypeElement enumType) {
		boolean isProtobufEnum = isProtobufEnum(enumType);

		if (isProtobufEnum) {
			return addEnumNamePrefixToConstant(enumType, DEFAULT_ENUM_CONSTANT);
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
				return getDefaultEnumConstant(enumType);
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
		Boolean isProtobufEnum = knownEnums.get(enumType);
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

			knownEnums.put(enumType, isProtobufEnum);
		}

		return isProtobufEnum;
	}
}

package no.entur.mapstruct.example.protobuf;

import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.spi.DefaultEnumValueMappingStrategy;
import org.mapstruct.ap.spi.MapStructProcessingEnvironment;

import com.google.common.base.CaseFormat;

public class ProtobufEnumValueMappingStrategy extends DefaultEnumValueMappingStrategy {

	private static final String PROTOBUF_ENUM_INTERFACE = "com.google.protobuf.ProtocolMessageEnum";

	@Override
	public void init(MapStructProcessingEnvironment processingEnvironment) {
		super.init(processingEnvironment);
	}

	@Override
	public String getEnumValue(TypeElement enumType, String sourceEnumValue) {
		List<? extends TypeMirror> interfaces = enumType.getInterfaces();
		boolean isProtobufEnum = false;
		for (TypeMirror implementedInterface : interfaces) {
			if (PROTOBUF_ENUM_INTERFACE.equals(implementedInterface.toString())) {
				isProtobufEnum = true;
				break;
			}
		}

		if (isProtobufEnum) {
			if ("UNRECOGNIZED".equals(sourceEnumValue)) {
				return null;
			} else {
				String enumName = enumType.getSimpleName().toString();
				String prefix = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, enumName);

				String trimmedValue = sourceEnumValue.replace(prefix + "_", "");
				if ("UNSPECIFIED".equals(trimmedValue)) {
					//
					return null;
				} else {
					return trimmedValue;
				}
			}
		} else {
			return sourceEnumValue;

		}
	}
}

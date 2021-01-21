package no.entur.mapstruct.spi.protobuf;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.util.Nouns;
import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy;
import org.mapstruct.ap.spi.MapStructProcessingEnvironment;
import org.mapstruct.ap.spi.util.IntrospectorUtils;

/**
 * @author Arne Seime
 */
public class ProtobufAccessorNamingStrategy extends DefaultAccessorNamingStrategy {
	public static final String PROTOBUF_STRING_LIST_TYPE = "com.google.protobuf.ProtocolStringList";
	public static final String PROTOBUF_MESSAGE_OR_BUILDER = "com.google.protobuf.MessageLiteOrBuilder";
	public static final String PROTOBUF_GENERATED_MESSAGE = "com.google.protobuf.Message";
	public static final String BUILDER_LIST_SUFFIX = "BuilderList";

	protected static final Set<String> INTERNAL_METHODS = new HashSet<>(Arrays.asList("newBuilder", "newBuilderForType", "parseFrom", "parseDelimitedFrom",
			"getDefaultInstance", "getDescriptor", "getDescriptorForType", "getDefaultInstanceForType", "clear", "clearField", "clearOneof", "mergeFrom",
			"setRepeatedField", "setUnknownFields", "getSerializedSize", "getAllFields", "getAllFieldsMutable", "getAllFieldsRaw", "getDescriptorForType",
			"getField", "getFieldRaw", "getOneofFieldDescriptor", "getParserForType", "getRepeatedField", "getRepeatedFieldCount", "getUnknownFields",
			"getInitializationErrorString", "getMemoizedSerializedSize", "getOneofFieldDescriptor", "getSerializedSize", "getMemoizedSerializedSize",
			"getSerializingExceptionMessage", "isInitialized", "mergeUnknownFields"));

	protected static final List<String> INTERNAL_SPECIAL_METHOD_ENDINGS = Arrays.asList("Value", "Count", "Bytes", "Map", "ValueList");

	protected static final List<String> INTERNAL_SPECIAL_METHOD_BEGINNINGS = Arrays.asList("remove", "clear", "mutable", "merge", "putAll", "getMutable");

	protected TypeMirror protobufMesageOrBuilderType;

	@Override
	public void init(MapStructProcessingEnvironment processingEnvironment) {
		super.init(processingEnvironment);

		TypeElement typeElement = elementUtils.getTypeElement(PROTOBUF_MESSAGE_OR_BUILDER);
		if (typeElement != null) {
			protobufMesageOrBuilderType = typeElement.asType();
		}
	}

	private boolean isSpecialMethod(ExecutableElement method) {
		if (!isMethodFromProtobufGeneratedClass(method)) {
			return false;
		}
		String methodName = method.getSimpleName().toString();

		for (String checkMethod : INTERNAL_SPECIAL_METHOD_ENDINGS) {
			if (methodName.endsWith(checkMethod)) {
				String propertyMethod = methodName.substring(0, methodName.length() - checkMethod.length());
				boolean propertyMethodExists = method.getEnclosingElement()
						.getEnclosedElements()
						.stream()
						.anyMatch(e -> e.getSimpleName().toString().equals(propertyMethod));
				if (propertyMethodExists) {
					return true;
				}
			}
		}

		for (String checkMethod : INTERNAL_SPECIAL_METHOD_BEGINNINGS) {
			if (methodName.startsWith(checkMethod)) {
				String propertyMethod = "get" + methodName.substring(checkMethod.length());

				boolean propertyMethodExists = method.getEnclosingElement()
						.getEnclosedElements()
						.stream()
						.anyMatch(e -> (e).getSimpleName().toString().equals(propertyMethod));
				if (propertyMethodExists) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean isGetterMethod(ExecutableElement method) {
		String methodName = method.getSimpleName().toString();

//        if (methodName.endsWith("Builder")) {
//            return false;
//        }

		if (methodName.endsWith("OrBuilder")) {
			return false;
		}

		if (methodName.endsWith("OrBuilderList")) {
			return false;
		}

		if (methodName.endsWith(BUILDER_LIST_SUFFIX)) {
			return false;
		}

		if (INTERNAL_METHODS.contains(methodName)) {
			return false;
		} else {
			if (isSpecialMethod(method)) {
				return false;
			}

			return super.isGetterMethod(method);
		}

	}

	@Override
	public boolean isSetterMethod(ExecutableElement method) {
		String methodName = method.getSimpleName().toString();

		if (INTERNAL_METHODS.contains(methodName)) {
			return false;
		} else {
			if (isSpecialMethod(method)) {
				return false;
			}
			return super.isSetterMethod(method);
		}
	}

	@Override
	protected boolean isFluentSetter(ExecutableElement method) {
		String methodName = method.getSimpleName().toString();

		if (INTERNAL_METHODS.contains(methodName)) {
			return false;
		} else if (methodName.startsWith("get")) {
			// Protobuf fluent setters should always start with set, if it starts with get it is probably a getter
			// for a builder in a list field with recursive references (param being index).
			// For instance UserDTO.getUsersBuilder(idx) in provided test case
			return false;
		} else {
			return super.isFluentSetter(method);
		}
	}

	@Override
	public boolean isAdderMethod(ExecutableElement method) {
		String methodName = method.getSimpleName().toString();

		if (INTERNAL_METHODS.contains(methodName)) {
			return false;
		} else {
			return super.isAdderMethod(method);
		}
	}

	@Override
	public boolean isPresenceCheckMethod(ExecutableElement method) {
		String methodName = method.getSimpleName().toString();

		if (INTERNAL_METHODS.contains(methodName)) {
			return false;
		} else {
			return super.isPresenceCheckMethod(method);
		}
	}

	@Override
	public String getElementName(ExecutableElement adderMethod) {

		String methodName = super.getElementName(adderMethod);
		if (protobufMesageOrBuilderType != null && isMethodFromProtobufGeneratedClass(adderMethod)) {
			String singularizedMethodName = Nouns.singularize(methodName);
			methodName = singularizedMethodName;
		}

		return methodName;
	}

	@Override
	public String getPropertyName(ExecutableElement getterOrSetterMethod) {

		String methodName = getterOrSetterMethod.getSimpleName().toString();
		if (isGetList(getterOrSetterMethod) || isSetList(getterOrSetterMethod)) {
			Element receiver = getterOrSetterMethod.getEnclosingElement();
			if (receiver != null && (receiver.getKind() == ElementKind.CLASS || receiver.getKind() == ElementKind.INTERFACE)) {
				TypeElement type = (TypeElement) receiver;
				if (isProtobufGeneratedMessage(type)) {
					String propertyName = IntrospectorUtils.decapitalize(methodName.substring(3, methodName.length() - 4));
					return propertyName;
				}
			}
		}
		return super.getPropertyName(getterOrSetterMethod);
	}

	private boolean isGetList(ExecutableElement element) {
		return element.getSimpleName().toString().startsWith("get") && isListType(element.getReturnType());
	}

	private boolean isSetList(ExecutableElement element) {
		if (element.getSimpleName().toString().startsWith("set") && element.getParameters().size() == 1) {
			TypeMirror param = element.getParameters().get(0).asType();
			return isListType(param);
		}
		return false;
	}

	private boolean isListType(TypeMirror t) {
		return t.toString().startsWith(List.class.getCanonicalName()) || t.toString().startsWith(PROTOBUF_STRING_LIST_TYPE);
	}

	private boolean isProtobufGeneratedMessage(TypeElement type) {
		List<? extends TypeMirror> interfaces = type.getInterfaces();

		if (interfaces != null) {
			for (TypeMirror implementedInterface : interfaces) {
				if (implementedInterface.toString().startsWith(PROTOBUF_MESSAGE_OR_BUILDER)) {
					return true;
				} else if (implementedInterface instanceof DeclaredType) {
					DeclaredType declared = (DeclaredType) implementedInterface;
					Element supertypeElement = declared.asElement();
					if (isProtobufGeneratedMessage((TypeElement) supertypeElement)) {
						return true;
					}
				}
			}
		}

		TypeMirror superType = type.getSuperclass();
		if (superType instanceof DeclaredType) {
			DeclaredType declared = (DeclaredType) superType;
			Element supertypeElement = declared.asElement();
			return isProtobufGeneratedMessage((TypeElement) supertypeElement);
		}
		return false;
	}

	private boolean isMethodFromProtobufGeneratedClass(ExecutableElement method) {
		Element receiver = method.getEnclosingElement();
		return receiver != null && typeUtils.isAssignable(receiver.asType(), protobufMesageOrBuilderType);
	}
}

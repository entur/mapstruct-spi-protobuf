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

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;

import org.mapstruct.ap.spi.DefaultBuilderProvider;

/**
 * Detect builder methods for protobuf lite generated classes.
 * <p>
 * Protobuf lite builders are not detected as such by the DefaultBuilderProvider because the return type is not an explicit match for the desired TypeElement.
 * Relying instead on known name of builder method and superclass of return type.
 */
public class ProtobufLiteBuilderProvider extends DefaultBuilderProvider {

	public static final String PROTOBUF_LITE_GENERATED_MESSAGE = "com.google.protobuf.GeneratedMessageLite";
	private static final String PROTOBUF_BUILD_METHOD_NAME = "build";

	@Override
	protected boolean isBuildMethod(ExecutableElement buildMethod, TypeElement typeElement) {

		if (PROTOBUF_BUILD_METHOD_NAME.equals(buildMethod.getSimpleName().toString())) {
			if (buildMethod.getReturnType() instanceof TypeVariable) {
				TypeVariable tv = (TypeVariable) buildMethod.getReturnType();

				TypeMirror upperBound = tv.getUpperBound() == null ? tv : tv.getUpperBound();

				if (upperBound.toString().startsWith(PROTOBUF_LITE_GENERATED_MESSAGE)) {
					return true;
				}

			}
		}

		return super.isBuildMethod(buildMethod, typeElement);
	}
}

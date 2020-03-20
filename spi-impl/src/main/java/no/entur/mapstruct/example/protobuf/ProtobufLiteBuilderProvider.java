package no.entur.mapstruct.example.protobuf;

/*-
 * #%L
 * protobuf-spi-impl
 * %%
 * Copyright (C) 2018 - 2020 Entur AS and original authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.mapstruct.ap.spi.DefaultBuilderProvider;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;

/**
 * Detect builder methods for protobuf lite generated classes.
 * <p>
 * Protobuf lite builders are not detected as such by the DefaultBuilderProvider because the return type is not an explicit
 * match for the desired TypeElement. Relying instead on known name of builder method and superclass of return type.
 */
public class ProtobufLiteBuilderProvider extends DefaultBuilderProvider {

    private static final String PROTOBUF_BUILD_METHOD_NAME = "build";

    public static final String PROTOBUF_LITE_GENERATED_MESSAGE = "com.google.protobuf.GeneratedMessageLite";

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

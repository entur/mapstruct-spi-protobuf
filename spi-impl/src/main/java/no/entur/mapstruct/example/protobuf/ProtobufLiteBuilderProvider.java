package no.entur.mapstruct.example.protobuf;

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


package com.al.qdt.common.services.v1;

import com.al.qdt.rps.grpc.v1.services.DeleteScoreByIdRequest;
import com.al.qdt.rps.grpc.v1.services.DeleteScoreByIdResponse;
import com.al.qdt.rps.grpc.v1.services.ScoreCmdServiceGrpc;
import io.grpc.MethodDescriptor;
import io.grpc.stub.annotations.RpcMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.tools.ToolProvider;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.grpc.MethodDescriptor.MethodType.UNARY;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing of the ScoreCmdService class")
@Tag(value = "grpc")
class ScoreCmdServiceTest {
    private static final String GENERATED_PROTOBUF_FILES_PATH = "./target/generated-sources/protobuf/grpc-java/com/al/qdt/rps/grpc/v1/services/ScoreCmdServiceGrpc.java";
    private static final String TASK_OPTION_1 = "-proc:only";
    private static final String SERVICE_DESCRIPTOR = "v1.services.ScoreCmdService";
    private static final String DELETE_BY_ID_METHODS_GROUP_NAME = "Test deleteById() methods group";

    @Test
    @DisplayName("Testing of the ScoreCmdService descriptor")
    void scoreCmdServiceDescriptorTest() {
        assertEquals(SERVICE_DESCRIPTOR, ScoreCmdServiceGrpc.getServiceDescriptor().getName());
    }

    @Test
    @DisplayName("Testing of the ScoreCmdService deleteById() methods")
    void scoreCmdServiceDeleteByIdMethodDescriptors() {
        MethodDescriptor<DeleteScoreByIdRequest, DeleteScoreByIdResponse> genericTypeShouldMatchWhenAssigned;

        genericTypeShouldMatchWhenAssigned = ScoreCmdServiceGrpc.getDeleteByIdMethod();
        assertEquals(UNARY, genericTypeShouldMatchWhenAssigned.getType());
    }

    @Test
    @DisplayName("Testing of the ScoreCmdService methods tracing")
    void generatedMethodsAreSampledToLocalTracing() {
        assertAll(DELETE_BY_ID_METHODS_GROUP_NAME,
                () -> assertTrue(ScoreCmdServiceGrpc.getDeleteByIdMethod().isSampledToLocalTracing())
        );
    }

    @Test
    @DisplayName("Testing of the ScoreCmdService method annotations")
    void testScoreCmdServiceMethodAnnotations() throws Exception {
        final var grpcJavaFile = new File(GENERATED_PROTOBUF_FILES_PATH);

        assertTrue(grpcJavaFile.exists());

        final var compiler = ToolProvider.getSystemJavaCompiler();
        final var fileManager = compiler.getStandardFileManager(null, null, null);
        final var processor = new AnnotationProcessor();
        final var obs = fileManager.getJavaFileObjects(grpcJavaFile);
        final var task =
                compiler.getTask(
                        null,
                        fileManager,
                        null,
                        List.of(TASK_OPTION_1),
                        List.of(ScoreCmdServiceGrpc.class.getCanonicalName()),
                        obs);
        task.setProcessors(Set.of(processor));

        assertTrue(task.call());
        assertTrue(processor.processedClass);

        fileManager.close();
    }

    static class AnnotationProcessor extends AbstractProcessor {
        private boolean processedClass = false;

        @Override
        public Set<String> getSupportedAnnotationTypes() {
            return Set.of(RpcMethod.class.getCanonicalName());
        }

        @Override
        public synchronized boolean process(
                final Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
            roundEnv.getRootElements().forEach((rootElement) -> {
                if (!String.valueOf(rootElement.asType()).equals(ScoreCmdServiceGrpc.class.getCanonicalName())) {
                    return;
                }

                final Map<String, RpcMethod> methodToAnnotation = new HashMap<>();
                rootElement.getEnclosedElements().forEach((enclosedElement) -> {
                    final var annotation = enclosedElement.getAnnotation(RpcMethod.class);
                    if (annotation != null) {
                        methodToAnnotation.put(String.valueOf(enclosedElement.getSimpleName()), annotation);
                    }
                });

                assertAll(DELETE_BY_ID_METHODS_GROUP_NAME,
                        () -> verifyDeleteByIdRpcMethodAnnotation(
                                ScoreCmdServiceGrpc.getDeleteByIdMethod(), methodToAnnotation.get("getDeleteByIdMethod"))
                );

                processedClass = true;
            });
            return false;
        }

        @Override
        public SourceVersion getSupportedSourceVersion() {
            return SourceVersion.latest();
        }

        private void verifyDeleteByIdRpcMethodAnnotation(MethodDescriptor<DeleteScoreByIdRequest, DeleteScoreByIdResponse> descriptor, RpcMethod annotation) {
            assertEquals(descriptor.getFullMethodName(), annotation.fullMethodName());
            assertEquals(descriptor.getType(), annotation.methodType());

            // Class objects may not be available at runtime, handle MirroredTypeException if it occurs
            try {
                assertEquals(DeleteScoreByIdRequest.class, annotation.requestType());
            } catch (MirroredTypeException e) {
                assertEquals(DeleteScoreByIdRequest.class.getCanonicalName(), String.valueOf(e.getTypeMirror()));
            }

            try {
                assertEquals(DeleteScoreByIdResponse.class, annotation.responseType());
            } catch (MirroredTypeException e) {
                assertEquals(DeleteScoreByIdResponse.class.getCanonicalName(), String.valueOf(e.getTypeMirror()));
            }
        }
    }
}

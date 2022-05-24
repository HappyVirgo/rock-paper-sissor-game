
package com.al.qdt.common.services.v1;

import com.al.qdt.rps.grpc.v1.services.DeleteGameByIdRequest;
import com.al.qdt.rps.grpc.v1.services.DeleteGameByIdResponse;
import com.al.qdt.rps.grpc.v1.services.GameRequest;
import com.al.qdt.rps.grpc.v1.services.GameResponse;
import com.al.qdt.rps.grpc.v1.services.RpsCmdServiceGrpc;
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

import static io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING;
import static io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING;
import static io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING;
import static io.grpc.MethodDescriptor.MethodType.UNARY;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing of the RpsCmdService class")
@Tag(value = "grpc")
class RpsCmdServiceTest {
    private static final String GENERATED_PROTOBUF_FILES_PATH = "./target/generated-sources/protobuf/grpc-java/com/al/qdt/rps/grpc/v1/services/RpsCmdServiceGrpc.java";
    private static final String TASK_OPTION_1 = "-proc:only";
    private static final String SERVICE_DESCRIPTOR = "v1.services.RpsCmdService";
    private static final String PLAY_METHODS_GROUP_NAME = "Test play() methods group";
    private static final String DELETE_BY_ID_METHODS_GROUP_NAME = "Test deleteById() methods group";

    @Test
    @DisplayName("Testing of the RpsCmdService descriptor")
    void rpsCmdServiceDescriptorTest() {
        assertEquals(SERVICE_DESCRIPTOR, RpsCmdServiceGrpc.getServiceDescriptor().getName());
    }

    @Test
    @DisplayName("Testing of the RpsCmdService play() methods")
    void rpsCmdServicePlayMethodDescriptors() {
        MethodDescriptor<GameRequest, GameResponse> genericTypeShouldMatchWhenAssigned;

        genericTypeShouldMatchWhenAssigned = RpsCmdServiceGrpc.getPlayMethod();
        assertEquals(UNARY, genericTypeShouldMatchWhenAssigned.getType());

        genericTypeShouldMatchWhenAssigned = RpsCmdServiceGrpc.getPlayClientStreamingMethod();
        assertEquals(CLIENT_STREAMING, genericTypeShouldMatchWhenAssigned.getType());

        genericTypeShouldMatchWhenAssigned = RpsCmdServiceGrpc.getPlayServerStreamingMethod();
        assertEquals(SERVER_STREAMING, genericTypeShouldMatchWhenAssigned.getType());

        genericTypeShouldMatchWhenAssigned = RpsCmdServiceGrpc.getPlayBidirectionalStreamingMethod();
        assertEquals(BIDI_STREAMING, genericTypeShouldMatchWhenAssigned.getType());
    }

    @Test
    @DisplayName("Testing of the RpsCmdService deleteById() methods")
    void rpsCmdServiceDeleteByIdMethodDescriptors() {
        MethodDescriptor<DeleteGameByIdRequest, DeleteGameByIdResponse> genericTypeShouldMatchWhenAssigned;

        genericTypeShouldMatchWhenAssigned = RpsCmdServiceGrpc.getDeleteByIdMethod();
        assertEquals(UNARY, genericTypeShouldMatchWhenAssigned.getType());

        genericTypeShouldMatchWhenAssigned = RpsCmdServiceGrpc.getDeleteByIdClientStreamingMethod();
        assertEquals(CLIENT_STREAMING, genericTypeShouldMatchWhenAssigned.getType());

        genericTypeShouldMatchWhenAssigned = RpsCmdServiceGrpc.getDeleteByIdServerStreamingMethod();
        assertEquals(SERVER_STREAMING, genericTypeShouldMatchWhenAssigned.getType());

        genericTypeShouldMatchWhenAssigned = RpsCmdServiceGrpc.getDeleteByIdBidirectionalStreamingMethod();
        assertEquals(BIDI_STREAMING, genericTypeShouldMatchWhenAssigned.getType());
    }

    @Test
    @DisplayName("Testing of the RpsCmdService methods tracing")
    void generatedMethodsAreSampledToLocalTracing() {
        assertAll(PLAY_METHODS_GROUP_NAME,
                () -> assertTrue(RpsCmdServiceGrpc.getPlayMethod().isSampledToLocalTracing()),
                () -> assertTrue(RpsCmdServiceGrpc.getPlayClientStreamingMethod().isSampledToLocalTracing()),
                () -> assertTrue(RpsCmdServiceGrpc.getPlayServerStreamingMethod().isSampledToLocalTracing()),
                () -> assertTrue(RpsCmdServiceGrpc.getPlayBidirectionalStreamingMethod().isSampledToLocalTracing())
        );

        assertAll(DELETE_BY_ID_METHODS_GROUP_NAME,
                () -> assertTrue(RpsCmdServiceGrpc.getDeleteByIdMethod().isSampledToLocalTracing()),
                () -> assertTrue(RpsCmdServiceGrpc.getDeleteByIdClientStreamingMethod().isSampledToLocalTracing()),
                () -> assertTrue(RpsCmdServiceGrpc.getDeleteByIdServerStreamingMethod().isSampledToLocalTracing()),
                () -> assertTrue(RpsCmdServiceGrpc.getDeleteByIdBidirectionalStreamingMethod().isSampledToLocalTracing())
        );
    }

    @Test
    @DisplayName("Testing of the RpsCmdService method annotations")
    void testRpsCmdServiceMethodAnnotations() throws Exception {
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
                        List.of(RpsCmdServiceGrpc.class.getCanonicalName()),
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
                if (!String.valueOf(rootElement.asType()).equals(RpsCmdServiceGrpc.class.getCanonicalName())) {
                    return;
                }

                final Map<String, RpcMethod> methodToAnnotation = new HashMap<>();
                rootElement.getEnclosedElements().forEach((enclosedElement) -> {
                    final var annotation = enclosedElement.getAnnotation(RpcMethod.class);
                    if (annotation != null) {
                        methodToAnnotation.put(String.valueOf(enclosedElement.getSimpleName()), annotation);
                    }
                });

                assertAll(PLAY_METHODS_GROUP_NAME,
                        () -> verifyPlayRpcMethodAnnotation(
                                RpsCmdServiceGrpc.getPlayMethod(), methodToAnnotation.get("getPlayMethod")),
                        () -> verifyPlayRpcMethodAnnotation(
                                RpsCmdServiceGrpc.getPlayClientStreamingMethod(),
                                methodToAnnotation.get("getPlayClientStreamingMethod")),
                        () -> verifyPlayRpcMethodAnnotation(
                                RpsCmdServiceGrpc.getPlayServerStreamingMethod(),
                                methodToAnnotation.get("getPlayServerStreamingMethod")),
                        () -> verifyPlayRpcMethodAnnotation(
                                RpsCmdServiceGrpc.getPlayBidirectionalStreamingMethod(),
                                methodToAnnotation.get("getPlayBidirectionalStreamingMethod"))
                );

                assertAll(DELETE_BY_ID_METHODS_GROUP_NAME,
                        () -> verifyDeleteByIdRpcMethodAnnotation(
                                RpsCmdServiceGrpc.getDeleteByIdMethod(), methodToAnnotation.get("getDeleteByIdMethod")),
                        () -> verifyDeleteByIdRpcMethodAnnotation(
                                RpsCmdServiceGrpc.getDeleteByIdClientStreamingMethod(),
                                methodToAnnotation.get("getDeleteByIdClientStreamingMethod")),
                        () -> verifyDeleteByIdRpcMethodAnnotation(
                                RpsCmdServiceGrpc.getDeleteByIdServerStreamingMethod(),
                                methodToAnnotation.get("getDeleteByIdServerStreamingMethod")),
                        () -> verifyDeleteByIdRpcMethodAnnotation(
                                RpsCmdServiceGrpc.getDeleteByIdBidirectionalStreamingMethod(),
                                methodToAnnotation.get("getDeleteByIdBidirectionalStreamingMethod"))
                );
                processedClass = true;
            });
            return false;
        }

        @Override
        public SourceVersion getSupportedSourceVersion() {
            return SourceVersion.latest();
        }

        private void verifyPlayRpcMethodAnnotation(MethodDescriptor<GameRequest, GameResponse> descriptor, RpcMethod annotation) {
            assertEquals(descriptor.getFullMethodName(), annotation.fullMethodName());
            assertEquals(descriptor.getType(), annotation.methodType());

            // Class objects may not be available at runtime, handle MirroredTypeException if it occurs
            try {
                assertEquals(GameRequest.class, annotation.requestType());
            } catch (MirroredTypeException e) {
                assertEquals(GameRequest.class.getCanonicalName(), String.valueOf(e.getTypeMirror()));
            }

            try {
                assertEquals(GameResponse.class, annotation.responseType());
            } catch (MirroredTypeException e) {
                assertEquals(GameResponse.class.getCanonicalName(), String.valueOf(e.getTypeMirror()));
            }
        }

        private void verifyDeleteByIdRpcMethodAnnotation(MethodDescriptor<DeleteGameByIdRequest, DeleteGameByIdResponse> descriptor, RpcMethod annotation) {
            assertEquals(descriptor.getFullMethodName(), annotation.fullMethodName());
            assertEquals(descriptor.getType(), annotation.methodType());

            // Class objects may not be available at runtime, handle MirroredTypeException if it occurs
            try {
                assertEquals(DeleteGameByIdRequest.class, annotation.requestType());
            } catch (MirroredTypeException e) {
                assertEquals(DeleteGameByIdRequest.class.getCanonicalName(), String.valueOf(e.getTypeMirror()));
            }

            try {
                assertEquals(DeleteGameByIdResponse.class, annotation.responseType());
            } catch (MirroredTypeException e) {
                assertEquals(DeleteGameByIdResponse.class.getCanonicalName(), String.valueOf(e.getTypeMirror()));
            }
        }
    }
}

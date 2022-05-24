
package com.al.qdt.common.services.v1;

import com.al.qdt.rps.grpc.v1.services.AdminCmdServiceGrpc;
import com.al.qdt.rps.grpc.v1.services.RestoreDbRequest;
import com.al.qdt.rps.grpc.v1.services.RestoreDbResponse;
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

@DisplayName("Testing of the AdminCmdService class")
@Tag(value = "grpc")
class AdminCmdServiceTest {
    private static final String GENERATED_PROTOBUF_FILES_PATH = "./target/generated-sources/protobuf/grpc-java/com/al/qdt/rps/grpc/v1/services/AdminCmdServiceGrpc.java";
    private static final String TASK_OPTION_1 = "-proc:only";
    private static final String SERVICE_DESCRIPTOR = "v1.services.AdminCmdService";
    private static final String RESTORE_DB_METHODS_GROUP_NAME = "Test restoreDb() methods group";

    @Test
    @DisplayName("Testing of the AdminCmdService descriptor")
    void adminCmdServiceDescriptorTest() {
        assertEquals(SERVICE_DESCRIPTOR, AdminCmdServiceGrpc.getServiceDescriptor().getName());
    }

    @Test
    @DisplayName("Testing of the AdminCmdService restoreDb() methods")
    void adminCmdServiceRestoreDbMethodDescriptors() {
        MethodDescriptor<RestoreDbRequest, RestoreDbResponse> genericTypeShouldMatchWhenAssigned;

        genericTypeShouldMatchWhenAssigned = AdminCmdServiceGrpc.getRestoreDbMethod();
        assertEquals(UNARY, genericTypeShouldMatchWhenAssigned.getType());

        genericTypeShouldMatchWhenAssigned = AdminCmdServiceGrpc.getRestoreDbClientStreamingMethod();
        assertEquals(CLIENT_STREAMING, genericTypeShouldMatchWhenAssigned.getType());

        genericTypeShouldMatchWhenAssigned = AdminCmdServiceGrpc.getRestoreDbServerStreamingMethod();
        assertEquals(SERVER_STREAMING, genericTypeShouldMatchWhenAssigned.getType());

        genericTypeShouldMatchWhenAssigned = AdminCmdServiceGrpc.getRestoreDbBidirectionalStreamingMethod();
        assertEquals(BIDI_STREAMING, genericTypeShouldMatchWhenAssigned.getType());
    }

    @Test
    @DisplayName("Testing of the AdminCmdService methods tracing")
    void generatedMethodsAreSampledToLocalTracing() {
        assertAll(RESTORE_DB_METHODS_GROUP_NAME,
                () -> assertTrue(AdminCmdServiceGrpc.getRestoreDbMethod().isSampledToLocalTracing()),
                () -> assertTrue(AdminCmdServiceGrpc.getRestoreDbClientStreamingMethod().isSampledToLocalTracing()),
                () -> assertTrue(AdminCmdServiceGrpc.getRestoreDbServerStreamingMethod().isSampledToLocalTracing()),
                () -> assertTrue(AdminCmdServiceGrpc.getRestoreDbBidirectionalStreamingMethod().isSampledToLocalTracing())
        );
    }

    @Test
    @DisplayName("Testing of the AdminCmdService method annotations")
    void testAdminCmdServiceMethodAnnotations() throws Exception {
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
                        List.of(AdminCmdServiceGrpc.class.getCanonicalName()),
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
                if (!String.valueOf(rootElement.asType()).equals(AdminCmdServiceGrpc.class.getCanonicalName())) {
                    return;
                }

                final Map<String, RpcMethod> methodToAnnotation = new HashMap<>();
                rootElement.getEnclosedElements().forEach((enclosedElement) -> {
                    final var annotation = enclosedElement.getAnnotation(RpcMethod.class);
                    if (annotation != null) {
                        methodToAnnotation.put(String.valueOf(enclosedElement.getSimpleName()), annotation);
                    }
                });

                assertAll(RESTORE_DB_METHODS_GROUP_NAME,
                        () -> verifyRestoreDbRpcMethodAnnotation(
                                AdminCmdServiceGrpc.getRestoreDbMethod(), methodToAnnotation.get("getRestoreDbMethod")),
                        () -> verifyRestoreDbRpcMethodAnnotation(
                                AdminCmdServiceGrpc.getRestoreDbClientStreamingMethod(),
                                methodToAnnotation.get("getRestoreDbClientStreamingMethod")),
                        () -> verifyRestoreDbRpcMethodAnnotation(
                                AdminCmdServiceGrpc.getRestoreDbServerStreamingMethod(),
                                methodToAnnotation.get("getRestoreDbServerStreamingMethod")),
                        () -> verifyRestoreDbRpcMethodAnnotation(
                                AdminCmdServiceGrpc.getRestoreDbBidirectionalStreamingMethod(),
                                methodToAnnotation.get("getRestoreDbBidirectionalStreamingMethod"))
                );
                processedClass = true;
            });
            return false;
        }

        @Override
        public SourceVersion getSupportedSourceVersion() {
            return SourceVersion.latest();
        }

        private void verifyRestoreDbRpcMethodAnnotation(MethodDescriptor<RestoreDbRequest, RestoreDbResponse> descriptor, RpcMethod annotation) {
            assertEquals(descriptor.getFullMethodName(), annotation.fullMethodName());
            assertEquals(descriptor.getType(), annotation.methodType());

            // Class objects may not be available at runtime, handle MirroredTypeException if it occurs
            try {
                assertEquals(RestoreDbRequest.class, annotation.requestType());
            } catch (MirroredTypeException e) {
                assertEquals(RestoreDbRequest.class.getCanonicalName(), String.valueOf(e.getTypeMirror()));
            }

            try {
                assertEquals(RestoreDbResponse.class, annotation.responseType());
            } catch (MirroredTypeException e) {
                assertEquals(RestoreDbResponse.class.getCanonicalName(), String.valueOf(e.getTypeMirror()));
            }
        }
    }
}

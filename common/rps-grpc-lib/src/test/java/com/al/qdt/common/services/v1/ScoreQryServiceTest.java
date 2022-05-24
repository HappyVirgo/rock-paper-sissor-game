
package com.al.qdt.common.services.v1;

import com.al.qdt.rps.grpc.v1.services.FindScoreByIdRequest;
import com.al.qdt.rps.grpc.v1.services.FindScoreByWinnerRequest;
import com.al.qdt.rps.grpc.v1.services.ListOfScoresRequest;
import com.al.qdt.rps.grpc.v1.services.ListOfScoresResponse;
import com.al.qdt.rps.grpc.v1.services.ScoreQryServiceGrpc;
import com.al.qdt.rps.grpc.v1.services.ScoreResponse;
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

@DisplayName("Testing of the ScoreQryService class")
@Tag(value = "grpc")
class ScoreQryServiceTest {
    private static final String GENERATED_PROTOBUF_FILES_PATH = "./target/generated-sources/protobuf/grpc-java/com/al/qdt/rps/grpc/v1/services/ScoreQryServiceGrpc.java";
    private static final String TASK_OPTION_1 = "-proc:only";
    private static final String SERVICE_DESCRIPTOR = "v1.services.ScoreQryService";
    private static final String GET_ALL_SCORES_METHODS_GROUP_NAME = "Test listOfScores() methods group";
    private static final String FIND_BY_ID_METHODS_GROUP_NAME = "Test findById() methods group";
    private static final String FIND_BY_WINNER_METHODS_GROUP_NAME = "Test findByWinner() methods group";

    @Test
    @DisplayName("Testing of the ScoreQryService descriptor")
    void scoreQryServiceDescriptorTest() {
        assertEquals(SERVICE_DESCRIPTOR, ScoreQryServiceGrpc.getServiceDescriptor().getName());
    }

    @Test
    @DisplayName("Testing of the ScoreQryService listOfScores() methods")
    void scoreQryServiceListOfScoresMethodDescriptors() {
        MethodDescriptor<ListOfScoresRequest, ListOfScoresResponse> genericTypeShouldMatchWhenAssigned;

        genericTypeShouldMatchWhenAssigned = ScoreQryServiceGrpc.getListOfScoresMethod();
        assertEquals(UNARY, genericTypeShouldMatchWhenAssigned.getType());
    }

    @Test
    @DisplayName("Testing of the ScoreQryService findById() methods")
    void scoreQryServiceFindByIdMethodDescriptors() {
        MethodDescriptor<FindScoreByIdRequest, ScoreResponse> genericTypeShouldMatchWhenAssigned;

        genericTypeShouldMatchWhenAssigned = ScoreQryServiceGrpc.getFindByIdMethod();
        assertEquals(UNARY, genericTypeShouldMatchWhenAssigned.getType());
    }

    @Test
    @DisplayName("Testing of the ScoreQryService findByWinner() methods")
    void scoreQryServiceFindByWinnerMethodDescriptors() {
        MethodDescriptor<FindScoreByWinnerRequest, ListOfScoresResponse> genericTypeShouldMatchWhenAssigned;

        genericTypeShouldMatchWhenAssigned = ScoreQryServiceGrpc.getFindByWinnerMethod();
        assertEquals(UNARY, genericTypeShouldMatchWhenAssigned.getType());
    }

    @Test
    @DisplayName("Testing of the ScoreQryService methods tracing")
    void generatedMethodsAreSampledToLocalTracing() {
        assertAll(GET_ALL_SCORES_METHODS_GROUP_NAME,
                () -> assertTrue(ScoreQryServiceGrpc.getListOfScoresMethod().isSampledToLocalTracing())
        );

        assertAll(FIND_BY_ID_METHODS_GROUP_NAME,
                () -> assertTrue(ScoreQryServiceGrpc.getFindByIdMethod().isSampledToLocalTracing())
        );

        assertAll(FIND_BY_WINNER_METHODS_GROUP_NAME,
                () -> assertTrue(ScoreQryServiceGrpc.getFindByWinnerMethod().isSampledToLocalTracing())
        );
    }

    @Test
    @DisplayName("Testing of the ScoreQryService method annotations")
    void testScoreQryServiceMethodAnnotations() throws Exception {
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
                        List.of(ScoreQryServiceGrpc.class.getCanonicalName()),
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
                if (!String.valueOf(rootElement.asType()).equals(ScoreQryServiceGrpc.class.getCanonicalName())) {
                    return;
                }

                final Map<String, RpcMethod> methodToAnnotation = new HashMap<>();
                rootElement.getEnclosedElements().forEach((enclosedElement) -> {
                    final var annotation = enclosedElement.getAnnotation(RpcMethod.class);
                    if (annotation != null) {
                        methodToAnnotation.put(String.valueOf(enclosedElement.getSimpleName()), annotation);
                    }
                });

                assertAll(GET_ALL_SCORES_METHODS_GROUP_NAME,
                        () -> verifyGetListOfScoresRpcMethodAnnotation(
                                ScoreQryServiceGrpc.getListOfScoresMethod(), methodToAnnotation.get("getListOfScoresMethod"))
                );

                assertAll(FIND_BY_ID_METHODS_GROUP_NAME,
                        () -> verifyFindByIdRpcMethodAnnotation(
                                ScoreQryServiceGrpc.getFindByIdMethod(), methodToAnnotation.get("getFindByIdMethod"))
                );

                assertAll(FIND_BY_WINNER_METHODS_GROUP_NAME,
                        () -> verifyFindByWinnerRpcMethodAnnotation(
                                ScoreQryServiceGrpc.getFindByWinnerMethod(), methodToAnnotation.get("getFindByWinnerMethod"))
                );
                processedClass = true;
            });
            return false;
        }

        @Override
        public SourceVersion getSupportedSourceVersion() {
            return SourceVersion.latest();
        }

        private void verifyGetListOfScoresRpcMethodAnnotation(MethodDescriptor<ListOfScoresRequest, ListOfScoresResponse> descriptor, RpcMethod annotation) {
            assertEquals(descriptor.getFullMethodName(), annotation.fullMethodName());
            assertEquals(descriptor.getType(), annotation.methodType());

            // Class objects may not be available at runtime, handle MirroredTypeException if it occurs
            try {
                assertEquals(ListOfScoresRequest.class, annotation.requestType());
            } catch (MirroredTypeException e) {
                assertEquals(ListOfScoresRequest.class.getCanonicalName(), String.valueOf(e.getTypeMirror()));
            }

            try {
                assertEquals(ListOfScoresResponse.class, annotation.responseType());
            } catch (MirroredTypeException e) {
                assertEquals(ListOfScoresResponse.class.getCanonicalName(), String.valueOf(e.getTypeMirror()));
            }
        }

        private void verifyFindByIdRpcMethodAnnotation(MethodDescriptor<FindScoreByIdRequest, ScoreResponse> descriptor, RpcMethod annotation) {
            assertEquals(descriptor.getFullMethodName(), annotation.fullMethodName());
            assertEquals(descriptor.getType(), annotation.methodType());

            // Class objects may not be available at runtime, handle MirroredTypeException if it occurs
            try {
                assertEquals(FindScoreByIdRequest.class, annotation.requestType());
            } catch (MirroredTypeException e) {
                assertEquals(FindScoreByIdRequest.class.getCanonicalName(), String.valueOf(e.getTypeMirror()));
            }

            try {
                assertEquals(ScoreResponse.class, annotation.responseType());
            } catch (MirroredTypeException e) {
                assertEquals(ScoreResponse.class.getCanonicalName(), String.valueOf(e.getTypeMirror()));
            }
        }

        private void verifyFindByWinnerRpcMethodAnnotation(MethodDescriptor<FindScoreByWinnerRequest, ListOfScoresResponse> descriptor, RpcMethod annotation) {
            assertEquals(descriptor.getFullMethodName(), annotation.fullMethodName());
            assertEquals(descriptor.getType(), annotation.methodType());

            // Class objects may not be available at runtime, handle MirroredTypeException if it occurs
            try {
                assertEquals(FindScoreByWinnerRequest.class, annotation.requestType());
            } catch (MirroredTypeException e) {
                assertEquals(FindScoreByWinnerRequest.class.getCanonicalName(), String.valueOf(e.getTypeMirror()));
            }

            try {
                assertEquals(ListOfScoresResponse.class, annotation.responseType());
            } catch (MirroredTypeException e) {
                assertEquals(ListOfScoresResponse.class.getCanonicalName(), String.valueOf(e.getTypeMirror()));
            }
        }
    }
}

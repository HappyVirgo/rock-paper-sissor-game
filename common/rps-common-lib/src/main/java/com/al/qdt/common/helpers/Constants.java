package com.al.qdt.common.helpers;

import lombok.experimental.UtilityClass;

import java.util.UUID;

import static com.al.qdt.common.infrastructure.commands.CommandDispatcherImpl.NO_COMMAND_HANDLER_REGISTERED_EXCEPTION_MESSAGE;
import static com.al.qdt.common.infrastructure.commands.CommandDispatcherImpl.SEND_COMMAND_EXCEPTION_MESSAGE;

@UtilityClass
public class Constants {
    public static final String USERNAME_ONE = "User1"; // first test username
    public static final String USERNAME_TWO = "User2"; // second test username
    public static final int PASSWORD_STRENGTH = 12; // password encryption length
    public static final long MAX_AGE_SECS = 3600L; // indicates how long the results of a request can be cached
    public static final UUID TEST_UUID = UUID.fromString("748873ec-f887-4090-93ff-f8b8cbb34c7a");
    public static final UUID TEST_UUID_TWO = UUID.fromString("898873ec-f887-4090-93ff-f8b8cbb34c7a");
    public static final String TEST_ID = "748873ec-f887-4090-93ff-f8b8cbb34c7a";
    public static final String TEST_WINNER = "USER";
    public final static String SUCCESS_MESSAGE = "Database restore request completed successfully!";

    // kafka testing properties
    public static final String TEST_KEY = "1";
    public static final String DUMMY_BOOTSTRAP_SERVER = "localhost:9092";
    public static final String INPUT_TOPIC_PREFIX = "input_";
    public static final String OUTPUT_TOPIC_PREFIX = "output_";
    public static final String EMPTY_TOPIC_EXCEPTION_MESSAGE = "Error occurred, empty topic: %s";
    public static final String TEST_TIMESTAMP = "2021-10-12T00:00:00Z";
    public static final long POLLING_INTERVAL = 100L;
    public static final String EXECUTION_EXCEPTION_MESSAGE = "Error occurred while sending an event to Kafka topic.";
    public static final String INTERRUPTION_EXCEPTION_MESSAGE = "This thread was interrupted...";

    // dto serialization/deserialization testing properties
    public static final String BASE_RESPONSE_EXPECTED_JSON = "{\"message\":\"Database restore request completed successfully!\"}";
    public static final String GAME_RESPONSE_EXPECTED_JSON = "{\"user_choice\":\"ROCK\",\"machine_choice\":\"SCISSORS\",\"result\":\"USER\"}";
    public static final String GAME_EXPECTED_JSON = "{\"username\":\"User1\",\"hand\":\"ROCK\"}";
    public static final String GAMES_EXPECTED_JSON = "[{\"username\":\"User1\",\"hand\":\"ROCK\"}]";
    public static final String SCORE_EXPECTED_JSON = "{\"winner\":\"USER\"}";
    public static final String SCORES_EXPECTED_JSON = "[{\"winner\":\"USER\"}]";

    // open api
    public static final String HANDLER_ERROR_JSON = "{\n" +
            "  \"apierror\": {\n" +
            "    \"status\": \"400 BAD_REQUEST\",\n" +
            "    \"timestamp\": \"11-02-2022 07:26:53\",\n" +
            "    \"message\": \"" +
            NO_COMMAND_HANDLER_REGISTERED_EXCEPTION_MESSAGE +
            "\"\n" +
            "  }\n" +
            "}";

    public static final String MULTIPLE_HANDLERS_ERROR_JSON = "{\n" +
            "  \"apierror\": {\n" +
            "    \"status\": \"400 BAD_REQUEST\",\n" +
            "    \"timestamp\": \"11-02-2022 07:26:53\",\n" +
            "    \"message\": \"" +
            SEND_COMMAND_EXCEPTION_MESSAGE +
            "\"\n" +
            "  }\n" +
            "}";

    public static final String MALFORMED_JSON = "{\n" +
            "  \"apierror\": {\n" +
            "    \"status\": \"400 BAD_REQUEST\",\n" +
            "    \"timestamp\": \"11-02-2022 07:26:53\",\n" +
            "    \"message\": \"Validation error!\"\n" +
            "  }\n" +
            "}";

    public static final String GAMES_NOT_FOUND_JSON = "{\n" +
            "  \"apierror\": {\n" +
            "    \"status\": \"404 NOT_FOUND\",\n" +
            "    \"timestamp\": \"11-02-2022 07:26:53\",\n" +
            "    \"message\": \"Games have not been found!\"\n" +
            "  }\n" +
            "}";

    public static final String GAME_BY_ID_NOT_FOUND_JSON = "{\n" +
            "  \"apierror\": {\n" +
            "    \"status\": \"404 NOT_FOUND\",\n" +
            "    \"timestamp\": \"11-02-2022 07:26:53\",\n" +
            "    \"message\": \"Game with id 748873ec-f887-4090-93ff-f8b8cbb34c7a has not been found!\"\n" +
            "  }\n" +
            "}";

    public static final String GAMES_BY_USER_NOT_FOUND_JSON = "{\n" +
            "  \"apierror\": {\n" +
            "    \"status\": \"404 NOT_FOUND\",\n" +
            "    \"timestamp\": \"11-02-2022 07:26:53\",\n" +
            "    \"message\": \"Games with username User1 have not been found!\"\n" +
            "  }\n" +
            "}";

    public static final String SCORES_NOT_FOUND_JSON = "{\n" +
            "  \"apierror\": {\n" +
            "    \"status\": \"404 NOT_FOUND\",\n" +
            "    \"timestamp\": \"11-02-2022 07:26:53\",\n" +
            "    \"message\": \"Scores have not been found!\"\n" +
            "  }\n" +
            "}";

    public static final String SCORE_BY_ID_NOT_FOUND_JSON = "{\n" +
            "  \"apierror\": {\n" +
            "    \"status\": \"404 NOT_FOUND\",\n" +
            "    \"timestamp\": \"11-02-2022 07:26:53\",\n" +
            "    \"message\": \"Score with id 748873ec-f887-4090-93ff-f8b8cbb34c7a has not been found!\"\n" +
            "  }\n" +
            "}";

    public static final String SCORES_BY_WINNER_NOT_FOUND_JSON = "{\n" +
            "  \"apierror\": {\n" +
            "    \"status\": \"404 NOT_FOUND\",\n" +
            "    \"timestamp\": \"11-02-2022 07:26:53\",\n" +
            "    \"message\": \"Scores with winner type DRAW have not been found!\"\n" +
            "  }\n" +
            "}";
}

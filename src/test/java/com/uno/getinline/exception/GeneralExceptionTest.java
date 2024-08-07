package com.uno.getinline.exception;

import com.uno.getinline.constant.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class GeneralExceptionTest {

    @DisplayName("")
    @MethodSource
    @ParameterizedTest(name = "[{index}] message({2}) => \"{1}\"")
    void givenException_whenInstantiating_thenContainsRelevantInformation(Throwable input, String expectedMessage, ErrorCode expectedErrorCode){

        //Given


        //When & Then
        assertThat(input)
                .isInstanceOf(input.getClass())
                .hasMessage(expectedMessage)
                .hasFieldOrPropertyWithValue("errorCode",expectedErrorCode);

    }

    static Stream<Arguments> givenException_whenInstantiating_thenContainsRelevantInformation(){
        String msg = "Test message.";
        Throwable t = new RuntimeException("inner message");
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;

        return Stream.of(
                arguments(new GeneralException(),"internal error", ErrorCode.INTERNAL_ERROR),
                arguments(new GeneralException(msg),msg,ErrorCode.INTERNAL_ERROR),
                arguments(new GeneralException(msg,t), msg, ErrorCode.INTERNAL_ERROR),
                arguments(new GeneralException(t),"internal error - " + t.getMessage(),ErrorCode.INTERNAL_ERROR),
                arguments(new GeneralException(errorCode), errorCode.getMessage(), errorCode),
                arguments(new GeneralException(errorCode, msg), msg, errorCode),
                arguments(new GeneralException(errorCode, msg, t), msg, errorCode),
                arguments(new GeneralException(errorCode, t), "Validation error", errorCode)
        );

    }
}
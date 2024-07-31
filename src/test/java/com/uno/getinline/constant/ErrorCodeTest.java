package com.uno.getinline.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ErrorCodeTest {


    @ParameterizedTest
    @MethodSource
    @DisplayName("예외를 받으면, 예외 메시지가 포함된 메시지 출력   ")
    void givenExceptionWithMessage_whenGettingMessageThenReturnsMessage(ErrorCode sut, String expected){
        //Given
        Exception e = new Exception("This is test message.");

        //when
        String actual = sut.getMessage(e);

        //Then
        assertThat(actual).isEqualTo(expected);
    }


    static Stream<Arguments> givenExceptionWithMessage_whenGettingMessageThenReturnsMessage(){
        return Stream.of(
                arguments(ErrorCode.OK,"Ok - This is test message."),
                arguments(ErrorCode.BAD_REQUEST,"Bad request - This is test message."),
                arguments(ErrorCode.SPRING_BAD_REQUEST,"Spring-detected bad request - This is test message."),
                arguments(ErrorCode.VALIDATION_ERROR,"Validation error - This is test message."),
                arguments(ErrorCode.INTERNAL_ERROR,"Internal error - This is test message."),
                arguments(ErrorCode.SPRING_INTERNAL_ERROR,"Spring-detected internal error- This is test message."),
                arguments(ErrorCode.DATA_ACCESS_ERROR,"Data access error - This is test message.")

        );
    }


    @ParameterizedTest(name = "[{index}] \"{0}\" ===> \"{1}\"")
    @MethodSource
    @DisplayName("에러 메시지를 받으면, 해당 에러 메시지로 출력")
    void givenMessage_whenGettingMessage_thenReturnsMessage(String input, String expected){
        //Given

        //when
        String actual = ErrorCode.INTERNAL_ERROR.getMessage(input);

        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> givenMessage_whenGettingMessage_thenReturnsMessage(){
        return Stream.of(
                arguments(null, ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments("",ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments("  ",ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments("This is test message.","This is test message.")
        );
    }


    @DisplayName("toString() 호출 포맷")
    @Test
    void givenErrorCode_whenToString_thenReturnsSimplifiedToString(){

        //Given

        //when
        String message = ErrorCode.INTERNAL_ERROR.toString();

        //then
        assertThat(message).isEqualTo("INTERNAL_ERROR (20000)");


    }





}
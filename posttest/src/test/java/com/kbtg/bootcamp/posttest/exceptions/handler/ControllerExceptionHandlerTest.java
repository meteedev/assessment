package com.kbtg.bootcamp.posttest.exceptions.handler;

import com.kbtg.bootcamp.posttest.exceptions.InternalServerException;
import com.kbtg.bootcamp.posttest.exceptions.UnProcessException;
import com.kbtg.bootcamp.posttest.exceptions.response.ApiErrorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ControllerExceptionHandlerTest {
    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;

    @Mock
    private WebRequest request;

    @Test
    public void testHandleUnProcessException() {
        // Create a sample Exception for testing

        String error_msg = "MSG_ERR";
        UnProcessException exception = new UnProcessException(error_msg);

        // Call the exception handler method
        ApiErrorResponse apiErrorResponse = controllerExceptionHandler.handleValidateException(exception, request);

        // Verify that the response is as expected
        assertNotNull(apiErrorResponse);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), apiErrorResponse.getStatus());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), apiErrorResponse.getError());
        assertEquals(error_msg, apiErrorResponse.getMessage());
    }

    @Test
    public void testHandleInternalServerException() {
        // Create a sample Exception for testing

        String error_msg = "MSG_ERR";
        InternalServerException exception = new InternalServerException(error_msg);

        // Call the exception handler method
        ApiErrorResponse apiErrorResponse = controllerExceptionHandler.handleInternalServerException(exception, request);

        // Verify that the response is as expected
        assertNotNull(apiErrorResponse);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), apiErrorResponse.getStatus());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), apiErrorResponse.getError());
        assertEquals(error_msg, apiErrorResponse.getMessage());
    }


    @Test
    void testHandleBadRequestException() {
        // Mock your MethodArgumentNotValidException and WebRequest
        MethodArgumentNotValidException notValidException = mock(MethodArgumentNotValidException.class);
        WebRequest webRequest = mock(WebRequest.class);

        // Instantiate your ControllerExceptionHandler
        ControllerExceptionHandler controllerExceptionHandler = new ControllerExceptionHandler();

        // Call the method under test
        ApiErrorResponse apiErrorResponse = controllerExceptionHandler.handleBadRequestException(notValidException, webRequest);
        assertEquals(HttpStatus.BAD_REQUEST.value(),apiErrorResponse.getStatus());
    }

}
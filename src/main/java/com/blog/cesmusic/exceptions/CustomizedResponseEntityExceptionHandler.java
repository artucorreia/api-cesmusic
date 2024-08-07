package com.blog.cesmusic.exceptions;

import com.blog.cesmusic.exceptions.auth.*;
import com.blog.cesmusic.exceptions.general.EmptyDataException;
import com.blog.cesmusic.exceptions.general.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(
            Exception exception,
            WebRequest request
    ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleResourceNotFoundExceptions(
            Exception exception,
            WebRequest request
    ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyDataException.class)
    public final ResponseEntity<ExceptionResponse> handleEmptyDataExceptions(
            Exception exception,
            WebRequest request
    ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JwtCreationTokenException.class)
    public final ResponseEntity<ExceptionResponse> handleJwtExceptions(
            Exception exception,
            WebRequest request
    ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public final ResponseEntity<ExceptionResponse> handleTokenExceptions(
            Exception exception,
            WebRequest request
    ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidLoginExceptions(
            Exception exception,
            WebRequest request
    ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginAlreadyUsedException.class)
    public final ResponseEntity<ExceptionResponse> handleLoginAlreadyUsedExceptions(
            Exception exception,
            WebRequest request
    ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(LoginLengthException.class)
    public final ResponseEntity<ExceptionResponse> handleLoginLengthExceptions(
            Exception exception,
            WebRequest request
    ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordLengthException.class)
    public final ResponseEntity<ExceptionResponse> handlePasswordLengthExceptions(
            Exception exception,
            WebRequest request
    ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FullNameLengthException.class)
    public final ResponseEntity<ExceptionResponse> handleFullNameLengthExceptions(
            Exception exception,
            WebRequest request
    ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FullNameNullException.class)
    public final ResponseEntity<ExceptionResponse> handleFullNameNullExceptions(
            Exception exception,
            WebRequest request
    ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserIsAlreadyActiveException.class)
    public final ResponseEntity<ExceptionResponse> handleUserAlreadyIsAdministratorExceptions(
            Exception exception,
            WebRequest request
    ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PendingUserException.class)
    public final ResponseEntity<ExceptionResponse> handleUserPendingExceptions(
            Exception exception,
            WebRequest request
    ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

}

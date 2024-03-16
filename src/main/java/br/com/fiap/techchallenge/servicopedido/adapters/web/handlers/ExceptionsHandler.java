package br.com.fiap.techchallenge.servicopedido.adapters.web.handlers;

import br.com.fiap.techchallenge.servicopedido.core.domain.exceptions.BadRequestException;
import br.com.fiap.techchallenge.servicopedido.core.domain.exceptions.EntityAlreadyExistException;
import br.com.fiap.techchallenge.servicopedido.core.domain.exceptions.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

import java.util.Objects;

@ControllerAdvice
public class ExceptionsHandler {
    Logger logger = LogManager.getLogger(ExceptionsHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        var errorDetails = new ErrorDetails.Builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getAllErrors().get(0).getDefaultMessage())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> handlerHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        var errorDetails = new ErrorDetails.Builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(Objects.requireNonNull(e.getRootCause()).getMessage())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> handlerBadRequestException(BadRequestException e, HttpServletRequest request) {
        var errorDetails = new ErrorDetails.Builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorDetails> handlerEntityAlreadyExistException(EntityAlreadyExistException e, HttpServletRequest request) {
        var errorDetails = new ErrorDetails.Builder()
                .status(HttpStatus.CONFLICT.value())
                .message(e.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDetails);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDetails> handlerEntityNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
        var errorDetails = new ErrorDetails.Builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDetails> handlerException(Exception e, HttpServletRequest request) {
        var errorDetails = new ErrorDetails.Builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        logger.error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> handlerNumberFormatException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        var errorDetails = new ErrorDetails.Builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> handlerIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        var errorDetails = new ErrorDetails.Builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> handlerMultipartException(MultipartException e, HttpServletRequest request) {
        var errorDetails = new ErrorDetails.Builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

}

package cl.dpichinil.demo.backend.config.exception;

import cl.dpichinil.demo.backend.dto.ResponseDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto> handleCustomException(CustomException ex) {
        HttpStatus status = ex.status != null ? ex.status : HttpStatus.INTERNAL_SERVER_ERROR;
        log.warn("Handled CustomException: {}", ex.getMessage());
        ResponseDto body = new ResponseDto(Boolean.FALSE, ex.getMessage());
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseDto> handleBadCredentialsException(BadCredentialsException ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        log.warn("Handled BadCredentialsException: {}", ex.getMessage());
        ResponseDto body = new ResponseDto(Boolean.FALSE, ex.getMessage());
        return new ResponseEntity<>(body, status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatusCode status,
                                                                   WebRequest request) {
        log.warn("Handled NoHandlerFoundException: {}", ex.getRequestURL());
        ResponseDto body = new ResponseDto(Boolean.FALSE, "El recurso solicitado no fue encontrado.");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        fieldErrors.forEach(fe -> errors.put(fe.getField(), fe.getDefaultMessage()));

        log.debug("Validation failed: {}", errors);

        ResponseDto body = new ResponseDto(Boolean.FALSE, "Validation failed", errors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Handled IllegalArgumentException: {}", ex.getMessage());
        ResponseDto body = new ResponseDto(Boolean.FALSE, ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleAll(Exception ex) {
        log.error("Unhandled exception caught by global handler", ex);
        ResponseDto body = new ResponseDto(Boolean.FALSE, "Internal server error");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    

}

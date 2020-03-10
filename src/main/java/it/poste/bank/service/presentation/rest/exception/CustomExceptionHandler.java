package it.poste.bank.service.presentation.rest.exception;

import io.opentracing.Tracer;
import it.poste.bank.service.presentation.rest.config.CustomErrorResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
@Slf4j
    public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String TAG_ERROR = "error";
    private static final String TAG_EXCEPTION = "exception";

    @Autowired
    private Tracer tracer;
    

    /**
     * Intercetta le exception sollevate e tagga in Jaeger il messaggio
     * contenuto nell'exception
     */
    private ResponseEntity<Object> interceptException(Exception exception, HttpStatus statusCode, String errorCode, String errorDescription) {
        log.debug("ExceptionHandler - ", exception);
        tracer.activeSpan().setTag(TAG_ERROR, true);
        tracer.activeSpan().setTag(TAG_EXCEPTION, exception.toString());
        tracer.activeSpan().log(Collections.singletonMap(TAG_EXCEPTION, exception.toString()));

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(errorCode);
        errors.setErrorDescription(errorDescription);
        errors.setStatus(statusCode.value());

        return new ResponseEntity<>(errors, statusCode);
    }

    
    private ResponseEntity<Object> interceptException(Exception exception, HttpStatus statusCode, String errorDescription) {
        String errorCode = null;
        return interceptException(exception, statusCode, errorCode, errorDescription);
    }
    
    
    
    /**
     * Default exception handler Convert a predefined exception to an HTTP
     * Status code
     *
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad request")
    public ResponseEntity<Object> defaultException(Exception exception) throws Exception {
        return interceptException(exception, HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * Custom exception handler Convert runtime exceptions to an HTTP Status
     * code
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({NullPointerException.class, ArrayIndexOutOfBoundsException.class, IOException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal server error")
    public ResponseEntity<Object> intern(Exception exception) {
        return interceptException(exception, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    }

    /**
     * Custom exception handler Convert resource not found exception to an HTTP
     * Status code
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({EntityNotFoundException.class})  
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
        public ResponseEntity<Object> notFoundException(Exception exception) {
        return interceptException(exception, HttpStatus.NOT_FOUND, "Resource not found");
    }

}

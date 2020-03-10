package it.poste.bank.service.presentation.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal server error")
public class InvalidException extends RuntimeException {

    private static final long serialVersionUID = -1176909170633019929L;

    public InvalidException(Exception e) {
        super("Internal server error: ", e);
    }
}

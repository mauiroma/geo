package it.poste.bank.service.presentation.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such item")
public class EntityNotFoundException extends RuntimeException {

    /**
     * generated
     */
    private static final long serialVersionUID = -3115016356570305748L;

    /**
     * @param entity The entity causing the exception.
     * @param id The entity id causing the exception.
     */
    public EntityNotFoundException(String entity, String id) {
        super("entity " + entity + " not found for id " + id);
    }

    public EntityNotFoundException(String msg) {
        super(msg);
    }

}

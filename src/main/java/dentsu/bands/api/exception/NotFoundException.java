package dentsu.bands.api.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_FOUND)
public class NotFoundException extends IllegalStateException {

    public NotFoundException(final String message) {
        super("The desired resource does not exist: " + (message.isBlank() ? "unknown" : message));
    }

}

package dentsu.bands.http.exception;

/**
 * {@link JsonParsingException} is thrown when parsing JSON encountered any
 * problem.
 */
public class JsonParsingException extends RuntimeException {

    public JsonParsingException() {
        super("There was a problem parsing the external api response into a JSON");
    }

}

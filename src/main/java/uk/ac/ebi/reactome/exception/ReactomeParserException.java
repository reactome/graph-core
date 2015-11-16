package uk.ac.ebi.reactome.exception;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 15.11.15.
 */
public class ReactomeParserException extends Exception {

    protected ReactomeParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ReactomeParserException() {
        super();
    }

    public ReactomeParserException(String message) {
        super(message);
    }

    public ReactomeParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReactomeParserException(Throwable cause) {
        super(cause);
    }
}


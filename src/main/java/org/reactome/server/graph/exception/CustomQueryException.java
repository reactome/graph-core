package org.reactome.server.graph.exception;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */

public class CustomQueryException extends Exception {

    public CustomQueryException(String message) {
        super(message);
    }

    public CustomQueryException(Throwable cause) {
        super(cause);
    }

}

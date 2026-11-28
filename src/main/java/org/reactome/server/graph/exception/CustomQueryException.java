package org.reactome.server.graph.exception;

/**
 * @author Guilherme S Viteri &lt;gviteri@ebi.ac.uk&gt;
 */
public class CustomQueryException extends Exception {
    private static final long serialVersionUID = 1L;

    public CustomQueryException(String message) {
        super(message);
    }

    public CustomQueryException(Throwable cause) {
        super(cause);
    }
}

package br.com.nozella.exception;

public class SplitTextException extends Exception {
    private final int code;

    public SplitTextException(final int code, final String message, final Throwable t) {
        super(message, t);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

package io.github.suxil.rsql.exception;

/**
 * rsql 解析通用异常
 *
 * @author lu_it
 * @since V1.0
 */
public class RSQLCommonException extends RuntimeException {

    public RSQLCommonException() {

    }

    public RSQLCommonException(String message) {
        super(message);
    }

    public RSQLCommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public RSQLCommonException(Throwable cause) {
        super(cause);
    }

}

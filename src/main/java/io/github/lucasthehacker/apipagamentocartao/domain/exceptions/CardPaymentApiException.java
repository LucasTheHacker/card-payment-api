package io.github.lucasthehacker.apipagamentocartao.domain.exceptions;

public class CardPaymentApiException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CardPaymentApiException(String msg) {
        super(msg);
    }

}

package io.github.lucasthehacker.apipagamentocartao.domain.interfaces;

import io.github.lucasthehacker.apipagamentocartao.domain.exceptions.CardPaymentApiException;

public interface IFieldTypeValidation {

    boolean isLong(String str) throws CardPaymentApiException;

    boolean isDouble(String str) throws CardPaymentApiException;
}

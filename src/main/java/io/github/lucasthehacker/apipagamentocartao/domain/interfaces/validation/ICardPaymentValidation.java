package io.github.lucasthehacker.apipagamentocartao.domain.interfaces.validation;

import io.github.lucasthehacker.apipagamentocartao.persistence.entitity.CardPayment;
import io.github.lucasthehacker.apipagamentocartao.domain.exceptions.CardPaymentApiException;

public interface ICardPaymentValidation {

    void valorPagamentoValidation(CardPayment cardPayment) throws CardPaymentApiException;

    void cPFCNPJValidationPadronization(CardPayment cardPayment) throws CardPaymentApiException;

    void cardValidationPadronization(CardPayment cardPayment) throws  CardPaymentApiException;

    void personTypeValidation(CardPayment cardPayment) throws CardPaymentApiException;

    void cardDateValidation(CardPayment cardPayment) throws CardPaymentApiException;

    void cVVValidationPadronization (CardPayment cardPayment) throws CardPaymentApiException;

}

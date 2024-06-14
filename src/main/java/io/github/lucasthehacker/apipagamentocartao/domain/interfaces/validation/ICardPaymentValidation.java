package io.github.lucasthehacker.apipagamentocartao.domain.interfaces.validation;


import io.github.lucasthehacker.apipagamentocartao.domain.exceptions.CardPaymentApiException;
import io.github.lucasthehacker.apipagamentocartao.domain.models.CardPaymentModel;

public interface ICardPaymentValidation {

    void valorPagamentoValidation(CardPaymentModel cardPayment) throws CardPaymentApiException;

    void cPFCNPJValidationPadronization(CardPaymentModel cardPayment) throws CardPaymentApiException;

    void cardValidationPadronization(CardPaymentModel cardPayment) throws  CardPaymentApiException;

    void personTypeValidation(CardPaymentModel cardPayment) throws CardPaymentApiException;

    void cardDateValidation(CardPaymentModel cardPayment) throws CardPaymentApiException;

    void cVVValidationPadronization (CardPaymentModel cardPayment) throws CardPaymentApiException;

}

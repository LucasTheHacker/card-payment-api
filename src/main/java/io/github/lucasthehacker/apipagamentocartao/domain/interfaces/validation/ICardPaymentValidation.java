package io.github.lucasthehacker.apipagamentocartao.domain.interfaces.validation;


import io.github.lucasthehacker.apipagamentocartao.domain.exceptions.CardPaymentApiException;
import io.github.lucasthehacker.apipagamentocartao.domain.models.CardPaymentModel;

public interface ICardPaymentValidation {

    void valorPagamentoValidation(CardPaymentModel cardPaymentModel) throws CardPaymentApiException;

    void cPFCNPJValidationPadronization(CardPaymentModel cardPaymentModel) throws CardPaymentApiException;

    void cardValidationPadronization(CardPaymentModel cardPaymentModel) throws  CardPaymentApiException;

    void personTypeValidation(CardPaymentModel cardPaymentModel) throws CardPaymentApiException;

    void cardDateValidation(CardPaymentModel cardPaymentModel) throws CardPaymentApiException;

    void cVVValidationPadronization(CardPaymentModel cardPaymentModel) throws CardPaymentApiException;

}

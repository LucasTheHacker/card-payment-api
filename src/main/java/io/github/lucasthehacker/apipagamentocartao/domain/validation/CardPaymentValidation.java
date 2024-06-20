package io.github.lucasthehacker.apipagamentocartao.domain.validation;

import io.github.lucasthehacker.apipagamentocartao.domain.models.CardPaymentModel;
import io.github.lucasthehacker.apipagamentocartao.domain.exceptions.CardPaymentApiException;
import io.github.lucasthehacker.apipagamentocartao.domain.interfaces.validation.ICardPaymentValidation;
import io.github.lucasthehacker.apipagamentocartao.domain.interfaces.validation.IFieldTypeValidation;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;

@RequestScoped
public class CardPaymentValidation implements IFieldTypeValidation, ICardPaymentValidation {

    @Inject
    CardPaymentModel cardPaymentModel;

    public CardPaymentValidation(){}

    public boolean applyValidations(CardPaymentModel cardPaymentModel) {
        try {
            valorPagamentoValidation(cardPaymentModel);

            cPFCNPJValidationPadronization(cardPaymentModel);

            cardValidationPadronization(cardPaymentModel);

            personTypeValidation(cardPaymentModel);

            cardDateValidation(cardPaymentModel);

            cVVValidationPadronization(cardPaymentModel);

            Log.info("Validation Succeed");
            return true;
        }
        catch (Throwable t) {
            Log.debug("Un error ocurred in validation: " + t.getMessage());
            return false;
        }
    }

    @Override
    public boolean isLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void valorPagamentoValidation(CardPaymentModel cardPaymentModel) throws CardPaymentApiException {

        cardPaymentModel.setValorPagamento(cardPaymentModel.getValorPagamento().replace(",", "."));

        if (!isDouble(cardPaymentModel.getValorPagamento())) {
            throw new CardPaymentApiException("The value of valorPagamento must be double");
        }
    }

    @Override
    public void cPFCNPJValidationPadronization(CardPaymentModel cardPaymentModel)  throws  CardPaymentApiException {

        if (cardPaymentModel.getTipoPessoa() == 1) {
            if (cardPaymentModel.getCPFCNPJCliente().length() > 17) {  //Aceita com espaço no final
                throw new CardPaymentApiException("CPF is longer than expected");
            }
            String cPFCNPJ = cardPaymentModel.getCPFCNPJCliente().trim().replace(".", "").replace("-", "").replace("/", "").replace(" ", "");
            if (isLong(cPFCNPJ)) {
                cardPaymentModel.setCPFCNPJCliente(cPFCNPJ);
            }
            else {
                throw new CardPaymentApiException("CPF must be numeric");
            }

        }
        else {
            if (cardPaymentModel.getCPFCNPJCliente().length() > 20) {  //Aceita com espaço no final
                throw new CardPaymentApiException("CNPJ is longer than expected");
            }
            String cPFCNPJ = cardPaymentModel.getCPFCNPJCliente().trim().replace(".", "").replace("-", "").replace("/", "").replace(" ", "");
            if (isLong(cPFCNPJ)) {
                cardPaymentModel.setCPFCNPJCliente(cPFCNPJ);
            }
            else {
                throw new CardPaymentApiException("CNPJ must be numeric");
            }
        }
    }

    @Override
    public void cardValidationPadronization(CardPaymentModel cardPaymentModel) throws CardPaymentApiException {
        if (cardPaymentModel.getNumeroCartao().length() > 21) {
            throw new CardPaymentApiException("Card number is longer than expected");
        }
        String cardNumber = cardPaymentModel.getNumeroCartao().trim().replace(".", "").replace("-", "");
        if (isLong(cardNumber)) {
            cardPaymentModel.setNumeroCartao(cardNumber);
        }
        else {
            throw new CardPaymentApiException("Card number must be numeric");
        }
    }

    @Override
    public void personTypeValidation(CardPaymentModel cardPaymentModel) throws CardPaymentApiException {

        if (!(cardPaymentModel.getTipoPessoa() == 1 || cardPaymentModel.getTipoPessoa() == 2)) {
            throw new CardPaymentApiException("Person Type must be '1' for PF or '2' for PJ");
        }
    }

    @Override
    public void cardDateValidation(CardPaymentModel cardPaymentModel) throws CardPaymentApiException {
        Integer mes = cardPaymentModel.getMesVencimentoCartao();
        Integer ano = cardPaymentModel.getAnoVencimentoCartao();

        Integer anoAtual = LocalDate.now().getYear();

        if (ano.equals(anoAtual)) {
            if (mes >= LocalDate.now().getMonthValue()) {
                throw new CardPaymentApiException("Card is expired");
            }
        }
        else if (ano < anoAtual) {
            throw new CardPaymentApiException("Card is expired");
        }
    }

    @Override
    public void cVVValidationPadronization (CardPaymentModel cardPaymentModel) throws CardPaymentApiException {
        if (cardPaymentModel.getCVV().length() > 6) {
            throw new CardPaymentApiException("CVV is longer than expected");
        }
        else {
            String cVV = cardPaymentModel.getCVV().trim();
            if (isLong(cVV)) {
                cardPaymentModel.setCVV(cVV);
            } else {
                throw new CardPaymentApiException("Unexpected error in CVV");
            }
        }
    }
}

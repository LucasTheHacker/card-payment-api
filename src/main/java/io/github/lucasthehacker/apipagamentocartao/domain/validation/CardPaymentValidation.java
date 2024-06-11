package io.github.lucasthehacker.apipagamentocartao.domain.validation;

import io.github.lucasthehacker.apipagamentocartao.persistence.entitity.CardPayment;
import io.github.lucasthehacker.apipagamentocartao.domain.exceptions.CardPaymentApiException;
import io.github.lucasthehacker.apipagamentocartao.domain.interfaces.validation.ICardPaymentValidation;
import io.github.lucasthehacker.apipagamentocartao.domain.interfaces.validation.IFieldTypeValidation;

import java.time.LocalDate;

public class CardPaymentValidation implements IFieldTypeValidation, ICardPaymentValidation {

    public boolean isLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void valorPagamentoValidation(CardPayment cardPayment) throws CardPaymentApiException {

        cardPayment.setValorPagamento(cardPayment.getValorPagamento().replace(",", "."));

        if (!isDouble(cardPayment.getValorPagamento())) {
            throw new CardPaymentApiException("The value of valorPagamento must be double");
        }
    }

    public void cPFCNPJValidationPadronization(CardPayment cardPayment)  throws  CardPaymentApiException {

        if (cardPayment.getTipoPessoa() == 1) {
            if (cardPayment.getcPFCNPJCliente().length() > 17) {  //Aceita com espaço no final
                throw new CardPaymentApiException("CPF is longer than expected");
            }
            String cPFCNPJ = cardPayment.getcPFCNPJCliente().trim().replace(".", "").replace("-", "").replace("/", "").replace(" ", "");
            if (isLong(cPFCNPJ)) {
                cardPayment.setcPFCNPJCliente(cPFCNPJ);
            }
            else {
                throw new CardPaymentApiException("CPF must be numeric");
            }

        }
        else {
            if (cardPayment.getcPFCNPJCliente().length() > 20) {  //Aceita com espaço no final
                throw new CardPaymentApiException("CNPJ is longer than expected");
            }
            String cPFCNPJ = cardPayment.getcPFCNPJCliente().trim().replace(".", "").replace("-", "").replace("/", "").replace(" ", "");
            if (isLong(cPFCNPJ)) {
                cardPayment.setcPFCNPJCliente(cPFCNPJ);
            }
            else {
                throw new CardPaymentApiException("CNPJ must be numeric");
            }
        }
    }

    public void cardValidationPadronization(CardPayment cardPayment) throws CardPaymentApiException {
        if (cardPayment.getNumeroCartao().length() > 21) {
            throw new CardPaymentApiException("Card number is longer than expected");
        }
        String cardNumber = cardPayment.getNumeroCartao().trim().replace(".", "").replace("-", "");
        if (isLong(cardNumber)) {
            cardPayment.setNumeroCartao(cardNumber);
        }
        else {
            throw new CardPaymentApiException("Card number must be numeric");
        }
    }

    public void personTypeValidation(CardPayment cardPayment) throws CardPaymentApiException {

        if (!(cardPayment.getTipoPessoa() == 1 || cardPayment.getTipoPessoa() == 2)) {
            throw new CardPaymentApiException("Person Type must be '1' for PF or '2' for PJ");
        }
    }

    public void cardDateValidation(CardPayment cardPayment) throws CardPaymentApiException {
        Integer mes = cardPayment.getMesVencimentoCartao();
        Integer ano = cardPayment.getAnoVencimentoCartao();

        Integer anoAtual = LocalDate.now().getYear();

        if (ano == anoAtual ) {
            if (mes >= LocalDate.now().getMonthValue()) {
                throw new CardPaymentApiException("Card is expired");
            }
        }
        else if (ano < anoAtual) {
            throw new CardPaymentApiException("Card is expired");
        }
    }

    public void cVVValidationPadronization (CardPayment cardPayment) throws CardPaymentApiException {
        if (cardPayment.getcVV().length() > 6) {
            throw new CardPaymentApiException("CVV is longer than expected");
        }
        else {
            String cVV = cardPayment.getcVV().trim();
            if (isLong(cVV)) {
                cardPayment.setcVV(cVV);
            } else {
                throw new CardPaymentApiException("Unexpected error in CVV");
            }
        }
    }
}

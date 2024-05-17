package io.github.lucasthehacker.apipagamentocartao.domain.model.services;

import io.github.lucasthehacker.apipagamentocartao.domain.model.entities.CardPayment;
import io.github.lucasthehacker.apipagamentocartao.domain.model.exceptions.DomainException;
import java.time.LocalDate;

import com.aayushatharva.brotli4j.common.annotations.Local;

public class Validation {

    public static boolean isLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean valorPagamentoValidation(CardPayment cardPayment) {
        cardPayment.setValorPagamento(cardPayment.getValorPagamento().replace(",", "."));
        if (isDouble(cardPayment.getValorPagamento())) {
            return true;
        }
        if (isDouble(cardPayment.getValorPagamento())) {
            return true;
        }
        throw new DomainException("Unexpected error in valorPagamento");
    }

    public static void cPFCNPJValidationPadronization(CardPayment cardPayment) {
        if (cardPayment.getTipoPessoa() == 1) {
            if (cardPayment.getcPFCNPJCliente().length() > 17) {  //Aceita com espaço no final
                throw new DomainException("CPF is longer than expected");
            }
            String cPFCNPJ = cardPayment.getcPFCNPJCliente().trim().replace(".", "").replace("-", "").replace("/", "").replace(" ", "");
            if (isLong(cPFCNPJ)) {
                cardPayment.setcPFCNPJCliente(cPFCNPJ);
            }
            else {
                throw new DomainException("CPF must be numeric");
            }

        }
        else {
            if (cardPayment.getcPFCNPJCliente().length() > 20) {  //Aceita com espaço no final
                throw new DomainException("CNPJ is longer than expected");
            }
            String cPFCNPJ = cardPayment.getcPFCNPJCliente().trim().replace(".", "").replace("-", "").replace("/", "").replace(" ", "");
            if (isLong(cPFCNPJ)) {
                cardPayment.setcPFCNPJCliente(cPFCNPJ);
            }
            else {
                throw new DomainException("CNPJ must be numeric");
            }
        }
    }

    public static void cardValidationPadronization(CardPayment cardPayment) {
        if (cardPayment.getNumeroCartao().length() > 21) {
            throw new DomainException("Card number is longer than expected");
        }
        String cardNumber = cardPayment.getNumeroCartao().trim().replace(".", "").replace("-", "");
        if (isLong(cardNumber)) {
            cardPayment.setNumeroCartao(cardNumber);
        }
        else {
            throw new DomainException("Card number must be numeric");
        }
    }

    public static Integer personTypeValidation(CardPayment cardPayment) {
        if (cardPayment.getTipoPessoa() == 1 || cardPayment.getTipoPessoa() == 2) {
            return cardPayment.getTipoPessoa();
        }
        else {
            throw new DomainException("Person Type must be '1' for PF or '2' for PJ");
        }
    }

    public static void cardDateValidation(CardPayment cardPayment) {
        Integer mes = cardPayment.getMesVencimentoCartao();
        Integer ano = cardPayment.getAnoVencimentoCartao();

        Integer anoAtual = LocalDate.now().getYear();

        if (ano == anoAtual ) {
            if (mes >= LocalDate.now().getMonthValue()) {
                throw new DomainException("Card is expired");
            }
        }
        else if (ano < anoAtual) {
            throw new DomainException("Card is expired");
        }
    }

    public static void cVVValidationPadronization (CardPayment cardPayment) {
        if (cardPayment.getcVV().length() > 6) {
            throw new DomainException("CVV is longer than expected");
        }
        else {
            String cVV = cardPayment.getcVV().trim();
            if (isLong(cVV)) {
                cardPayment.setcVV(cVV);
            } else {
                throw new DomainException("Unexpected error in CVV");
            }
        }
    }
}

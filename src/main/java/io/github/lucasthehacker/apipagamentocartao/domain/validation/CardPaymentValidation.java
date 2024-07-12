package io.github.lucasthehacker.apipagamentocartao.domain.validation;

import io.github.lucasthehacker.apipagamentocartao.domain.models.CardPaymentModel;
import io.github.lucasthehacker.apipagamentocartao.domain.exceptions.CardPaymentApiException;
import io.github.lucasthehacker.apipagamentocartao.domain.interfaces.validation.ICardPaymentValidation;
import io.github.lucasthehacker.apipagamentocartao.domain.interfaces.validation.IFieldTypeValidation;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;

@ApplicationScoped
public class CardPaymentValidation implements IFieldTypeValidation, ICardPaymentValidation {

    public CardPaymentValidation(){}

    public void applyValidations(CardPaymentModel cardPaymentModel) throws CardPaymentApiException {
        Log.info("EXECUTANDO VALIDACAO DE PAGAMENTO");
        valorPagamentoValidation(cardPaymentModel);

        Log.info("EXECUTANDO VALIDACAO DE CPF e CNPJ");
        cPFCNPJValidationPadronization(cardPaymentModel);

        Log.info("EXECUTANDO VALIDACAO NUMERO DE CARTAO");
        cardValidationPadronization(cardPaymentModel);

        Log.info("EXECUTANDO VALIDACAO DE TIPO DE PESSOA");
        personTypeValidation(cardPaymentModel);

        Log.info("EXECUTANDO VALIDACAO DE VALIDADE DO CARTAO");
        cardDateValidation(cardPaymentModel);

        Log.info("EXEXCUTANDO VALIDACAO DE CVV");
        cVVValidationPadronization(cardPaymentModel);

        Log.info("TODAS AS VALIDACOES OCORRERAM COM SUCESSO");
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

            if (cardPaymentModel.getCPFCNPJCliente().length() > 17) {
                throw new CardPaymentApiException("CPF is longer than expected");
            }
            String cPFCNPJ = cardPaymentModel.getCPFCNPJCliente().trim().replace(".", "").replace("-", "").replace("/", "").replace(" ", "");
            if (isLong(cPFCNPJ)) {
                cardPaymentModel.setCPFCNPJCliente(cPFCNPJ);
            }
            else {
                throw new CardPaymentApiException("CPF must be numeric");
            }

            cardPaymentModel.setCPFCNPJCliente(aplicaMacaraCPF(cardPaymentModel.getCPFCNPJCliente()));

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

            cardPaymentModel.setCPFCNPJCliente(aplicaMacaraCNPJ(cardPaymentModel.getCPFCNPJCliente()));
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
            if (mes < LocalDate.now().getMonthValue()) {
                throw new CardPaymentApiException("Error: Card is expired");
            }
        }
        else if (ano < anoAtual) {
            throw new CardPaymentApiException("Error: Card is expired");
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
                throw new CardPaymentApiException("Erro inesperado no CVV. Verifique se foram informados apenas caracteres numericos.");
            }
        }
    }

    public String aplicaMacaraCPF(String cpf) {

        String cpfMascarado = "***" + '.' + cpf.substring(3,6) + '.' + cpf.substring(6,9)  + '-' + "**";

        return cpfMascarado;

    }

    public String aplicaMacaraCNPJ(String cnpj) {

        String cnpjMascarado = cnpj.substring(0,2) + '.' + "***" + '.' + "***" + "/" + cnpj.substring(8,12)  + '-' + cnpj.substring(12);

        return cnpjMascarado;

    }
}

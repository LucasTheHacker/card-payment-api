package io.github.lucasthehacker.apipagamentocartao.domain.models;

import io.github.lucasthehacker.apipagamentocartao.domain.dtos.PaymentRequestDto;
import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
@RequestScoped
public class CardPaymentModel {

    @NotBlank(message = "Card Number may not be blank")
    private String numeroCartao;

    @NotBlank(message = "Person Type may not be blank")
    private Integer tipoPessoa;

    @NotBlank(message = "CPF/CNPJ may not be blank")
    private String cPFCNPJCliente;

    @NotBlank(message = "Expiration month may not be blank")
    private Integer mesVencimentoCartao;

    @NotBlank(message = "Expiration year may not be blank")
    private Integer anoVencimentoCartao;

    @NotBlank(message = "CVV code year may not be blank")
    private String cVV;

    @NotBlank(message = "Payment value may not be blank")
    private String valorPagamento;

    private String date;

    public CardPaymentModel() {}

    public String getCPFCNPJCliente1() {
        return cPFCNPJCliente;
    }

    public void mapper(PaymentRequestDto dto) {

        setNumeroCartao(dto.getNumeroCartao());

        setTipoPessoa(dto.getTipoPessoa());

        setCPFCNPJCliente(dto.getCPFCNPJCliente());

        setMesVencimentoCartao(dto.getMesVencimentoCartao());

        setAnoVencimentoCartao(dto.getAnoVencimentoCartao());

        setCVV(dto.getCVV());

        setValorPagamento(dto.getValorPagamento());

        LocalDateTime horaPagamento = LocalDateTime.now();
        DateTimeFormatter formatadorPagamento = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        setDate(horaPagamento.format(formatadorPagamento));

    }

}

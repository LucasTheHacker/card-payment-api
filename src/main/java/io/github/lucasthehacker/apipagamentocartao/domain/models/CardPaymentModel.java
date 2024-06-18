package io.github.lucasthehacker.apipagamentocartao.domain.models;

import io.github.lucasthehacker.apipagamentocartao.domain.dtos.PaymentRequestDto;
import jakarta.enterprise.context.RequestScoped;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@RequestScoped
public class CardPaymentModel {

    private String numeroCartao;

    private Integer tipoPessoa;

    private String cPFCNPJCliente;

    private Integer mesVencimentoCartao;

    private Integer anoVencimentoCartao;

    private String cVV;

    private String valorPagamento;

    private String date;

    public CardPaymentModel(){}

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

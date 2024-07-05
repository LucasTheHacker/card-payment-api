package io.github.lucasthehacker.apipagamentocartao.domain.models;

import io.github.lucasthehacker.apipagamentocartao.domain.dtos.PaymentRequestDto;
import jakarta.enterprise.context.RequestScoped;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
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

    public void mapper(PaymentRequestDto dto) {

        this.setNumeroCartao(dto.getNumeroCartao());
        this.setTipoPessoa(dto.getTipoPessoa());
        this.setCPFCNPJCliente(dto.getcPFCNPJCliente());
        this.setMesVencimentoCartao(dto.getMesVencimentoCartao());
        this.setAnoVencimentoCartao(dto.getAnoVencimentoCartao());
        this.setCVV(dto.getcVV());
        this.setValorPagamento(dto.getValorPagamento());

        LocalDateTime horaPagamento = LocalDateTime.now();
        DateTimeFormatter formatadorPagamento = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        this.setDate(horaPagamento.format(formatadorPagamento));

    }

}

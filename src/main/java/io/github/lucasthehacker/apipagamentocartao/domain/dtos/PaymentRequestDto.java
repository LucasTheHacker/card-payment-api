package io.github.lucasthehacker.apipagamentocartao.domain.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDto {
    private String numeroCartao;
    private Integer tipoPessoa;
    private String cPFCNPJCliente;
    private Integer mesVencimentoCartao;
    private Integer anoVencimentoCartao;
    private String cVV;
    private String valorPagamento;
}

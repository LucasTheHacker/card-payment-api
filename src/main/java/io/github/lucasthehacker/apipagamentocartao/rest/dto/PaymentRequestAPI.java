package io.github.lucasthehacker.apipagamentocartao.rest.dto;

public class PaymentRequestAPI { 

    private String numeroCartao;

    private Integer tipoPessoa;

    private String cPFCNPJCliente;

    private Integer mesVencimentoCartao;

    private Integer anoVencimentoCartao;

    private String cVV;

    private String valorPagamento;

    public String getValorPagamento() {
        return valorPagamento;
    }

    public void setValorPagamento(String valorPagamento) {
        this.valorPagamento = valorPagamento;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public Integer getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(Integer tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getcPFCNPJCliente() {
        return cPFCNPJCliente;
    }

    public void setcPFCNPJCliente(String cPFCNPJCliente) {
        this.cPFCNPJCliente = cPFCNPJCliente;
    }

    public Integer getMesVencimentoCartao() {
        return mesVencimentoCartao;
    }

    public void setMesVencimentoCartao(Integer mesVencimentoCartao) {
        this.mesVencimentoCartao = mesVencimentoCartao;
    }

    public Integer getAnoVencimentoCartao() {
        return anoVencimentoCartao;
    }

    public void setAnoVencimentoCartao(Integer anoVencimentoCartao) {
        this.anoVencimentoCartao = anoVencimentoCartao;
    }

    public String getcVV() {
        return cVV;
    }

    public void setcVV(String cVV) {
        this.cVV = cVV;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((numeroCartao == null) ? 0 : numeroCartao.hashCode());
        result = prime * result + ((tipoPessoa == null) ? 0 : tipoPessoa.hashCode());
        result = prime * result + ((cPFCNPJCliente == null) ? 0 : cPFCNPJCliente.hashCode());
        result = prime * result + ((mesVencimentoCartao == null) ? 0 : mesVencimentoCartao.hashCode());
        result = prime * result + ((anoVencimentoCartao == null) ? 0 : anoVencimentoCartao.hashCode());
        result = prime * result + ((cVV == null) ? 0 : cVV.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PaymentRequestAPI other = (PaymentRequestAPI) obj;
        if (numeroCartao == null) {
            if (other.numeroCartao != null)
                return false;
        } else if (!numeroCartao.equals(other.numeroCartao))
            return false;
        if (tipoPessoa == null) {
            if (other.tipoPessoa != null)
                return false;
        } else if (!tipoPessoa.equals(other.tipoPessoa))
            return false;
        if (cPFCNPJCliente == null) {
            if (other.cPFCNPJCliente != null)
                return false;
        } else if (!cPFCNPJCliente.equals(other.cPFCNPJCliente))
            return false;
        if (mesVencimentoCartao == null) {
            if (other.mesVencimentoCartao != null)
                return false;
        } else if (!mesVencimentoCartao.equals(other.mesVencimentoCartao))
            return false;
        if (anoVencimentoCartao == null) {
            if (other.anoVencimentoCartao != null)
                return false;
        } else if (!anoVencimentoCartao.equals(other.anoVencimentoCartao))
            return false;
        if (cVV == null) {
            if (other.cVV != null)
                return false;
        } else if (!cVV.equals(other.cVV))
            return false;
        return true;
    }
}

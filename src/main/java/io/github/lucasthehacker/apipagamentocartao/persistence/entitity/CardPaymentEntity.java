package io.github.lucasthehacker.apipagamentocartao.persistence.entitity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "Pagamentos")
@NamedNativeQueries({
        @NamedNativeQuery(name = "CONSULTAR_PAGAMENTO_POR_ID", query = "SELECT Id, NumeroCartao , TipoPessoa, CPFCNPJCliente, MesVencimentoCartao, AnoVencimentoCartao, CVV, ValorPagamento, DataPagamento, FROM Pagamentos WHERE Id = :Id ", resultClass = CardPaymentEntity.class),
        @NamedNativeQuery(name = "CRIA_PAGAMENTO", query = "INSERT INTO Pagamentos (NumeroCartao, TipoPessoa, CPFCNPJCliente, MesVencimentoCartao, AnoVencimentoCartao, CVV, ValorPagamento, DataPagamento) VALUES (:NumeroCartao :TipoPessoa, :CPFCNPJCliente, :MesVencimentoCartao, :AnoVencimentoCartao, :CVV, :ValorPagamento, :DataPagamento ) "),
        @NamedNativeQuery(name = "LISTA_PAGAMENTOS", query = "SELECT Id, NumeroCartao, TipoPessoa, CPFCNPJCliente, MesVnecimentoCartao, AnoVencimentoCartao, CVV, ValorPagamento, DataPagamento FROM Pagamentos")
})
@RequestScoped
public class CardPaymentEntity extends PanacheEntityBase implements AutoCloseable {  //Try-catch support

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer numeroPagamento; 

    @Column(name = "NumeroCartao")
    private String numeroCartao;

    @Column(name = "TipoPessoa")
    private Integer tipoPessoa;

    @Column(name = "CPFCNPJCliente")
    private String cPFCNPJCliente;

    @Column(name = "MesVencimentoCartao")
    private Integer mesVencimentoCartao;

    @Column(name = "AnoVencimentoCartao")
    private Integer anoVencimentoCartao;

    @Column(name = "CVV")
    private String cVV;

    @Column(name = "ValorPagamento")
    private String valorPagamento;

    @Column(name = "DataPagamento")
    private String dataPagamento;

    
    public CardPaymentEntity() {
        LocalDateTime horaPagamento = LocalDateTime.now();
        DateTimeFormatter formatadorPagamento = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        setDataPagamento(horaPagamento.format(formatadorPagamento));
    }

    @Override
    public String toString() {
        return "CardPayment [numeroPagamento=" + numeroPagamento + ", numeroCartao=" + numeroCartao + ", tipoPessoa="
                + tipoPessoa + ", cPFCNPJCliente=" + cPFCNPJCliente + ", mesVencimentoCartao=" + mesVencimentoCartao
                + ", anoVencimentoCartao=" + anoVencimentoCartao + ", cVV=" + cVV + ", valorPagamento=" + valorPagamento
                + ", dataPagamento=" + dataPagamento + ", getValorPagamento()=" + getValorPagamento()
                + ", getDataPagamento()=" + getDataPagamento() + ", getNumeroPagamento()=" + getNumeroPagamento()
                + ", getNumeroCartao()=" + getNumeroCartao() + ", getTipoPessoa()=" + getTipoPessoa()
                + ", getcPFCNPJCliente()=" + getCPFCNPJCliente() + ", getClass()=" + getClass()
                + ", getMesVencimentoCartao()=" + getMesVencimentoCartao() + ", getAnoVencimentoCartao()="
                + getAnoVencimentoCartao() + ", isPersistent()=" + isPersistent() + ", getcVV()=" + getCVV()
                + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((numeroPagamento == null) ? 0 : numeroPagamento.hashCode());
        result = prime * result + ((numeroCartao == null) ? 0 : numeroCartao.hashCode());
        result = prime * result + ((tipoPessoa == null) ? 0 : tipoPessoa.hashCode());
        result = prime * result + ((cPFCNPJCliente == null) ? 0 : cPFCNPJCliente.hashCode());
        result = prime * result + ((mesVencimentoCartao == null) ? 0 : mesVencimentoCartao.hashCode());
        result = prime * result + ((anoVencimentoCartao == null) ? 0 : anoVencimentoCartao.hashCode());
        result = prime * result + ((cVV == null) ? 0 : cVV.hashCode());
        result = prime * result + ((valorPagamento == null) ? 0 : valorPagamento.hashCode());
        result = prime * result + ((dataPagamento == null) ? 0 : dataPagamento.hashCode());
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
        CardPaymentEntity other = (CardPaymentEntity) obj;
        if (numeroPagamento == null) {
            if (other.numeroPagamento != null)
                return false;
        } else if (!numeroPagamento.equals(other.numeroPagamento))
            return false;
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
        if (valorPagamento == null) {
            if (other.valorPagamento != null)
                return false;
        } else if (!valorPagamento.equals(other.valorPagamento))
            return false;
        if (dataPagamento == null) {
            if (other.dataPagamento != null)
                return false;
        } else if (!dataPagamento.equals(other.dataPagamento))
            return false;
        return true;
    }

    @Override
    public void close() throws Exception {
        throw new UnsupportedOperationException("Unimplemented method 'close'");
    }
    
}

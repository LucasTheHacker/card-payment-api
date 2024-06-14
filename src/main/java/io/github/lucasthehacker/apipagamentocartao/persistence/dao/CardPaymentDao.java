package io.github.lucasthehacker.apipagamentocartao.persistence.dao;

import io.github.lucasthehacker.apipagamentocartao.domain.models.CardPaymentModel;
import io.github.lucasthehacker.apipagamentocartao.persistence.entitity.CardPaymentEntity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequestScoped
public class CardPaymentDao {

    @Inject
    EntityManager entityManager;

    public CardPaymentDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<CardPaymentEntity> listaPagamentos() {

        TypedQuery<CardPaymentEntity> query = entityManager.createNamedQuery("LISTA_PAGAMENTOS", CardPaymentEntity.class);

        return query.getResultList();
    }

    @Transactional
    public CardPaymentModel novoPagamento(CardPaymentModel cardPaymentModel){

        Query query = entityManager.createNamedQuery("CRIA_PAGAMENTO");

        LocalDateTime horaPagamento = LocalDateTime.now();
        DateTimeFormatter formatadorPagamento = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

        query.setParameter("NumeroCartao", cardPaymentModel.getNumeroCartao());
        query.setParameter("TipoPessoa", cardPaymentModel.getTipoPessoa());
        query.setParameter("CPFCNPJCliente", cardPaymentModel.getCPFCNPJCliente());
        query.setParameter("MesVencimentoCartao", cardPaymentModel.getMesVencimentoCartao());
        query.setParameter("AnoVencimentoCartao", cardPaymentModel.getAnoVencimentoCartao());
        query.setParameter("CVV", cardPaymentModel.getCVV());
        query.setParameter("ValorPagamento", cardPaymentModel.getValorPagamento());
        query.setParameter("DataPagamento", horaPagamento.format(formatadorPagamento));

        query.executeUpdate();

        return cardPaymentModel;

    }

}

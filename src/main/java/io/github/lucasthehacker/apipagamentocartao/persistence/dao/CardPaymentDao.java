package io.github.lucasthehacker.apipagamentocartao.persistence.dao;

import io.github.lucasthehacker.apipagamentocartao.domain.dtos.PaymentRequestDto;
import io.github.lucasthehacker.apipagamentocartao.domain.exceptions.CardPaymentApiException;
import io.github.lucasthehacker.apipagamentocartao.domain.models.CardPaymentModel;
import io.github.lucasthehacker.apipagamentocartao.persistence.entitity.CardPaymentEntity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import net.bytebuddy.implementation.bytecode.Throw;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequestScoped
public class CardPaymentDao {

    @Inject
    EntityManager entityManager;

    @Inject
    CardPaymentModel cardPaymentModel;

    public CardPaymentDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<CardPaymentEntity> listaPagamentos() {

        TypedQuery<CardPaymentEntity> query = entityManager.createNamedQuery("LISTA_PAGAMENTOS", CardPaymentEntity.class);

        return query.getResultList();
    }

    @Transactional
    public CardPaymentModel novoPagamento(){

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

    public CardPaymentEntity buscaPagamentoPorId(int idPgto) throws CardPaymentApiException{

        try {
            TypedQuery<CardPaymentEntity> query = entityManager.createNamedQuery("CONSULTAR_PAGAMENTO_POR_ID", CardPaymentEntity.class);
            query.setParameter("Id", idPgto);
            return query.getSingleResult();
        } catch (Exception e) {
            throw  new CardPaymentApiException("Um erro ocorreu ao tentar buscar o pagamento na base de dados");
    }
    }

    public List<CardPaymentEntity> buscaTodosPagamentos() throws CardPaymentApiException {
        try {
            TypedQuery<CardPaymentEntity> query = entityManager.createNamedQuery("LISTA_PAGAMENTOS", CardPaymentEntity.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new CardPaymentApiException("Um erro ocorreu ao tentar listar os pagamentos na base de dados");
        }
    }

    public CardPaymentEntity atualizaPagamento(CardPaymentModel model, int idPgto) {

        Query query = entityManager.createNamedQuery("ATUALIZA_PAGAMENTO");
        query.setParameter("NumeroCartao", model.getNumeroCartao());
        query.setParameter("TipoPessoa", model.getTipoPessoa());
        query.setParameter("CPFCNPJCliente", model.getCPFCNPJCliente());
        query.setParameter("MesVencimentoCartao", model.getMesVencimentoCartao());
        query.setParameter("AnoVencimentoCartao", model.getAnoVencimentoCartao());
        query.setParameter("CVV", model.getCVV());
        query.setParameter("ValorPagamento", model.getValorPagamento());

        LocalDateTime horaPagamento = LocalDateTime.now();
        DateTimeFormatter formatadorPagamento = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        query.setParameter("DataPagamento", horaPagamento.format(formatadorPagamento));

        query.executeUpdate();

        return  buscaPagamentoPorId(idPgto);
    }

//    public Conta buscaContaPorNumero(int numConta) {
//        try {
//            TypedQuery<Conta> query = em.createNamedQuery("CONSULTAR_CONTA_NUMERO", Conta.class);
//            query.setParameter("numConta", numConta);
//            return query.getSingleResult();
//        } catch (NoResultException e) {
//            throw new NoResultException("Conta não encontrada.");
//        }
//    }

}

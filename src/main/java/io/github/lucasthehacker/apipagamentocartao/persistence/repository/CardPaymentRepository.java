package io.github.lucasthehacker.apipagamentocartao.persistence.repository;

import io.github.lucasthehacker.apipagamentocartao.domain.models.CardPaymentModel;
import io.github.lucasthehacker.apipagamentocartao.persistence.dao.CardPaymentDao;
import io.github.lucasthehacker.apipagamentocartao.persistence.entitity.CardPaymentEntity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequestScoped
public class CardPaymentRepository {

    @Inject
    CardPaymentModel cardPaymentModel;

    @Inject
    CardPaymentEntity cardPaymentEntity;

    @Inject
    CardPaymentDao cardPaymentDao;

    public void persistePagamento() {

        cardPaymentEntity.setCVV(cardPaymentModel.getCVV());

        cardPaymentEntity.setNumeroCartao(cardPaymentModel.getNumeroCartao());

        cardPaymentEntity.setCPFCNPJCliente(cardPaymentModel.getCPFCNPJCliente());

        LocalDateTime horaPagamento = LocalDateTime.now();
        DateTimeFormatter formatadorPagamento = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

        cardPaymentEntity.setDataPagamento(horaPagamento.format(formatadorPagamento));

        cardPaymentEntity.setCVV(cardPaymentModel.getCVV());

        cardPaymentEntity.setTipoPessoa(cardPaymentModel.getTipoPessoa());

        cardPaymentEntity.setValorPagamento(cardPaymentModel.getValorPagamento());

        cardPaymentEntity.setAnoVencimentoCartao(cardPaymentModel.getAnoVencimentoCartao());

        cardPaymentEntity.setMesVencimentoCartao(cardPaymentModel.getMesVencimentoCartao());

    }

    public List<CardPaymentEntity> buscaPagamentos() {
        return cardPaymentDao.listaPagamentos();
    }


}

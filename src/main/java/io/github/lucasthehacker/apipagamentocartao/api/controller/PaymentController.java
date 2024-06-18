package io.github.lucasthehacker.apipagamentocartao.api.controller;

import io.github.lucasthehacker.apipagamentocartao.domain.exceptions.CardPaymentApiException;
import io.github.lucasthehacker.apipagamentocartao.domain.models.CardPaymentModel;
import io.github.lucasthehacker.apipagamentocartao.persistence.dao.CardPaymentDao;
import io.github.lucasthehacker.apipagamentocartao.persistence.entitity.CardPaymentEntity;
import io.github.lucasthehacker.apipagamentocartao.domain.validation.CardPaymentValidation;
import io.github.lucasthehacker.apipagamentocartao.domain.dtos.PaymentRequestDto;
import io.github.lucasthehacker.apipagamentocartao.persistence.repository.CardPaymentRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.bytebuddy.asm.Advice;
import org.h2.schema.Domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;


@Path("/pagamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PaymentController {

    @Inject
    CardPaymentEntity cardPaymentEntity;

    @Inject
    CardPaymentRepository cardPaymentRepository;

    @Inject
    CardPaymentDao cardPaymentDao;

    @Inject
    CardPaymentModel cardPaymentModel;

    @Inject
    CardPaymentValidation cardPaymentValidation;

    


    @POST
    @Transactional
    public Response createPaymentAPI(PaymentRequestDto paymentRequestDto) {

        try {

            cardPaymentModel.mapper(paymentRequestDto);

            cardPaymentRepository.persistePagamento();

            cardPaymentEntity.persist();

            return Response.ok(cardPaymentEntity).build();

        }
        catch (CardPaymentApiException cardPaymentApiException) {
            Log.debug(cardPaymentApiException.getMessage());
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }

    }

    @GET
    public Response requestPaymentDataAPI() {

        try { 
            PanacheQuery<CardPaymentEntity> query = CardPaymentEntity.findAll();;

            return Response.ok(query.list()).build();
        }
        catch (Exception e) {
            Log.debug("Erro while finding payments: " + e.getMessage());
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }
    }

    @PUT
    @Path("/{paymentNumber}")
    @Transactional
    public Response updateCardPayment(@PathParam("paymentNumber") Integer paymentNumber, PaymentRequestDto paymentRequestDto) {

        CardPaymentEntity cardPaymentEntity = CardPaymentEntity.findById(paymentNumber);

        if ( cardPaymentEntity != null ) {

            cardPaymentEntity.setCVV(paymentRequestDto.getCVV());
            cardPaymentEntity.setNumeroCartao(paymentRequestDto.getNumeroCartao());
            cardPaymentEntity.setCPFCNPJCliente(paymentRequestDto.getCPFCNPJCliente());

            LocalDateTime horaPagamento = LocalDateTime.now();
            DateTimeFormatter formatadorPagamento = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

            cardPaymentEntity.setDataPagamento(horaPagamento.format(formatadorPagamento));
            cardPaymentEntity.setCVV(paymentRequestDto.getCVV());
            cardPaymentEntity.setTipoPessoa(paymentRequestDto.getTipoPessoa());
            cardPaymentEntity.setValorPagamento(paymentRequestDto.getValorPagamento());
            cardPaymentEntity.setAnoVencimentoCartao(paymentRequestDto.getAnoVencimentoCartao());
            cardPaymentEntity.setMesVencimentoCartao(paymentRequestDto.getMesVencimentoCartao());

            return Response.ok(cardPaymentEntity).build();
        }

        else {
            return  Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{numeroPagamento}")
    @Transactional
    public Response deleteCardPayment(@PathParam("numeroPagamento") Integer numeroPagamento) {

        CardPaymentEntity cardPayment = CardPaymentEntity.findById(numeroPagamento);

        if (cardPayment != null) {
            cardPayment.delete();
            return Response.ok(cardPayment).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}

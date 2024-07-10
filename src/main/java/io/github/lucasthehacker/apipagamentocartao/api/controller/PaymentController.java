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
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Path("/pagamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PaymentController {

    @Inject
    CardPaymentEntity cardPaymentEntity;

    @Inject
    CardPaymentDao cardPaymentDao;

    @Inject
    CardPaymentValidation cardPaymentValidation;

    @Inject
    CardPaymentModel cardPaymentModel;


    @Operation(
            summary = "Criacao de Pagamento",
            description = "Valida dados recebidos e os persiste no banco de dados")
    @APIResponse(
            responseCode = "201",
            description = "sucesso na criacao de pagamento",
            content = { @Content(mediaType = "application/json")})
    @POST
    @Transactional
    public Response createPaymentAPI(PaymentRequestDto dto) {

        try {

            cardPaymentModel.mapper(dto);

            try {
                cardPaymentValidation.applyValidations(cardPaymentModel);
            }
            catch (CardPaymentApiException e) {
                return Response
                                .status(Response.Status.UNAUTHORIZED.getStatusCode())
                                .entity(e.getMessage())
                                .build();
            }

            return Response
                    .status(Response.Status.CREATED.getStatusCode())
                    .entity(cardPaymentDao.novoPagamento())
                    .build();


        }
        catch (CardPaymentApiException cardPaymentApiException) {
            Log.debug(cardPaymentApiException.getMessage());
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }

    }

    @GET
    public Response requestPaymentDataAPI() {

        try {

            return Response.status(Response.Status.FOUND).entity(cardPaymentDao.buscaTodosPagamentos()).build();

        }
        catch (Exception e) {
            Log.debug("Erro while finding payments: " + e.getMessage());
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }
    }

    @GET
    @Path("/hello")
    public Response requestHello() {

        try {
            return Response.ok("Hello World Test").build();
        }
        catch (Exception e) {
            Log.debug("Erro while finding payments: " + e.getMessage());
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }
    }

    @PUT
    @Path("/{idPgto}")
    @Transactional
    public Response updateCardPayment(@PathParam("idPgto") Integer idPgto, PaymentRequestDto dto) {

        if (cardPaymentDao.buscaPagamentoPorId(idPgto) == null) {
            try {
                throw new CardPaymentApiException("Não existe pagamento na base com o id informado.");
            } catch (CardPaymentApiException e) {
                return Response
                        .status(Response.Status.NOT_FOUND.getStatusCode())
                        .entity(e.getMessage())
                        .build();
            }
        }
        try {

            cardPaymentModel.mapper(dto);

            try {
                cardPaymentValidation.applyValidations(cardPaymentModel);
            } catch (CardPaymentApiException e) {
                return Response
                        .status(Response.Status.UNAUTHORIZED.getStatusCode())
                        .entity(e.getMessage())
                        .build();
            }

            cardPaymentDao.atualizaPagamento(cardPaymentModel, idPgto);

            return Response
                    .status(Response.Status.MOVED_PERMANENTLY.getStatusCode())
                    .entity(cardPaymentDao.buscaPagamentoPorId(idPgto))
                    .build();

        } catch (CardPaymentApiException e) {
            return Response
                    .status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{paymentNumber}")
    public Response recoverPaymentData(@PathParam("paymentNumber") Integer paymentNumber) {

        try {
            var pagamento = cardPaymentDao.buscaPagamentoPorId(paymentNumber);
            return Response.status(Response.Status.FOUND).entity(cardPaymentDao.buscaPagamentoPorId(paymentNumber)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }


    }

    @DELETE
    @Path("/{numeroPagamento}")
    @Transactional
    public Response deleteCardPayment(@PathParam("numeroPagamento") Integer numeroPagamento) {

//        CardPaymentEntity cardPayment = CardPaymentEntity.findById(numeroPagamento);
//
//        if (cardPayment != null) {
//            cardPayment.delete();
//            return Response
//                    .status(Response.Status.ACCEPTED)
//                    .entity(cardPayment)
//                    .build();
//        }
//        else {
//            return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
//        }
        if (cardPaymentDao.deletaPagamento(numeroPagamento)) {
            return Response.status(Response.Status.OK).entity("Pagamento com Id " + numeroPagamento + " deletado.").build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
}

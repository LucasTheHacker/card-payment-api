package io.github.lucasthehacker.apipagamentocartao.rest.resource;

import io.github.lucasthehacker.apipagamentocartao.domain.model.entities.CardPayment;
import io.github.lucasthehacker.apipagamentocartao.domain.model.exceptions.DomainException;
import io.github.lucasthehacker.apipagamentocartao.domain.model.services.CardPaymentService;
import io.github.lucasthehacker.apipagamentocartao.rest.dto.PaymentRequestDto;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pagamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class PaymentResource {

    @POST
    @Transactional
    public Response createPaymentAPI(PaymentRequestDto paymentRequestDto) {

            CardPayment cardPayment = new CardPayment();

            cardPayment.setTipoPessoa(paymentRequestDto.getTipoPessoa());
            
            cardPayment.setNumeroCartao(paymentRequestDto.getNumeroCartao());
                    
            cardPayment.setcPFCNPJCliente(paymentRequestDto.getcPFCNPJCliente());
            
            cardPayment.setMesVencimentoCartao(paymentRequestDto.getMesVencimentoCartao());

            cardPayment.setAnoVencimentoCartao(paymentRequestDto.getAnoVencimentoCartao());
            
            cardPayment.setcVV(paymentRequestDto.getcVV());

            cardPayment.setValorPagamento(paymentRequestDto.getValorPagamento());

            CardPaymentService.personTypeValidation(cardPayment);

            CardPaymentService.cardValidationPadronization(cardPayment);

            CardPaymentService.cPFCNPJValidationPadronization(cardPayment);

            CardPaymentService.cardDateValidation(cardPayment);

            CardPaymentService.cVVValidationPadronization(cardPayment);

            CardPaymentService.valorPagamentoValidation(cardPayment);

            cardPayment.persist(); 

            return Response.ok(cardPayment).build();

    }

    @GET
    public Response requestPaymentDataAPI() {

        PanacheQuery<CardPayment> query = CardPayment.findAll();;

        return Response.ok(query.list()).build();
    }

    @PUT
    @Path("/{paymentNumber}")
    @Transactional
    public Response updateCardPayment(@PathParam("paymentNumber") Integer paymentNumber, PaymentRequestDto paymentRequestDto) {

        CardPayment cardPayment = CardPayment.findById(paymentNumber);


        return Response.ok().build();
    }

    @DELETE
    @Path("/{numeroPagamento}")
    @Transactional
    public Response deleteCardPayment(@PathParam("numeroPagamento") Integer numeroPagamento) {

        CardPayment cardPayment = CardPayment.findById(numeroPagamento);

        if (cardPayment != null) {
            cardPayment.delete();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().build();
    }
}

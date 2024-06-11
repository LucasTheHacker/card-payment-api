package io.github.lucasthehacker.apipagamentocartao.api.controller;

import io.github.lucasthehacker.apipagamentocartao.persistence.entitity.CardPayment;
import io.github.lucasthehacker.apipagamentocartao.domain.validation.CardPaymentValidation;
import io.github.lucasthehacker.apipagamentocartao.domain.dtos.PaymentRequestDto;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pagamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class PaymentController {

    @POST
    @Transactional
    public Response createPaymentAPI(PaymentRequestDto paymentRequestDto) {

            CardPayment cardPayment = new CardPayment();

            CardPaymentValidation cardPaymentValidation = new CardPaymentValidation();

            //RESPONSABILIDADE DO MODEL
            cardPayment.setTipoPessoa(paymentRequestDto.getTipoPessoa());
            
            cardPayment.setNumeroCartao(paymentRequestDto.getNumeroCartao());
                    
            cardPayment.setcPFCNPJCliente(paymentRequestDto.getcPFCNPJCliente());
            
            cardPayment.setMesVencimentoCartao(paymentRequestDto.getMesVencimentoCartao());

            cardPayment.setAnoVencimentoCartao(paymentRequestDto.getAnoVencimentoCartao());
            
            cardPayment.setcVV(paymentRequestDto.getcVV());

            cardPayment.setValorPagamento(paymentRequestDto.getValorPagamento());

            //RESPONSABILIDADE DO VALIDATION
            cardPaymentValidation.personTypeValidation(cardPayment);

            cardPaymentValidation.cardValidationPadronization(cardPayment);

            cardPaymentValidation.cPFCNPJValidationPadronization(cardPayment);

            cardPaymentValidation.cardDateValidation(cardPayment);

            cardPaymentValidation.cVVValidationPadronization(cardPayment);

            cardPaymentValidation.valorPagamentoValidation(cardPayment);

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

        CardPaymentValidation cardPaymentValidation = new CardPaymentValidation();

        if ( cardPayment != null ) {

            //RESPONSABILIDADE DO MODEL
            cardPayment.setTipoPessoa(paymentRequestDto.getTipoPessoa());

            cardPayment.setNumeroCartao(paymentRequestDto.getNumeroCartao());

            cardPayment.setcPFCNPJCliente(paymentRequestDto.getcPFCNPJCliente());

            cardPayment.setMesVencimentoCartao(paymentRequestDto.getMesVencimentoCartao());

            cardPayment.setAnoVencimentoCartao(paymentRequestDto.getAnoVencimentoCartao());

            cardPayment.setcVV(paymentRequestDto.getcVV());

            cardPayment.setValorPagamento(paymentRequestDto.getValorPagamento());

            //RESPONSABILIDADE DO VALIDATION
            cardPaymentValidation.personTypeValidation(cardPayment);

            cardPaymentValidation.cardValidationPadronization(cardPayment);

            cardPaymentValidation.cPFCNPJValidationPadronization(cardPayment);

            cardPaymentValidation.cardDateValidation(cardPayment);

            cardPaymentValidation.cVVValidationPadronization(cardPayment);

            cardPaymentValidation.valorPagamentoValidation(cardPayment);

            return Response.ok(cardPayment).build();
        }

        else {
            return  Response.status(Response.Status.NOT_FOUND).build();
        }
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

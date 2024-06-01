package io.github.lucasthehacker.apipagamentocartao.rest.resource;

import io.github.lucasthehacker.apipagamentocartao.domain.model.entities.CardPayment;
//import io.github.lucasthehacker.apipagamentocartao.domain.model.exceptions.DomainException;
import io.github.lucasthehacker.apipagamentocartao.domain.model.services.CardPaymentService;
import io.github.lucasthehacker.apipagamentocartao.rest.dto.PaymentRequestDto;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
//import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
//import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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

    /*@PUT
    @Transactional
    public Response updatePaymentDataAPI() {
        return Response.ok().build();
    }

    @DELETE
    public Response deletePaymentDataAPI() {
        return Response.ok().build();
    }*/
}

//CONTROLADOR DE REQUISIÇÕES REST

package io.github.lucasthehacker.apipagamentocartao.rest;

import io.github.lucasthehacker.apipagamentocartao.domain.model.CardPayment;
import io.github.lucasthehacker.apipagamentocartao.rest.dto.PaymentRequestAPI;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pagamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class PaymentOperations {

    @POST
    @Transactional
    public Response createPaymentAPI(PaymentRequestAPI paymentRequestAPI) {
        CardPayment cardPayment = new CardPayment(); //Id gerado automatico) 

        cardPayment.setNumeroCartao(paymentRequestAPI.getNumeroCartao());

        cardPayment.setTipoPessoa(paymentRequestAPI.getTipoPessoa());

        cardPayment.setcPFCNPJCliente(paymentRequestAPI.getcPFCNPJCliente());

        cardPayment.setMesVencimentoCartao(paymentRequestAPI.getMesVencimentoCartao());

        cardPayment.setAnoVencimentoCartao(paymentRequestAPI.getAnoVencimentoCartao());

        cardPayment.setcVV(paymentRequestAPI.getcVV());

        cardPayment.persist();

        return Response.ok(cardPayment).build();
    }

    @GET
    public Response requestPaymentDataAPI() {

        PanacheQuery<CardPayment> query = CardPayment.findAll();;

        return Response.ok(query.list()).build();
    }

    @PUT
    @Transactional
    public Response updatePaymentDataAPI() {
        return Response.ok().build();
    }

    @DELETE
    public Response deletePaymentDataAPI() {
        return Response.ok().build();
    }
}

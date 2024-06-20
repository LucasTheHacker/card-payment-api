package io.github.lucasthehacker.apipagamentocartao.api.controller;

import io.github.lucasthehacker.apipagamentocartao.domain.dtos.PaymentRequestDto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class PaymentControllerTest {

    @Test
    @DisplayName("Deve testar o pagamentos/hello")
    public void testHelloEndpoint() {
        given()
                .when().get("/pagamentos/hello")
        .then().
                statusCode(200).body(is("Hello World Test"));
    }

    @Test
    @DisplayName("Deve criar um pagamento com sucesso")
    public void testCreatePaymentAPISuccess() {
        var paymentRequestDto = new PaymentRequestDto();

        paymentRequestDto.setCVV("111");
        paymentRequestDto.setAnoVencimentoCartao(2025);
        paymentRequestDto.setMesVencimentoCartao(12);
        paymentRequestDto.setCPFCNPJCliente("111-000-555-66");
        paymentRequestDto.setNumeroCartao("4444-6666-9999-8888");
        paymentRequestDto.setTipoPessoa(1);
        paymentRequestDto.setValorPagamento("2700.00");

        var response  =
            given()
                    .contentType(ContentType.JSON)
                    .body(paymentRequestDto)
            .when()
                    .post("/pagamentos")
            .then()
                    .extract().response();

        assertEquals(201, response.statusCode());
        assertNotNull(response.jsonPath().getString("numeroPagamento")); //acesso ao objeto de retorno


    }
}

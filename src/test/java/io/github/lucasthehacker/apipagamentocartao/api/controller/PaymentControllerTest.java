package io.github.lucasthehacker.apipagamentocartao.api.controller;

import io.github.lucasthehacker.apipagamentocartao.domain.dtos.PaymentRequestDto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentControllerTest {

    @Test
    @DisplayName("PGTO sucesso")
    @Order(1)
    @Transactional
    public void testCreatePaymentAPISuccess() {
        var paymentRequestDto = new PaymentRequestDto();

        paymentRequestDto.setcVV("111");
        paymentRequestDto.setAnoVencimentoCartao(2025);
        paymentRequestDto.setMesVencimentoCartao(12);
        paymentRequestDto.setcPFCNPJCliente("111-000-555-66");
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

    }

    @Test
    @DisplayName("PGTO data expirada")
    public void pagamentoDataExpirada() {
        var paymentRequestDto = new PaymentRequestDto();

        paymentRequestDto.setcPFCNPJCliente("111-000-555-66");
        paymentRequestDto.setcVV("111");
        paymentRequestDto.setAnoVencimentoCartao(2022);
        paymentRequestDto.setMesVencimentoCartao(12);
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

        assertEquals(401, response.statusCode());

    }

    @Test
    @DisplayName("PGTO CPF longo")
    public void pagamentoCpfLongo() {
        var paymentRequestDto = new PaymentRequestDto();

        paymentRequestDto.setcPFCNPJCliente("111-000-555-66777777");
        paymentRequestDto.setcVV("111");
        paymentRequestDto.setAnoVencimentoCartao(2025);
        paymentRequestDto.setMesVencimentoCartao(12);
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

        assertEquals(401, response.statusCode());


    }

    @Test
    @DisplayName("PGTO CNPJ longo")
    public void pagamentoCnpjLongo() {
        var dto = new PaymentRequestDto();

        dto.setcPFCNPJCliente("111-000-555-6677/8887777");
        dto.setcVV("111");
        dto.setAnoVencimentoCartao(2025);
        dto.setMesVencimentoCartao(12);
        dto.setNumeroCartao("4444-6666-9999-8888");
        dto.setTipoPessoa(1);
        dto.setValorPagamento("2700.00");

        var response  =
                given()
                        .contentType(ContentType.JSON)
                        .body(dto)
                .when()
                        .post("/pagamentos")
                        .then()
                .extract().response();

        assertEquals(401, response.statusCode());

    }

    @Test
    @DisplayName("PGTO valorPgto não double")
    public void pagamentoValorNotDouble() {
        var dto = new PaymentRequestDto();

        dto.setcPFCNPJCliente("111-000-555-6677/888");
        dto.setcVV("111");
        dto.setAnoVencimentoCartao(2025);
        dto.setMesVencimentoCartao(12);
        dto.setNumeroCartao("4444-6666-9999-8888");
        dto.setTipoPessoa(1);
        dto.setValorPagamento("2700000000");

        var response  =
                given()
                        .contentType(ContentType.JSON)
                        .body(dto)
                .when()
                        .post("/pagamentos")
                        .then()
                .extract().response();

        assertEquals(401, response.statusCode());

    }

    @Test
    @DisplayName("Numero de cartao muito extenso")
    public void pagamentoCartaoMuitoLongo() {
        var dto = new PaymentRequestDto();

        dto.setcPFCNPJCliente("111-000-555-6677/888");
        dto.setcVV("111");
        dto.setAnoVencimentoCartao(2025);
        dto.setMesVencimentoCartao(12);
        dto.setNumeroCartao("4444-6666-9999-8888111111");
        dto.setTipoPessoa(1);
        dto.setValorPagamento("270.00");

        var response  =
                given()
                        .contentType(ContentType.JSON)
                        .body(dto)
                        .when()
                        .post("/pagamentos")
                        .then()
                        .extract().response();

        assertEquals(401, response.statusCode());

    }

    @Test
    @DisplayName("Tipo pessoa inexistente")
    public void tipoPessoaInexsistente() {
        var dto = new PaymentRequestDto();

        dto.setcPFCNPJCliente("111-000-555-6677/888");
        dto.setcVV("111");
        dto.setAnoVencimentoCartao(2025);
        dto.setMesVencimentoCartao(12);
        dto.setNumeroCartao("4444-6666-9999-8888");
        dto.setTipoPessoa(3);
        dto.setValorPagamento("270.00");

        var response  =
                given()
                        .contentType(ContentType.JSON)
                        .body(dto)
                        .when()
                        .post("/pagamentos")
                        .then()
                        .extract().response();

        assertEquals(401, response.statusCode());

    }

    @Test
    @DisplayName("CVV muito longo")
    public void cVVTooLong() {
        var dto = new PaymentRequestDto();

        dto.setcPFCNPJCliente("111-000-555-6677/888");
        dto.setcVV("1118888");
        dto.setAnoVencimentoCartao(2025);
        dto.setMesVencimentoCartao(12);
        dto.setNumeroCartao("4444-6666-9999-8888");
        dto.setTipoPessoa(1);
        dto.setValorPagamento("270.00");

        var response  =
                given()
                        .contentType(ContentType.JSON)
                        .body(dto)
                        .when()
                        .post("/pagamentos")
                        .then()
                        .extract().response();

        assertEquals(401, response.statusCode());

    }

    @Test
    @DisplayName("CVV deve ser um tipo numérico")
    public void cVVContainsAlfa() {
        var dto = new PaymentRequestDto();

        dto.setcPFCNPJCliente("111-000-555-6677/888");
        dto.setcVV("11OI18");
        dto.setAnoVencimentoCartao(2025);
        dto.setMesVencimentoCartao(12);
        dto.setNumeroCartao("4444-6666-9999-8888");
        dto.setTipoPessoa(1);
        dto.setValorPagamento("270.00");

        var response  =
                given()
                        .contentType(ContentType.JSON)
                        .body(dto)
                        .when()
                        .post("/pagamentos")
                        .then()
                        .extract().response();

        assertEquals(401, response.statusCode());

    }

    @Test
    @DisplayName("Deve listar todos os pagamentos")
    @Order(3)
    public void listaPagamentos() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/pagamentos")
                .then()
                .statusCode(302)
                .body("size()", Matchers.is(6));
    }

    @Test
    @DisplayName("Deve consultar pagamento por ID criado nos testes")
    @Order(2)
    public void listaPagamentoPorId() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/pagamentos/1")
                .then()
                .statusCode(302);
    }



}

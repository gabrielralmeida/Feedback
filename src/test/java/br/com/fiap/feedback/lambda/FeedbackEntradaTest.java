package br.com.fiap.feedback.lambda;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.fiap.feedback.dto.FeedbackRequest;

class FeedbackEntradaTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void lePedidoDireto() throws Exception {
        FeedbackRequest pedido = FeedbackEntrada.ler(mapper.readTree("""
                {"codigoCurso":1,"observacao":"Conteudo claro.","nota":9}
                """), mapper);

        assertEquals(1, pedido.getCodigoCurso());
        assertEquals("Conteudo claro.", pedido.getObservacao());
        assertEquals(9, pedido.getNota());
    }

    @Test
    void leCorpoDoApiGateway() throws Exception {
        FeedbackRequest pedido = FeedbackEntrada.ler(mapper.readTree("""
                {
                  "version": "2.0",
                  "routeKey": "POST /Feedback",
                  "isBase64Encoded": false,
                  "body": "{\\"codigoCurso\\":1,\\"observacao\\":\\"Conteudo claro.\\",\\"nota\\":9}"
                }
                """), mapper);

        assertEquals(1, pedido.getCodigoCurso());
        assertEquals("Conteudo claro.", pedido.getObservacao());
        assertEquals(9, pedido.getNota());
    }
}

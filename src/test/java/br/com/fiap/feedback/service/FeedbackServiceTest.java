package br.com.fiap.feedback.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.fiap.feedback.dto.FeedbackRequest;
import br.com.fiap.feedback.dto.FeedbackResponse;
import br.com.fiap.feedback.repository.MemoriaFeedbackStore;

class FeedbackServiceTest {

    private static final LocalDateTime HORA = LocalDateTime.of(2026, 11, 14, 15, 0);

    private MemoriaFeedbackStore store;
    private FeedbackService service;

    @BeforeEach
    void setUp() {
        store = new MemoriaFeedbackStore();
        Clock clock = Clock.fixed(Instant.parse("2026-11-14T18:00:00Z"), ZoneId.of("America/Sao_Paulo"));
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        service = new FeedbackService(validator, store, clock);
    }

    @Test
    void rejeitaPedidoAusente() {
        FeedbackResponse resposta = service.registrar(null);

        assertFalse(resposta.sucesso());
        assertEquals(Set.of("Os dados do feedback são obrigatórios."), Set.copyOf(resposta.mensagens()));
        assertEquals(0, store.quantidade());
    }

    @Test
    void rejeitaCamposAusentes() {
        FeedbackResponse resposta = service.registrar(pedido(null, null, null));

        assertFalse(resposta.sucesso());
        assertEquals(
                Set.of(
                        "Código do curso é obrigatório",
                        "Observação é obrigatória",
                        "Nota é obrigatória"),
                Set.copyOf(resposta.mensagens()));
        assertEquals(0, store.quantidade());
    }

    @Test
    void rejeitaObservacaoEmBrancoOuLongaDemais() {
        FeedbackResponse emBranco = service.registrar(pedido(1, "   ", 8));
        FeedbackResponse longa = service.registrar(pedido(1, "a".repeat(101), 8));

        assertEquals(Set.of("Observação é obrigatória"), Set.copyOf(emBranco.mensagens()));
        assertEquals(Set.of("Observação deve ter no máximo 100 caracteres"), Set.copyOf(longa.mensagens()));
        assertEquals(0, store.quantidade());
    }

    @Test
    void rejeitaNotaForaDoIntervalo() {
        FeedbackResponse negativa = service.registrar(pedido(1, "Curso ruim", -1));
        FeedbackResponse acima = service.registrar(pedido(1, "Curso ruim", 11));

        assertEquals(Set.of("Nota deve estar entre 0 e 10"), Set.copyOf(negativa.mensagens()));
        assertEquals(Set.of("Nota deve estar entre 0 e 10"), Set.copyOf(acima.mensagens()));
        assertEquals(0, store.quantidade());
    }

    @Test
    void aceitaNotaZeroEDez() {
        FeedbackResponse zero = service.registrar(pedido(7, "Sem aproveitamento", 0));
        FeedbackResponse dez = service.registrar(pedido(7, "Excelente", 10));

        assertTrue(zero.sucesso());
        assertEquals(0, zero.nota());
        assertEquals(1, zero.sequencia());
        assertTrue(dez.sucesso());
        assertEquals(10, dez.nota());
        assertEquals(2, dez.sequencia());
        assertEquals(HORA, zero.dataHora());
        assertEquals(HORA, dez.dataHora());
    }

    @Test
    void devolveASequenciaGeradaPelaTabela() {
        FeedbackResponse primeira = service.registrar(pedido(10, "Primeira", 8));
        FeedbackResponse segunda = service.registrar(pedido(10, "Segunda", 9));
        FeedbackResponse outroCurso = service.registrar(pedido(20, "Outro curso", 6));

        assertEquals(1, primeira.sequencia());
        assertEquals(2, segunda.sequencia());
        assertEquals(3, outroCurso.sequencia());
        assertEquals("Primeira", primeira.observacao());
        assertEquals(10, primeira.codigoCurso());
        assertEquals(20, outroCurso.codigoCurso());
    }

    @Test
    void gravaObservacaoAparadaComCemCaracteres() {
        String texto = "  " + "b".repeat(100) + "  ";

        FeedbackResponse resposta = service.registrar(pedido(3, texto, 5));

        assertTrue(resposta.sucesso());
        assertEquals("b".repeat(100), resposta.observacao());
        assertEquals(1, resposta.sequencia());
    }

    private static FeedbackRequest pedido(Integer codigoCurso, String observacao, Integer nota) {
        FeedbackRequest request = new FeedbackRequest();
        request.setCodigoCurso(codigoCurso);
        request.setObservacao(observacao);
        request.setNota(nota);
        return request;
    }
}

package br.com.fiap.feedback.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.com.fiap.feedback.model.Feedback;

public record FeedbackResponse(
        boolean sucesso,
        Integer codigoCurso,
        Integer sequencia,
        String observacao,
        LocalDateTime dataHora,
        Integer nota,
        List<String> mensagens) {

    public FeedbackResponse {
        mensagens = mensagens == null ? List.of() : List.copyOf(mensagens);
    }

    public static FeedbackResponse erro(List<String> mensagens) {
        return new FeedbackResponse(false, null, null, null, null, null, mensagens);
    }

    public static FeedbackResponse ok(Feedback feedback) {
        return new FeedbackResponse(
                true,
                feedback.getCodigoCurso(),
                feedback.getSequencia(),
                feedback.getObservacao(),
                feedback.getDataHora(),
                feedback.getNota(),
                List.of());
    }
}

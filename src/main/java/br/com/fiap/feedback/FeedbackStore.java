package br.com.fiap.feedback;

import java.time.LocalDateTime;

public interface FeedbackStore {

    Feedback inserir(int codigoCurso, String observacao, int nota, LocalDateTime dataHora);
}

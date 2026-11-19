package br.com.fiap.feedback.repository;

import java.time.LocalDateTime;

import br.com.fiap.feedback.model.Feedback;

public interface FeedbackStore {

    Feedback inserir(int codigoCurso, String observacao, int nota, LocalDateTime dataHora);
}

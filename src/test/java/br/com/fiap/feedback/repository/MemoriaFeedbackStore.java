package br.com.fiap.feedback.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.feedback.model.Feedback;

public final class MemoriaFeedbackStore implements FeedbackStore {

    private final List<Feedback> registros = new ArrayList<>();
    private int proximaSequencia = 1;

    @Override
    public Feedback inserir(int codigoCurso, String observacao, int nota, LocalDateTime dataHora) {
        Feedback feedback = new Feedback(codigoCurso, observacao, dataHora, nota);
        feedback.definirSequencia(proximaSequencia++);
        registros.add(feedback);
        return feedback;
    }

    public int quantidade() {
        return registros.size();
    }
}

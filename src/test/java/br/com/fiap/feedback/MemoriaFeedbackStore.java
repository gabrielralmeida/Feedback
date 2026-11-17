package br.com.fiap.feedback;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

final class MemoriaFeedbackStore implements FeedbackStore {

    private final List<Feedback> registros = new ArrayList<>();
    private int proximaSequencia = 1;

    @Override
    public Feedback inserir(int codigoCurso, String observacao, int nota, LocalDateTime dataHora) {
        Feedback feedback = new Feedback(codigoCurso, observacao, dataHora, nota);
        feedback.definirSequencia(proximaSequencia++);
        registros.add(feedback);
        return feedback;
    }

    int quantidade() {
        return registros.size();
    }
}

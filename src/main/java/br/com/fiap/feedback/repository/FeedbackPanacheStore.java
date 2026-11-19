package br.com.fiap.feedback.repository;

import java.time.LocalDateTime;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import br.com.fiap.feedback.model.Feedback;

@ApplicationScoped
public class FeedbackPanacheStore implements FeedbackStore {

    private final FeedbackRepository repository;

    @Inject
    public FeedbackPanacheStore(FeedbackRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Feedback inserir(int codigoCurso, String observacao, int nota, LocalDateTime dataHora) {
        Feedback feedback = new Feedback(codigoCurso, observacao, dataHora, nota);
        repository.persist(feedback);
        repository.getEntityManager().flush();
        return feedback;
    }
}

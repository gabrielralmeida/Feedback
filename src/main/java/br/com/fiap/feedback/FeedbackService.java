package br.com.fiap.feedback;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@ApplicationScoped
public class FeedbackService {

    private final Validator validator;
    private final FeedbackStore store;
    private final Clock clock;

    @Inject
    public FeedbackService(Validator validator, FeedbackStore store, Clock clock) {
        this.validator = validator;
        this.store = store;
        this.clock = clock;
    }

    public FeedbackResponse registrar(FeedbackRequest request) {
        if (request == null) {
            return FeedbackResponse.erro(List.of("Os dados do feedback são obrigatórios."));
        }
        FeedbackRequest normalizado = request.normalizado();
        List<String> mensagens = validator.validate(normalizado).stream()
                .map(ConstraintViolation::getMessage)
                .distinct()
                .sorted()
                .toList();
        if (!mensagens.isEmpty()) {
            return FeedbackResponse.erro(mensagens);
        }

        Feedback gravado = store.inserir(
                normalizado.getCodigoCurso(),
                normalizado.getObservacao(),
                normalizado.getNota(),
                LocalDateTime.now(clock));
        return FeedbackResponse.ok(gravado);
    }
}

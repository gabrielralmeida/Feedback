package br.com.fiap.feedback;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

@ApplicationScoped
@Named("feedback")
public class FeedbackLambda implements RequestHandler<FeedbackRequest, FeedbackResponse> {

    private final FeedbackService service;

    @Inject
    public FeedbackLambda(FeedbackService service) {
        this.service = service;
    }

    @Override
    public FeedbackResponse handleRequest(FeedbackRequest input, Context context) {
        return service.registrar(input);
    }
}

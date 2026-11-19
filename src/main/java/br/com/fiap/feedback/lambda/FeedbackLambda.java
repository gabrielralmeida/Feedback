package br.com.fiap.feedback.lambda;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.feedback.dto.FeedbackResponse;
import br.com.fiap.feedback.service.FeedbackService;

@ApplicationScoped
@Named("feedback")
public class FeedbackLambda implements RequestHandler<JsonNode, FeedbackResponse> {

    private final FeedbackService service;
    private final ObjectMapper mapper;

    @Inject
    public FeedbackLambda(FeedbackService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public FeedbackResponse handleRequest(JsonNode input, Context context) {
        return service.registrar(FeedbackEntrada.ler(input, mapper));
    }
}

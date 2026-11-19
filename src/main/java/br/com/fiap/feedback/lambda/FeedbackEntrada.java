package br.com.fiap.feedback.lambda;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.feedback.dto.FeedbackRequest;

final class FeedbackEntrada {

    private FeedbackEntrada() {
    }

    static FeedbackRequest ler(JsonNode entrada, ObjectMapper mapper) {
        JsonNode payload = corpo(entrada, mapper);
        if (payload == null || payload.isNull()) {
            return null;
        }
        try {
            return mapper.treeToValue(payload, FeedbackRequest.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private static JsonNode corpo(JsonNode entrada, ObjectMapper mapper) {
        if (entrada == null || entrada.isNull() || entrada.isMissingNode()) {
            return null;
        }
        if (!entrada.has("body")) {
            return entrada;
        }
        JsonNode body = entrada.get("body");
        if (body == null || body.isNull()) {
            return null;
        }
        if (body.isObject()) {
            return body;
        }
        if (!body.isTextual() || body.asText().isBlank()) {
            return null;
        }
        String texto = body.asText();
        if (entrada.path("isBase64Encoded").asBoolean(false)) {
            texto = new String(Base64.getDecoder().decode(texto), StandardCharsets.UTF_8);
        }
        try {
            return mapper.readTree(texto);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}

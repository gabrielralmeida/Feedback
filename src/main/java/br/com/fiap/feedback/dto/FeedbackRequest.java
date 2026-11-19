package br.com.fiap.feedback.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FeedbackRequest {

    @NotNull(message = "Código do curso é obrigatório")
    private Integer codigoCurso;

    @NotBlank(message = "Observação é obrigatória")
    @Size(max = 100, message = "Observação deve ter no máximo 100 caracteres")
    private String observacao;

    @NotNull(message = "Nota é obrigatória")
    @Min(value = 0, message = "Nota deve estar entre 0 e 10")
    @Max(value = 10, message = "Nota deve estar entre 0 e 10")
    private Integer nota;

    public FeedbackRequest normalizado() {
        FeedbackRequest copia = new FeedbackRequest();
        copia.codigoCurso = codigoCurso;
        copia.observacao = observacao == null ? null : observacao.trim();
        copia.nota = nota;
        return copia;
    }

    public Integer getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(Integer codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }
}

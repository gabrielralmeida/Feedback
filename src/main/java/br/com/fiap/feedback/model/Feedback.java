package br.com.fiap.feedback.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TbFeedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Sequencia", nullable = false)
    private Integer sequencia;

    @Column(name = "CodigoCurso", nullable = false)
    private Integer codigoCurso;

    @Column(name = "Observacao", nullable = false, length = 100)
    private String observacao;

    @Column(name = "DataHora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "Nota", nullable = false)
    private Integer nota;

    protected Feedback() {
    }

    public Feedback(Integer codigoCurso, String observacao, LocalDateTime dataHora, Integer nota) {
        this.codigoCurso = codigoCurso;
        this.observacao = observacao;
        this.dataHora = dataHora;
        this.nota = nota;
    }

    public void definirSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }

    public Integer getSequencia() {
        return sequencia;
    }

    public Integer getCodigoCurso() {
        return codigoCurso;
    }

    public String getObservacao() {
        return observacao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Integer getNota() {
        return nota;
    }
}

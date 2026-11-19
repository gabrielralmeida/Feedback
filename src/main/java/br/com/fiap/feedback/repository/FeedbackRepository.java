package br.com.fiap.feedback.repository;

import jakarta.enterprise.context.ApplicationScoped;

import br.com.fiap.feedback.model.Feedback;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class FeedbackRepository implements PanacheRepositoryBase<Feedback, Integer> {
}

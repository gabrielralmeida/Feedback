package br.com.fiap.feedback;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class FeedbackRepository implements PanacheRepositoryBase<Feedback, Integer> {
}

package my.project.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.persistence.EntityManager;


@ApplicationScoped
public class ReplicationService {
    @Inject
    @PersistenceUnit("origin")
    private EntityManager originManager;

    @Inject
    @PersistenceUnit("replicant1")
    private EntityManager replicant1Manager;

    public void replicate() {
    }
}

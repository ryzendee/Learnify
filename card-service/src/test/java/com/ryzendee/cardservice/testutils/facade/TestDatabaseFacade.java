package com.ryzendee.cardservice.testutils.facade;

import com.ryzendee.cardservice.testutils.builder.TestBaseBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.support.TransactionTemplate;

public class TestDatabaseFacade {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public <T> T find(Object id, Class<T> entityClass) {
        return transactionTemplate.execute(status -> entityManager.find(entityClass, id));
    }
    public void saveAll(TestBaseBuilder<?>... builders) {
        transactionTemplate.execute(status -> {
            for (TestBaseBuilder<?> b : builders) {
                save(b);
            }
            return null;
        });
    }

    public <T> T save(TestBaseBuilder<T> builder) {
        return transactionTemplate.execute(status -> {
            T entity = builder.build();
            entityManager.persist(entity);
            entityManager.flush();
            return entity;
        });
    }

    public void cleanDatabase() {
        transactionTemplate.execute(status -> {
            JdbcTestUtils.deleteFromTables(jdbcTemplate, getTablesToClean());
            return null;
        });
    }

    private String[] getTablesToClean() {
        return new String[]{
                "cards"
        };
    }
}

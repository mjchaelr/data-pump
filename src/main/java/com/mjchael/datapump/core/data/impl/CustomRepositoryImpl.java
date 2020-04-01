package com.mjchael.datapump.core.data.impl;

import com.mjchael.datapump.core.data.CustomRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public class CustomRepositoryImpl implements CustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> executeQuery(String sqlStatement) {
        Query query = entityManager.createNativeQuery(sqlStatement);

        return query.getResultList();
    }
}

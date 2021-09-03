
package com.surl.service.jpa.impl;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.surl.service.jpa.api.BaseRepository;
import com.surl.service.jpa.api.SurlRepository;
import com.surl.service.jpa.entity.Surl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class SurlRepositoryImpl implements SurlRepository {

    @Autowired
    private BaseRepository baseRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Surl save(final Surl surl) {
        return baseRepository.save(surl);
    }

    @Override
    public Optional<String> findIdByUrl(final String url) {
        final Optional<Surl> surl = selectSurlWhereEqual(new SimpleEntry<>("url", url));
        if (surl.isPresent()) {
            updateTimestamp(surl.get());
            return Optional.of(surl.get().getCode());
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> findUrlById(final String code) {
        final Optional<Surl> surl = baseRepository.findById(code);
        if (surl.isPresent()) {
            updateTimestamp(surl.get());
            return Optional.of(surl.get().getUrl());
        }
        return Optional.empty();
    }

    @Override
    public void removeByCode(final String code) {
        baseRepository.deleteById(code);
    }

    @Override
    public void removeByUrl(String url) {
        final Optional<Surl> surl = selectSurlWhereEqual(new SimpleEntry<>("url", url));
        if (surl.isPresent()) {
            baseRepository.delete(surl.get());
        }
    }

    @Override
    public long codeCount() {
        return baseRepository.count();
    }

    @Override
    public List<String> getBatchUrlByTimestamp(int batchSize) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        final Root<Surl> surlRoot = criteriaQuery.from(Surl.class);
        criteriaQuery.select(surlRoot.get("url"));
        criteriaQuery.orderBy(criteriaBuilder.asc(surlRoot.get("updated")));
        return entityManager.createQuery(criteriaQuery).setMaxResults(batchSize).getResultList();
    }

    @Override
    public void updateTimestampByUrl(final String url) {
        findIdByUrl(url);
    }

    private void updateTimestamp(final Surl surl) {
        try {
            surl.setUpdated(System.currentTimeMillis());
            baseRepository.save(surl);
        } catch (Exception e) {
            log.debug("Failed to update timestamp for {} ", surl);
        }
    }

    private Optional<Surl> selectSurlWhereEqual(final Entry<String, Object> where) {
        try {
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaQuery<Surl> criteriaQuery = criteriaBuilder.createQuery(Surl.class);
            final Root<Surl> surlRoot = criteriaQuery.from(Surl.class);
            return Optional.of(entityManager
                    .createQuery(criteriaQuery.multiselect(surlRoot.get("code"), surlRoot.get("url"))
                            .where(criteriaBuilder.equal(surlRoot.get(where.getKey()), where.getValue())))
                    .getSingleResult());
        } catch (NoResultException e) {
            log.debug("Failed surl where equal {} ", where.toString());
            return Optional.empty();
        }
    }

}

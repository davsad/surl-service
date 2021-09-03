package com.surl.service.jpa.api;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.surl.service.jpa.entity.Surl;

@Repository
public interface SurlRepository {

    public Surl save(final Surl surl);

    public Optional<String> findIdByUrl(final String url);

    public Optional<String> findUrlById(final String id);

    public void removeByCode(final String code);
    
    public void removeByUrl(final String url);

    public long codeCount();

    public void updateTimestampByUrl(final String id);
    
    public List<String> getBatchUrlByTimestamp(final int batchSize);
}

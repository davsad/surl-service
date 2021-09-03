package com.surl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.surl.service.api.UrlService;
import com.surl.service.jpa.api.SurlRepository;
import com.surl.service.jpa.entity.Surl;
import com.surl.service.util.api.IdGenerator;
import com.surl.service.util.exception.InvalidUrlException;
import com.surl.service.util.generator.AlphaNumericIdGenerator;
import com.surl.service.util.http.HttpConnectionValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UrlServiceImpl implements UrlService {

    @Autowired
    private SurlRepository surlRepository;
    
    private final IdGenerator idGenerator = new AlphaNumericIdGenerator();

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String shortenUrl(final String url) throws InvalidUrlException {
        log.debug("Call recieved to shorten url {%s}", url);
        if (HttpConnectionValidator.isValidUrl(url)) {
            String code = surlRepository.findIdByUrl(url).orElseGet(idGenerator::nextId);
            surlRepository.save(new Surl(code, url, "test", System.currentTimeMillis()));
            return code;
        } 
        throw new InvalidUrlException(url);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Cacheable(value = "urlcache", key ="#code")
    public String getUrlByCode(String code) throws InvalidUrlException {
        log.debug("Call recieved to retrieve url based on code {%s}", code);
        final String url = surlRepository.findUrlById(code).orElseThrow(() -> new InvalidUrlException("Url does not exist in our records"));
        if (HttpConnectionValidator.isValidUrl(url)) {
            return url;
        } else {
            surlRepository.removeByCode(code);
            throw new InvalidUrlException("Url does not exist in our records");
        }
    }

}

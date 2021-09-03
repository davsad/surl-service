
package com.surl.service.jpa.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.surl.service.jpa.api.SurlRepository;
import com.surl.service.util.http.HttpConnectionValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RecordManager {

    private static final int BATCH_SIZE = 500;
    
    @Autowired
    private SurlRepository surlRepository;

    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    @PostConstruct
    private void startThreadService() {
        log.debug("Starting background thread");
        addThread(() -> {
            long numberOfRecords = surlRepository.codeCount();
            while (numberOfRecords > 0) {
                try {
                    surlRepository.getBatchUrlByTimestamp(BATCH_SIZE).stream().forEach(url -> {
                        try {
                            if (HttpConnectionValidator.isValidUrl(url)) {
                                surlRepository.updateTimestampByUrl(url);
                            }
                        } catch (Exception e) {
                            log.debug("Exception caught while validating url {}", url);
                            surlRepository.removeByUrl(url);
                        }
                    });
                } catch (Exception e) {
                    log.error("Unexpected Error in thread {}", e);
                } finally {
                    numberOfRecords -= BATCH_SIZE;
                }
            }
        }, 10, 10, TimeUnit.MINUTES);
        log.debug("Background thread started");
    }

    private void addThread(Runnable runnable, long initDelay, long period, TimeUnit timeUnit) {
        executorService.scheduleAtFixedRate(runnable, initDelay, period, timeUnit);
    }

    @PreDestroy
    private void stopThreadService() {
        executorService.shutdownNow();
    }
}

package com.surl.service.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "surl")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Surl {

    @Id
    private String code;
    private String url;
    private String user;
    private long updated;

    public Surl(String code, String url) {
        this.code = code;
        this.url = url;
    }
    
    public Surl(String code, String url, String user, long updated) {
        this.code = code;
        this.url = url;
        this.user = user;
        this.updated = updated;
    }
}

package com.surl.service.jpa.api;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.surl.service.jpa.entity.Surl;

@Repository
public interface BaseRepository extends CrudRepository<Surl, String> {
    
}

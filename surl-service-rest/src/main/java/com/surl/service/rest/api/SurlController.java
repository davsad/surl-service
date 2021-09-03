package com.surl.service.rest.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;

import com.surl.service.rest.data.SurlRequest;
import com.surl.service.util.exception.InvalidUrlException;


public interface SurlController {

    @GetMapping("/{code}")
    public RedirectView redirect(final String code)  throws InvalidUrlException;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.TEXT_PLAIN_VALUE)
    public String shortenUrl(@RequestBody final SurlRequest surlRequest) throws InvalidUrlException;

}

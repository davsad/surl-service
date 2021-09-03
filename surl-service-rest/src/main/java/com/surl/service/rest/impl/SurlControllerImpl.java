
package com.surl.service.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import com.surl.service.api.UrlService;
import com.surl.service.rest.api.SurlController;
import com.surl.service.rest.data.SurlRequest;
import com.surl.service.util.exception.InvalidUrlException;

@RestController
public class SurlControllerImpl implements SurlController {

    @Autowired
    private UrlService urlService;
    
    @Override
    @GetMapping("/{code}")
    public RedirectView redirect(@PathVariable String code) throws InvalidUrlException {
        return new RedirectView(urlService.getUrlByCode(code));
    }

    @Override
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.TEXT_PLAIN_VALUE)
    public String shortenUrl(@RequestBody SurlRequest surlRequest) throws InvalidUrlException {
        ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        urlBuilder.scheme("http");
        return urlBuilder.path(urlService.shortenUrl(surlRequest.getUrl())).toUriString();
    }
}

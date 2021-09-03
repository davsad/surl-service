
package com.surl.service.api;

import com.surl.service.util.exception.InvalidUrlException;

public interface UrlService {
    public String shortenUrl(final String url) throws InvalidUrlException;

    public String getUrlByCode(final String code) throws InvalidUrlException;

}

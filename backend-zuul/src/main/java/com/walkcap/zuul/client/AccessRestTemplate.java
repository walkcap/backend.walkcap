package com.walkcap.zuul.client;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class AccessRestTemplate extends RestTemplate {
    @Nullable
    @Override
    protected <T> T doExecute(URI url, @Nullable HttpMethod method, @Nullable RequestCallback requestCallback,
                              @Nullable ResponseExtractor<T> responseExtractor) throws RestClientException {

        Assert.notNull(url, "URI is required");
        Assert.notNull(method, "HttpMethod is required");
        
        ClientHttpResponse response = null;
        try {
            ClientHttpRequest request = createRequest(url, method);

            // ---start---
            // 将Query参数“Authorization=value”添加到请求的Headers中
            // Headers: KEY=Authorization VALUE=value
            HttpHeaders myHeaders = request.getHeaders();
            String query = url.getQuery();
            String[] split = query.split("\\&");                                                      // ?requestURI={1}&Authorization={2}
            Arrays.stream(split)
                  .filter((A)-> StringUtils.startsWithIgnoreCase(A,"Authorization"))                  // A：Authorization=value
                  .forEach(A->{
                	  myHeaders.add("Authorization",StringUtils.substringAfter(A,"Authorization="));  // 
                    });
            // ---end---
            
            if (requestCallback != null) {
                requestCallback.doWithRequest(request);
            }
            response = request.execute();
            handleResponse(url, method, response);
            return (responseExtractor != null ? responseExtractor.extractData(response) : null);
        }
        catch (IOException ex) {
            String resource = url.toString();
            String query = url.getRawQuery();
            resource = (query != null ? resource.substring(0, resource.indexOf('?')) : resource);
            throw new ResourceAccessException("I/O error on " + method.name()
                    + " request for \"" + resource + "\": " + ex.getMessage(), ex);
        }
        finally {
            if (response != null) {
                response.close();
            }
        }
    }
}


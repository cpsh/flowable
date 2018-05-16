package com.sun.health.springboot.config;

import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.List;

/**
 * Created by 华硕 on 2018-05-16.
 */
public class MyConfig {

    public HttpMessageConverters customConverters() {
        HttpMessageConverters httpMessageConverters = new HttpMessageConverters();
        HttpMessageConverter<?> httpMessageConverter = new HttpMessageConverter<Object>() {
            @Override
            public boolean canRead(Class<?> aClass, MediaType mediaType) {
                return false;
            }

            @Override
            public boolean canWrite(Class<?> aClass, MediaType mediaType) {
                return false;
            }

            @Override
            public List<MediaType> getSupportedMediaTypes() {
                return null;
            }

            @Override
            public Object read(Class<?> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
                return null;
            }

            @Override
            public void write(Object o, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {

            }
        };
        return new HttpMessageConverters();
    }

}

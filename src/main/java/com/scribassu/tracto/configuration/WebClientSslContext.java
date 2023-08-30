package com.scribassu.tracto.configuration;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;

public class WebClientSslContext {

    public static final SslContext INSECURE_SSL_CONTEXT;

    static {
        try {
            INSECURE_SSL_CONTEXT = SslContextBuilder
                    .forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build();
        } catch (SSLException e) {
            throw new RuntimeException(e);
        }
    }
}

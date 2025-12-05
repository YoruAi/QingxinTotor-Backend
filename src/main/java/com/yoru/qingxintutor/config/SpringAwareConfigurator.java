package com.yoru.qingxintutor.config;

import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import org.springframework.context.ApplicationContext;

public class SpringAwareConfigurator extends ServerEndpointConfig.Configurator {

    private static volatile ApplicationContext context;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringAwareConfigurator.context = applicationContext;
    }

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
        if (context != null) {
            return context.getBean(clazz);
        }
        return super.getEndpointInstance(clazz);
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        config.getUserProperties().put("httpRequest", request);
        super.modifyHandshake(config, request, response);
    }
}
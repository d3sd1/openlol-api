package com.openlol.api.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;

@Component
public class AuthChannelInterceptorAdapter implements ChannelInterceptor {
    private static final String UUID_HEADER = "pc_uuid";
    private final WebSocketAuthenticatorService webSocketAuthenticatorService;

    @Inject
    public AuthChannelInterceptorAdapter(final WebSocketAuthenticatorService webSocketAuthenticatorService) {
        this.webSocketAuthenticatorService = webSocketAuthenticatorService;
    }

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            final String uuid = accessor.getFirstNativeHeader(UUID_HEADER);
            UsernamePasswordAuthenticationToken user = null;
            try {
                user = webSocketAuthenticatorService.getAuthenticatedOrFail(uuid);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            accessor.setUser(user);
        }
        return message;
    }
}

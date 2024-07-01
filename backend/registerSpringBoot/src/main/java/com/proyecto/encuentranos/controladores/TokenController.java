package com.proyecto.encuentranos.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TokenController {

    private static final String EMAIL_ATTRIBUTE = "email";
    private static final String TOKEN = "token";
    private static final String ERROR = "error";
    private static final String NOT_OAUTH2_AUTHENTICATION = "Not an OAuth2 authentication";

    private final OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    public TokenController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/token")
    public ResponseEntity<Map<String, String>> obtenerTokenYEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            OAuth2User oauth2User = oauthToken.getPrincipal();
            String email = (String) oauth2User.getAttribute(EMAIL_ATTRIBUTE);
            OAuth2AccessToken accessToken = authorizedClientService.loadAuthorizedClient(
                    oauthToken.getAuthorizedClientRegistrationId(),
                    oauthToken.getName()
            ).getAccessToken();
            return ResponseEntity.ok(Map.of(TOKEN, accessToken.getTokenValue(), EMAIL_ATTRIBUTE, email));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(ERROR, NOT_OAUTH2_AUTHENTICATION));
    }

}

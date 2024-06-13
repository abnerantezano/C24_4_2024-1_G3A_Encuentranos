package com.proyecto.encuentranos.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//ESTAMOS CREANDO EL CONTROLADOR PARA token
@RestController
public class TokenController {

	//INSTANCIAR LAS CLASES QUE USAREMOS

    private final OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    public TokenController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    //OBTENER EL TOKEN 
    @GetMapping("/token")
    public String getToken(OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName());
        OAuth2AccessToken accessToken = client.getAccessToken();
        return accessToken.getTokenValue();
    }
}

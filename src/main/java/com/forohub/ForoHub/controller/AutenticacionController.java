package com.forohub.ForoHub.controller;


import com.forohub.ForoHub.dto.UsernamePasswordAuthenticationDTO;
import com.forohub.ForoHub.model.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<String> autenticar(@RequestBody @Valid UsernamePasswordAuthenticationDTO dto) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.login(), dto.clave())
        );

        var usuario = (UserDetails) authentication.getPrincipal();
        String token = tokenService.generarToken(authentication.getName());

        return ResponseEntity.ok(token);
    }
}

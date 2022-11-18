package br.senai.sc.editoralivros.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class AutenticacaoController {
    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<Object> autenticar(
            @RequestBody @Valid UsuarioDTO usuarioDTO
    ) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            System.out.println(authentication.isAuthenticated());

//            if (authentication.isAuthenticated()) {
            String token = autenticacaoService.gerarToken(authentication);
//            return ResponseEntity.ok().build();
            return ResponseEntity.status(HttpStatus.OK).body(new TokenDTO("Bearer", token));
//            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

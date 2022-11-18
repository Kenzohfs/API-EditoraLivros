package br.senai.sc.editoralivros.security;

import br.senai.sc.editoralivros.dto.PessoaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class AutenticacaoController {
    @PostMapping
    public ResponseEntity<Object> autenticar(
            @RequestBody @Valid UsuarioDTO usuarioDTO
            ) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha());
        System.out.println(authenticationToken.isAuthenticated());
        if (authenticationToken.isAuthenticated()) {
//            return ResponseEntity.ok().build();
            return ResponseEntity.status(HttpStatus.OK).body(authenticationToken.getPrincipal());
        }
        return ResponseEntity.badRequest().build();
    }
}
package br.senai.sc.editoralivros.security;

import br.senai.sc.editoralivros.security.service.JpaService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class AutenticacaoFiltro extends OncePerRequestFilter {
    private TokenUtils tokenUtils;
    private JpaService jpaService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Filtro: " + request.getRequestURI());
        if (
                request.getRequestURI().equals("/login") ||
                        request.getRequestURI().equals("/login/auth") ||
                        request.getRequestURI().equals("/logout") ||
                        request.getRequestURI().startsWith("/v3/api-docs") ||
                        request.getRequestURI().startsWith("/swagger-ui")
//                        || request.getRequestURI().equals("/editoralivros/pessoa")

        ) {
            filterChain.doFilter(request, response);
            return;
        }

        // Dessa forma precisamos add no header da request o token do usuário,
        // mas podemos fazer com que a API busque o cookie que contém o token
//        String token = request.getHeader("Authorization");
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7);
//        } else {
//            token = null;
//        }

        // Busca o Cookie através do navegador do usuário
        String token = tokenUtils.buscarCookie(request);

        Boolean valido = tokenUtils.validarToken(token);

        if (valido) {
            Long usuarioCPF = tokenUtils.getUsuarioCPF(token);
            UserDetails usuario = jpaService.loadUserByCPF(usuarioCPF);

            // Verifica se o usuário está autenticado, por isso mandamos suas autoridades
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(usuario.getUsername(), null, usuario.getAuthorities());

            // Como estamos usando Session Stateless, essa linha não é precisa
//            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } else if (!request.getRequestURI().equals("/editoralivros/login") || !request.getRequestURI().equals("/editoralivros/usuarios")
        ) {
            response.setStatus(401);
        }

        filterChain.doFilter(request, response);
    }
}

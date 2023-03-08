package br.senai.sc.editoralivros.security;

import br.senai.sc.editoralivros.security.service.GoogleService;
import br.senai.sc.editoralivros.security.service.JpaService;
import br.senai.sc.editoralivros.security.users.UserJpa;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@AllArgsConstructor
public class AutenticacaoConfig {
    // No browser, ao acessar a rota irá pedir login e senha:
    // - usuário: user
    // - senha: a que gerar quando rodar
    private JpaService jpaService;
    private GoogleService googleService;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(jpaService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    // Configura as autorizações de acesso
    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) {
        try {
            httpSecurity.authorizeHttpRequests()
                    // Para a rota de login, estamos liberando o método post a todos
                    .antMatchers("/editoralivros/login", "/editoralivros/usuarios", "/editoralivros/pessoa", "/login").permitAll()

                    // Determina que todas as demais requisições terão de ser autenticadas
                    .anyRequest().authenticated();
            httpSecurity
                    .csrf().disable()
                    .cors().disable();

            httpSecurity
                    .formLogin().permitAll()
                    .and()
                    .logout().permitAll();

            // Autentica mas não deixa a sessão do usuário ativa (pq o objetivo é usar o token para validação de autorização)
            httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            // Faz com que antes de qualquer requisição é necessário passar pelo filtro
            httpSecurity.addFilterBefore(new AutenticacaoFiltro(new TokenUtils(), jpaService), UsernamePasswordAuthenticationFilter.class);

            return httpSecurity.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Serve para poder fazer a injeção de dependência dentro da autenticação controller, em suma, para poder usar o @Autowired
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration ac) throws Exception {
        return ac.getAuthenticationManager();
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123"));
    }
}
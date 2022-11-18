package br.senai.sc.editoralivros.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class AutenticacaoConfig extends WebSecurityConfigurerAdapter {
    // No browser, ao acessar a rota irá pedir login e senha:
    // - usuário: user
    // - senha: a que gerar quando rodar

    @Autowired
    private AutenticacaoService autenticacaoService;

    // Configura as autorizações de acesso
    @Override
    protected void configure(HttpSecurity httpSecurity) {
        try {
            httpSecurity.authorizeHttpRequests()
                    // Para a rota de login, estamos liberando o método post a todos
                    .antMatchers("/login").permitAll()
                    .antMatchers(HttpMethod.POST, "/editoralivros/pessoa").permitAll()
                    // Determina que todas as demais requisições terão de ser autenticadas
                    .anyRequest().authenticated()
//                    .and().formLogin()
                    .and().csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().addFilterBefore(new AutenticacaoFiltro(autenticacaoService), AutenticacaoFiltro.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Configura a autenticação para os acessos
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(autenticacaoService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    // Utilizado para realizar a autenticação em AutenticacaoController
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

//    Usuário feito em memória, não recomendado
//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("admin")
//                        .password("admin")
//                        .roles("ADM")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}
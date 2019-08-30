package dev.rija.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; // instancie dans JwtSpringSecurityApplication
    /**
     * Indiquer a spring security comment chercher les utilisateurs et roles
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // utilisateur en memoire
        /*PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser("admin").password(encoder.encode("1234")).roles("ADMIN", "USER")
                .and()
                .withUser("user").password(encoder.encode("1234")).roles("USER");*/

        // authentification basee sur UserDetailsService
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * Configuration des droits d'acces i.e quelle route pour quel role
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * spring security ne va pas generer le synchronized token csrf
         * (input hidden dans le formulaire d'authentification de spring security par defaut)
         * la partie front end n'aura pas besoin de generer ce token csrf
         */
        http.csrf().disable();

        // Desactiver authentification basee sur les sessions
        // http.formLogin(); // localhost:8080/login

        // securite en mode stateless
        http.sessionManagement().sessionCreationPolicy((SessionCreationPolicy.STATELESS));

        // gestion des droits sur les resources
        http.authorizeRequests().antMatchers("/login/**", "/register/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/tasks/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
    }
}

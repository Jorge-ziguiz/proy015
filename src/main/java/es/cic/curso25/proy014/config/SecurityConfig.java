package es.cic.curso25.proy014.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

        @Bean
        public UserDetailsService UserDetailsService() {
                // TODO DE MOMENTONTO EN LA VERSION 1.0.0 USUARIOS PREDETERMIIADOS UNA VEZ
                // TERMINADAO AHI SI HACER UN CREAR USARUIO CON BBDD
                var userAdmin = User.withUsername("admin")
                                .password("{noop}admin")
                                .roles("ADMIN")
                                .build();

                var user = User.withUsername("user")
                                .password("{noop}user")
                                .roles("USER")
                                .build();
                return new InMemoryUserDetailsManager(userAdmin, user);
        }

        @Bean
        public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(authorize -> authorize
                                                .anyRequest().authenticated())
                                .formLogin(Customizer.withDefaults());
                return http.build();
        }

        @Bean
        @Order(1)
        public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
                http
                                .securityMatcher("/vehiculo/**")
                                .securityMatcher("/multa/**")
                                .securityMatcher("/plaza/**")
                                .authorizeHttpRequests(authorize -> authorize
                                                .anyRequest().hasRole("ADMIN"))
                                .httpBasic(Customizer.withDefaults());
                return http.build();
        }

}

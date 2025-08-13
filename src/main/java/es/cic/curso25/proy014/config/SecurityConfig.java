package es.cic.curso25.proy014.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

        @Bean
        public UserDetailsService UserDetailsService() {
                var userAdmin = User.withUsername("admin")
                                .password(passwordEncoder().encode("#~@A41#s#ds@(.-"))
                                .roles("ADMIN")
                                .build();

                var user = User.withUsername("user")
                                .password(passwordEncoder().encode("#|@5{31./&}(.-"))
                                .roles("USER")
                                .build();
                return new InMemoryUserDetailsManager(userAdmin, user);
        }

        @Bean
        public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/**").hasAnyRole("USER", "ADMIN")
                                .anyRequest().authenticated())
                                .httpBasic(httpBasic -> {
                                })
                                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

}

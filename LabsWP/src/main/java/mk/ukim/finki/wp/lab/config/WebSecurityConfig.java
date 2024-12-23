package mk.ukim.finki.wp.lab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final PasswordEncoder passwordEncoder;


    public WebSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions().disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/h2" , "/listSongs", "/songs", "/assets/**", "/register", "/artist/artist-list", "/songs/song-details")
                        .permitAll()
                        .anyRequest().hasAnyRole("ADMIN", "MODERATOR")


                )

                .formLogin(form -> form
                        .permitAll()
                        .defaultSuccessUrl("/listSongs")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/listSongs")
                        .permitAll()
                );

        return http.build();
    }

    // In Memory Authentication
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder()
                .username("elena.atanasoska")
                .password(passwordEncoder.encode("ea"))
                .roles("USER")
                .build();
        UserDetails user2 = User.builder()
                .username("darko.sasanski")
                .password(passwordEncoder.encode("ds"))
                .roles("USER")
                .build();
        UserDetails user3 = User.builder()
                .username("ana.todorovska")
                .password(passwordEncoder.encode("at"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();
        UserDetails moderator = User.builder()
                .username("moderator")
                .password(passwordEncoder.encode("moderator"))
                .roles("MODERATOR")
                .build();

        return new InMemoryUserDetailsManager(user1, user2, user3, admin, moderator);
    }


}

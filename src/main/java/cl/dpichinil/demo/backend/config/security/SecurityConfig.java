package cl.dpichinil.demo.backend.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cl.dpichinil.demo.backend.util.ConstantUtil;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final JwtAuthenticationEntryPoint unauthorizedHandler; 
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Deshabilitar CSRF para APIs REST sin estado
            .exceptionHandling(ex -> ex 
                .authenticationEntryPoint(unauthorizedHandler)
                .accessDeniedHandler(accessDeniedHandler)
            ) // Manejo personalizado de errores de autenticación
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    ConstantUtil.AUTORIZED_REQUEST_LOGIN, 
                    ConstantUtil.AUTORIZED_REQUEST_API_DOCS_1, 
                    ConstantUtil.AUTORIZED_REQUEST_API_DOCS_2, 
                    ConstantUtil.AUTORIZED_REQUEST_SWAGGER_1,
                    ConstantUtil.AUTORIZED_REQUEST_SWAGGER_2
                ).permitAll() // Permitir acceso sin autenticar a endpoints de autenticación
                .anyRequest().authenticated() // Requerir autenticación para cualquier otro request
            )
            
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Hacer el servidor sin estado (Stateless)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Añadir filtro JWT

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

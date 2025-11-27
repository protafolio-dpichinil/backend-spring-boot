package cl.dpichinil.demo.backend.config.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import cl.dpichinil.demo.backend.entity.UserEntity;
import cl.dpichinil.demo.backend.repository.UserRepository;
import cl.dpichinil.demo.backend.util.ConstantUtil;
import cl.dpichinil.demo.backend.util.FunctionUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final JwtProvider jwtpProvider;
    private final FunctionUtil functionUtil;
    private final UserRepository userRepository;
    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException {
        final String authHeader = request.getHeader(ConstantUtil.AUTHORIZATION_HEADER);
       
        // 1. Verificar si hay un token
        if (authHeader == null || !authHeader.startsWith(ConstantUtil.BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extraer el token y username del token
        String jwt = authHeader.substring(7);
        String username = jwtpProvider.extractUsername(jwt);

        // 3. Validar y autenticar (si el usuario no está ya autenticado en el contexto)
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            Optional<UserEntity> op = userRepository.findByUsername(username);
            if(op.isPresent()){
                if (jwtpProvider.isTokenValid(jwt, username)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            functionUtil.mapRolesToAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Actualizar el contexto de seguridad
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } 
            
        }
        filterChain.doFilter(request, response);
    }
}

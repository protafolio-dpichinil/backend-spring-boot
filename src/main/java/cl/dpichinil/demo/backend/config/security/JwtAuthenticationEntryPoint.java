package cl.dpichinil.demo.backend.config.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import cl.dpichinil.demo.backend.dto.ResponseDto;
import cl.dpichinil.demo.backend.util.FunctionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{
    private final FunctionUtil functionUtil;
    
    public JwtAuthenticationEntryPoint(FunctionUtil functionUtil) {
        this.functionUtil = functionUtil;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        log.error("Error 401 NO AUTORIZADO. Mensaje: {} ", exception.getMessage());
        String message = "Acceso denegado. Se requiere una autenticación válida.";
        functionUtil.writeErrorResponse(HttpStatus.UNAUTHORIZED, message, response);
    }

}

package cl.dpichinil.demo.backend.config.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import cl.dpichinil.demo.backend.dto.ResponseDto;
import cl.dpichinil.demo.backend.util.FunctionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final FunctionUtil functionUtil;
    
    public CustomAccessDeniedHandler(FunctionUtil functionUtil) {
        this.functionUtil = functionUtil;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException exception) throws IOException, ServletException {
        log.error("Error 403 FORBBIDEN. Mensaje: {} ", exception.getMessage());
        String message = "Acceso denegado. Se requiere permisos válidos.";
        functionUtil.writeErrorResponse(HttpStatus.FORBIDDEN, message, response);
    }

}

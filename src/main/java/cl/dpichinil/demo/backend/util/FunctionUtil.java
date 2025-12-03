package cl.dpichinil.demo.backend.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.dpichinil.demo.backend.config.exception.CustomException;
import cl.dpichinil.demo.backend.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FunctionUtil {
    public Collection<? extends GrantedAuthority> mapRolesToAuthorities() {
        List<String> roles = List.of("USER");
        // Usamos Streams para mapear cada rol (String) a un nuevo SimpleGrantedAuthority
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toList());
    }

    public String parseObjectToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error al convertir objeto a JSON: {}" , e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al convertir objeto a JSON", e);
        }
        
        
    }

    public <T>T jsonToObject(String json, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Transformación (Deserialización) de JSON String a Objeto Java
            return mapper.readValue(json, clazz); 
        } catch (Exception e) {
            log.error("Error al convertir JSON a Objeto: {}" , e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al convertir Json a objeto", e);
        }
    }

    public void writeErrorResponse(HttpStatus status, String message, HttpServletResponse response) {
        try {
            response.setContentType("application/json");
            response.setStatus(status.value());
            ResponseDto dto = new ResponseDto(false, message);
            response.getWriter().write(parseObjectToJson(dto));    
        } catch (Exception e) {
            log.error("Error al escribir la respuesta JSON: {}" , e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al escribir la respuesta JSON", e);
        }
    }
}

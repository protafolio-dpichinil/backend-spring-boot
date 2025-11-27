package cl.dpichinil.demo.backend.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class FunctionUtil {
    public Collection<? extends GrantedAuthority> mapRolesToAuthorities() {
        List<String> roles = List.of("USER");
        // Usamos Streams para mapear cada rol (String) a un nuevo SimpleGrantedAuthority
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toList());
    }
}

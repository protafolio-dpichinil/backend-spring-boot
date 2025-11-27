package cl.dpichinil.demo.backend.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import cl.dpichinil.demo.backend.entity.UserEntity;
import cl.dpichinil.demo.backend.repository.UserRepository;
import cl.dpichinil.demo.backend.util.FunctionUtil;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Lazy
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final FunctionUtil functionUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if(username == null || password == null){
            log.error("Authentication failed: username or password is null");
            throw  new BadCredentialsException("Invalid username or password");
        }
        if(username.isEmpty() || password.isEmpty()){
            log.error("Authentication failed: username or password is empty");
            throw  new BadCredentialsException("Invalid username or password");
        }
        if(username.isBlank() || password.isBlank()){
            log.error("Authentication failed: username or password is blank");
            throw  new BadCredentialsException("Invalid username or password");
        }
        Optional<UserEntity> op = userRepository.findByUsername(username);
        if(op.isEmpty()){
            log.error("Authentication failed for username: {} User not Found", username);
            throw new BadCredentialsException("Invalid username or password");
        }
        UserEntity entity = op.get();
        if (entity.getActive() == null || !entity.getActive()) {
            log.error("Authentication failed for username: {} User is in status inactive", username);
            throw new BadCredentialsException("User is inactive");
        }

        if (!passwordEncoder.matches(password, entity.getPassword())) {
            log.error("Authentication failed for username: {} password matches failed", username);
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(entity.getUsername(), entity.getPassword(), functionUtil.mapRolesToAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
    
    

    
}

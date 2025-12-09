package cl.dpichinil.demo.backend.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.dpichinil.demo.backend.config.exception.CustomException;
import cl.dpichinil.demo.backend.config.security.JwtProvider;
import cl.dpichinil.demo.backend.dto.JWTAuthDto;
import cl.dpichinil.demo.backend.dto.ResponseDto;
import cl.dpichinil.demo.backend.dto.ResponsePageDto;
import cl.dpichinil.demo.backend.dto.UserDto;
import cl.dpichinil.demo.backend.entity.UserEntity;
import cl.dpichinil.demo.backend.mapper.UserMapper;
import cl.dpichinil.demo.backend.repository.UserRepository;
import cl.dpichinil.demo.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public UserServiceImpl(
            UserRepository userRepository, 
            PasswordEncoder passwordEncoder,
            JwtProvider jwtProvider
        ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public ResponseEntity<ResponseDto> getAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        validateNonEmptyListUserEntities(userEntities);
        List<UserDto> userDtos = userEntities.stream()
                .map(UserMapper::toDto)
                .toList();
        return ResponseEntity.ok(new ResponseDto(true, "Users retrieved successfully", userDtos));
    }
   
    @Override
    public ResponseEntity<ResponseDto> getById(Integer id) {
        validateNonNullId(id);
        Optional<UserEntity> op = userRepository.findById(id);
        validateNonEmptyOptional(op);
        UserEntity userEntity = op.get();
        UserDto userDto = UserMapper.toDto(userEntity);
        return ResponseEntity.ok(new ResponseDto(true, "User found", userDto));
    }

    @Override
    public ResponseEntity<ResponseDto> getByUsername(String username) {
        validateNonEmptyUsername(username);
        Optional<UserEntity> op = userRepository.findByUsername(username);
        validateNonEmptyOptional(op);
        UserEntity userEntity = op.get();
        UserDto userDto = UserMapper.toDto(userEntity);
        return ResponseEntity.ok(new ResponseDto(true, "User found", userDto));
    }

    @Override
    public ResponseEntity<ResponseDto> gettAll(Integer page, Integer size) {
        int p = page == null || page < 0 ? 0 : page;
        int s = size == null || size <= 0 ? 10 : size;
        PageRequest pageable = PageRequest.of(p, s);
        Page<UserEntity> pageResult = userRepository.findAll(pageable);
        if (pageResult.getTotalElements() == 0) {
            throw new CustomException(HttpStatus.NOT_FOUND, "No users found");
        }
        List<UserDto> userDtos = pageResult.getContent().stream()
                .map(UserMapper::toDto)
                .toList();        
        ResponsePageDto responsePageDto = ResponsePageDto.builder()
                .size(pageResult.getSize())
                .number(pageResult.getNumber())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .content(userDtos)
                .build();
        return ResponseEntity.ok(new ResponseDto(true, "Users retrieved successfully", responsePageDto));
    }

    @Override
    public ResponseEntity<ResponseDto> save(UserDto userDto) {
        validateNotEmptyUserDto(userDto);
        UserEntity userEntity = UserMapper.toEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(true, "user created successfully", userEntity.getId()));
    }

    @Override
    public ResponseEntity<ResponseDto> updateUserDto(Integer id, UserDto userDto) {
        validateUpdateFields(id, userDto);
        Optional<UserEntity> op = userRepository.findById(id);
        validateNonEmptyOptional(op);
        UserEntity userEntity = op.get();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setActive(userDto.getActive());
        userRepository.save(userEntity);
        return ResponseEntity.ok(new ResponseDto(true, "User updated successfully"));
    }

    @Override
    public ResponseEntity<ResponseDto> delete(Integer id) {
        validateNonNullId(id);
        Optional<UserEntity> op = userRepository.findById(id);
        validateNonEmptyOptional(op);
        userRepository.deleteById(id);
        return ResponseEntity.ok(new ResponseDto(true, "User deleted successfully"));
    }

    @Override
    public ResponseEntity<ResponseDto> changePassword(Integer id, UserDto userDto) {
        validateNonNullId(id);
        validateNonEmptyPassword(userDto.getPassword());
        Optional<UserEntity> op = userRepository.findById(id);
        validateNonEmptyOptional(op);
        UserEntity userEntity = op.get();
        userEntity.setPassword(userDto.getPassword());
        userRepository.save(userEntity);
        return ResponseEntity.ok(new ResponseDto(true, "Password updated successfully"));
    }

    @Override
    public ResponseEntity<ResponseDto> resetPassword(UserDto userDto) {
        validateNonEmptyUsername(userDto.getUsername());
        Optional<UserEntity> op = userRepository.findByUsername(userDto.getUsername());
        validateNonEmptyOptional(op);
        UserEntity user = op.get();

        String newPassword = UUID.randomUUID().toString().substring(0, 8);
        String encryptedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encryptedPassword);
        userRepository.save(user);

        log.info("Password generada para '{}': {}", user.getUsername(), newPassword);
        log.info("Password encriptada: {}", encryptedPassword);
        ResponseDto responseDto = new ResponseDto(true, "Password para el usuario '" + user.getUsername() + "' ha sido reseteada y la información impresa en consola.");
        return ResponseEntity.ok(responseDto);
    }

    private void validateNonEmptyPassword(String password) {
        if(password == null || password.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Password is required");
        }
    }

    private void validateNonEmptyOptional(Optional<UserEntity> op) {
        if(op.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    private void validateNotEmptyUserDto(UserDto userDto) {
        validateNonNullUserDto(userDto);
        validateNonNullId(userDto.getId());
        validateNonEmptyUsername(userDto.getUsername());
        validateNonEmptyPassword(userDto.getPassword());
        validateNonEmpyActive(userDto.getActive());
    }

    private void validateNonNullUserDto(UserDto user) {
        if(user == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid user data");
        }
    }

    private void validateNonEmpyActive(Boolean active) {
        if(active == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Active status is required");
        }
    }

    private void validateNonEmptyUsername(String username) {
        if(username == null || username.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Username is required");
        }
    }

    private void validateUpdateFields(Integer id, UserDto userDto) {
        validateNonNullId(id);
        validateNotEmptyUserDto(userDto);
    }
    
    private void validateNonNullId(Integer id) {
        if(id == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid user ID");
        }
    }

    private void validateNonEmptyListUserEntities(List<UserEntity> userEntities) {
        if(userEntities.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "No users found");
        }
    }

    

    @Override
    public ResponseEntity<ResponseDto> login(UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();

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

        Map<String, Object> extraClaims = Map.of(); // Puedes agregar claims adicionales si es necesario
        String token = jwtProvider.generateToken(extraClaims, userDto.getUsername());
        JWTAuthDto jwtAuthDto = new JWTAuthDto(token);
        return ResponseEntity.ok(new ResponseDto(true, "Login successful", jwtAuthDto));
    }

    
}

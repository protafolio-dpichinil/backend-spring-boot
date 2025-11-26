package cl.dpichinil.demo.backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cl.dpichinil.demo.backend.config.exception.CustomException;
import cl.dpichinil.demo.backend.dto.ResponseDto;
import cl.dpichinil.demo.backend.dto.ResponsePageDto;
import cl.dpichinil.demo.backend.dto.UserDto;
import cl.dpichinil.demo.backend.entity.UserEntity;
import cl.dpichinil.demo.backend.mapper.UserMapper;
import cl.dpichinil.demo.backend.repository.UserRepository;
import cl.dpichinil.demo.backend.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        Optional<UserEntity> op = userRepository.findById(id);
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
        userRepository.save(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(true, "user created successfully", userEntity.getId()));
    }

    @Override
    public ResponseEntity<ResponseDto> update(Integer id, UserDto userDto) {
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
        Optional<UserEntity> op = userRepository.findById(id);
        validateNonEmptyOptional(op);
        userRepository.deleteById(id);
        return ResponseEntity.ok(new ResponseDto(true, "User deleted successfully"));
    }

    @Override
    public ResponseEntity<ResponseDto> resetPassword(Integer id, UserDto userDto) {
        Optional<UserEntity> op = userRepository.findById(id);
        validateNonEmptyOptional(op);
        validateNonEmptyPassword(userDto.getPassword());
        UserEntity userEntity = op.get();
        userEntity.setPassword(userDto.getPassword());
        userRepository.save(userEntity);
        return ResponseEntity.ok(new ResponseDto(true, "Password updated successfully"));
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
        if(userDto == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid user data");
        }
        if(userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Username is required");
        }
        if(userDto.getActive() == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Active status is required");
        }
        validateNonEmptyPassword(userDto.getPassword());
    }

    private void validateUpdateFields(Integer id, UserDto userDto) {
        if(id == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid user ID");
        }
        validateNotEmptyUserDto(userDto);
    }

    private void validateNonEmptyListUserEntities(List<UserEntity> userEntities) {
        if(userEntities.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "No users found");
        }
    }
}

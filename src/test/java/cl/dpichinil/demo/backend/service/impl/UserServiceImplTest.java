package cl.dpichinil.demo.backend.service.impl;

import cl.dpichinil.demo.backend.config.exception.CustomException;
import cl.dpichinil.demo.backend.dto.ResponseDto;
import cl.dpichinil.demo.backend.dto.ResponsePageDto;
import cl.dpichinil.demo.backend.dto.UserDto;
import cl.dpichinil.demo.backend.entity.UserEntity;
import cl.dpichinil.demo.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserServiceImpl service;

    @Captor
    private ArgumentCaptor<UserEntity> entityCaptor;

    @BeforeEach
    void setUp() {
        service = new UserServiceImpl(userRepository);
    }

    private UserEntity sampleEntity(int id, String username) {
        UserEntity e = new UserEntity();
        e.setId(id);
        e.setUsername(username);
        e.setPassword("p");
        e.setActive(true);
        return e;
    }

    private UserDto sampleDto(String username) {
        UserDto d = new UserDto();
        d.setUsername(username);
        d.setPassword("p");
        d.setActive(true);
        return d;
    }

    @Test
    void getAll_whenEmpty_throwsNotFound() {
        when(userRepository.findAll()).thenReturn(List.of());

        CustomException ex = assertThrows(CustomException.class, () -> service.getAll());
        assertThat(ex.status).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getAll_whenHasData_returnsDto() {
        when(userRepository.findAll()).thenReturn(List.of(sampleEntity(1, "u1")));

        ResponseEntity<ResponseDto> res = service.getAll();
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody()).isNotNull();
        assertThat(res.getBody().getMessage()).contains("Users retrieved");
        assertThat(res.getBody().getData()).isInstanceOf(List.class);
    }

    @Test
    void getById_notFound_throws() {
        when(userRepository.findById(5)).thenReturn(Optional.empty());

        CustomException ex = assertThrows(CustomException.class, () -> service.getById(5));
        assertThat(ex.status).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getById_found_returnsUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(sampleEntity(1, "u1")));

        ResponseEntity<ResponseDto> res = service.getById(1);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody().getMessage()).isEqualTo("User found");
        assertThat(res.getBody().getData()).isNotNull();
    }

    @Test
    void gettAll_whenEmpty_throwsNotFound() {
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(List.of()));

        CustomException ex = assertThrows(CustomException.class, () -> service.gettAll(0, 10));
        assertThat(ex.status).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void gettAll_whenHasData_returnsPageDto() {
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(sampleEntity(1, "u1")), PageRequest.of(0, 10), 1));

        ResponseEntity<ResponseDto> res = service.gettAll(0, 10);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody().getData()).isInstanceOf(ResponsePageDto.class);
        ResponsePageDto page = (ResponsePageDto) res.getBody().getData();
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getContent()).isInstanceOf(List.class);
    }

    @Test
    void save_whenInvalidDto_throwsBadRequest() {
        CustomException ex1 = assertThrows(CustomException.class, () -> service.save(null));
        assertThat(ex1.status).isEqualTo(HttpStatus.BAD_REQUEST);

        UserDto missingUsername = new UserDto();
        missingUsername.setActive(true);
        missingUsername.setPassword("p");
        CustomException ex2 = assertThrows(CustomException.class, () -> service.save(missingUsername));
        assertThat(ex2.status).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void save_whenValid_savesAndReturnsCreated() {
        UserDto dto = sampleDto("u1");
        // emulate repository assigning id on save
        doAnswer(invocation -> {
            UserEntity e = invocation.getArgument(0);
            e.setId(99);
            return e;
        }).when(userRepository).save(any(UserEntity.class));

        ResponseEntity<ResponseDto> resp = service.save(dto);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getBody().getData()).isEqualTo(99);
        verify(userRepository).save(entityCaptor.capture());
        assertThat(entityCaptor.getValue().getUsername()).isEqualTo("u1");
    }

    @Test
    void update_invalidId_throwsBadRequest() {
        UserDto dto = sampleDto("u1");
        CustomException ex = assertThrows(CustomException.class, () -> service.update(null, dto));
        assertThat(ex.status).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void update_notFound_throwsNotFound() {
        when(userRepository.findById(2)).thenReturn(Optional.empty());
        UserDto dto = sampleDto("u1");
        CustomException ex = assertThrows(CustomException.class, () -> service.update(2, dto));
        assertThat(ex.status).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void update_success_saves() {
        UserEntity e = sampleEntity(2, "old");
        when(userRepository.findById(2)).thenReturn(Optional.of(e));

        UserDto dto = sampleDto("new");
        dto.setPassword("new-pass");
        dto.setActive(false);

        ResponseEntity<ResponseDto> res = service.update(2, dto);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userRepository).save(entityCaptor.capture());
        assertThat(entityCaptor.getValue().getUsername()).isEqualTo("new");
    }

    @Test
    void delete_notFound_throwsNotFound() {
        when(userRepository.findById(3)).thenReturn(Optional.empty());
        CustomException ex = assertThrows(CustomException.class, () -> service.delete(3));
        assertThat(ex.status).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void delete_success_deletes() {
        when(userRepository.findById(4)).thenReturn(Optional.of(sampleEntity(4, "u4")));
        ResponseEntity<ResponseDto> res = service.delete(4);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void resetPassword_missingPassword_throwsBadRequest() {
        when(userRepository.findById(5)).thenReturn(Optional.of(sampleEntity(5, "u5")));
        UserDto dto = new UserDto(); // no password
        CustomException ex = assertThrows(CustomException.class, () -> service.resetPassword(5, dto));
        assertThat(ex.status).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void resetPassword_success_saves() {
        UserEntity e = sampleEntity(6, "u6");
        when(userRepository.findById(6)).thenReturn(Optional.of(e));
        UserDto dto = new UserDto();
        dto.setPassword("new-p");

        ResponseEntity<ResponseDto> res = service.resetPassword(6, dto);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userRepository).save(entityCaptor.capture());
        assertThat(entityCaptor.getValue().getPassword()).isEqualTo("new-p");
    }
}

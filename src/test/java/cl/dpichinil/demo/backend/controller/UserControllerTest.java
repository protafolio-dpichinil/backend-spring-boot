package cl.dpichinil.demo.backend.controller;

import cl.dpichinil.demo.backend.dto.ResponseDto;
import cl.dpichinil.demo.backend.dto.UserDto;
import cl.dpichinil.demo.backend.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;


    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getAll_returnsList() throws Exception {
        when(userService.getAll()).thenReturn(ResponseEntity.ok(new ResponseDto(true, "ok", "payload")));

        mockMvc.perform(get("/users/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("ok"))
                .andExpect(jsonPath("$.data").value("payload"));
    }

    @Test
    void getPaginated_returnsPage() throws Exception {
        when(userService.gettAll(eq(0), eq(10))).thenReturn(ResponseEntity.ok(new ResponseDto(true, "paged", "pageData")));

        mockMvc.perform(get("/users/paginated/0/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("paged"))
                .andExpect(jsonPath("$.data").value("pageData"));
    }

    @Test
    void getById_returnsUser() throws Exception {
        when(userService.getById(1)).thenReturn(ResponseEntity.ok(new ResponseDto(true, "found", new UserDto())));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("found"));
    }
    
    @Test
    void createUser_returnsCreated() throws Exception {
        UserDto dto = new UserDto();
        dto.setUsername("test");

        when(userService.save(any(UserDto.class))).thenReturn(ResponseEntity.ok(new ResponseDto(true, "created", dto)));

        mockMvc.perform(post("/users/")
                .contentType("application/json")
                .content("{ \"username\": \"test\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("created"))
                .andExpect(jsonPath("$.data.username").value("test"));
    }

    @Test
    void updateUser_returnsOk() throws Exception {
        UserDto dto = new UserDto();
        dto.setUsername("updated");

        when(userService.update(eq(1), any(UserDto.class))).thenReturn(ResponseEntity.ok(new ResponseDto(true, "updated", dto)));

        mockMvc.perform(put("/users/1")
                .contentType("application/json")
                .content("{ \"username\": \"updated\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("updated"))
                .andExpect(jsonPath("$.data.username").value("updated"));
    }

    @Test
    void resetPassword_returnsOk() throws Exception {
        UserDto dto = new UserDto();
        dto.setPassword("new-pass");

        when(userService.resetPassword(eq(1), any(UserDto.class))).thenReturn(ResponseEntity.ok(new ResponseDto(true, "password reset", null)));

        mockMvc.perform(patch("/users/1/password")
                .contentType("application/json")
                .content("{ \"password\": \"new-pass\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("password reset"));
    }

    @Test
    void deleteUser_returnsOk() throws Exception {
        when(userService.delete(1)).thenReturn(ResponseEntity.ok(new ResponseDto(true, "deleted", null)));

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("deleted"));
    }
}

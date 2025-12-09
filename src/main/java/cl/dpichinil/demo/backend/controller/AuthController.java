package cl.dpichinil.demo.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.dpichinil.demo.backend.controller.openapi.AuthOpenApi;
import cl.dpichinil.demo.backend.dto.ResponseDto;
import cl.dpichinil.demo.backend.dto.UserDto;
import cl.dpichinil.demo.backend.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthOpenApi {
    

    private final UserService userService;
    

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody UserDto userDto){
        return userService.login(userDto);
    }
}

package cl.dpichinil.demo.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.dpichinil.demo.backend.dto.ResponseDto;
import cl.dpichinil.demo.backend.dto.UserDto;
import cl.dpichinil.demo.backend.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    

    private final UserService userService;
    

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> authenticateUser(@RequestBody UserDto userDto){
        return userService.login(userDto);
    }
}

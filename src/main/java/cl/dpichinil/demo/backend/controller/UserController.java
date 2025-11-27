package cl.dpichinil.demo.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.dpichinil.demo.backend.dto.ResponseDto;
import cl.dpichinil.demo.backend.dto.UserDto;
import cl.dpichinil.demo.backend.service.UserService;
import cl.dpichinil.demo.backend.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userService = userServiceImpl;
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/paginated/{page}/{size}")
    public ResponseEntity<ResponseDto> gettAll(
            @PathVariable(required = false) Integer page,
            @PathVariable(required = false) Integer size) {
        return userService.gettAll(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ResponseDto> getByUsername(@PathVariable String username) {
        return userService.getByUsername(username);
    }

    @PostMapping("/")
    public ResponseEntity<ResponseDto> save(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> update(@PathVariable Integer id, @RequestBody UserDto userDto) {
        return userService.update(id, userDto);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<ResponseDto> resetPassword(@PathVariable Integer id, @RequestBody UserDto userDto) {
        return userService.resetPassword(id, userDto);
    }

    @PutMapping("/reset/")
    public ResponseEntity<ResponseDto> resetPassword(@RequestBody UserDto userDto) {
        return userService.resetPassword(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable Integer id) {
        return userService.delete(id);
    }
}

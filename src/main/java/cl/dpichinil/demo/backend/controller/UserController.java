package cl.dpichinil.demo.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import cl.dpichinil.demo.backend.controller.openapi.UserOpenApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.dpichinil.demo.backend.dto.ResponseDto;
import cl.dpichinil.demo.backend.dto.UserDto;
import cl.dpichinil.demo.backend.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController implements UserOpenApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> update(@PathVariable Integer id, @RequestBody UserDto userDto) {
        return userService.updateUserDto(id, userDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable Integer id) {
        return userService.delete(id);
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<ResponseDto> getAll() {
        return userService.getAll();
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<ResponseDto> save(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @Override
    @PatchMapping("/{id}/password")
    public ResponseEntity<ResponseDto> changePassword(@PathVariable Integer id, @RequestBody UserDto userDto) {
        return userService.changePassword(id, userDto);
    }

    @Override
    @PatchMapping("/reset/")
    public ResponseEntity<ResponseDto> resetPassword(@RequestBody UserDto userDto) {
        return userService.resetPassword(userDto);
    }

    @Override
    @GetMapping("/username/{username}")
    public ResponseEntity<ResponseDto> getByUsername(@PathVariable String username) {
        return userService.getByUsername(username);
    }

    @Override
    @GetMapping("/paginated")
    public ResponseEntity<ResponseDto> getAllPaginated(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return userService.gettAll(page, size);
    }
}

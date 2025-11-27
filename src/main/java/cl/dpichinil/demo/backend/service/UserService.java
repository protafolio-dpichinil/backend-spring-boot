package cl.dpichinil.demo.backend.service;

import org.springframework.http.ResponseEntity;

import cl.dpichinil.demo.backend.dto.ResponseDto;
import cl.dpichinil.demo.backend.dto.UserDto;

public interface UserService {
    /**
     * Get all users.
     * @return
     */
    ResponseEntity<ResponseDto> getAll();

    /**
     * Get paginated list of users.
     * Added as `gettAll` (paginated) to avoid breaking existing API.
     * @param page zero-based page index (optional)
     * @param size page size (optional)
     */
    ResponseEntity<ResponseDto> gettAll(Integer page, Integer size);

    /**
     * Get user by ID.
     * @param id
     * @return
     */
    ResponseEntity<ResponseDto> getById(Integer id);

    /**
     * Save a new user.
     * @param userDto
     * @return
     */
    ResponseEntity<ResponseDto> save(UserDto userDto);

    /**
     * Update an existing user.
     * @param id
     * @param userDto
     * @return
     */
    ResponseEntity<ResponseDto> update(Integer id, UserDto userDto);

    /**
     * Delete a user by ID.
     * @param id
     * @return
     */
    ResponseEntity<ResponseDto> delete(Integer id);

    /**
     * Reset user password.
     * @param id
     * @param userDto
     * @return
     */
    ResponseEntity<ResponseDto> resetPassword(Integer id, UserDto userDto);

    /**
     * Get user by username.
     * @param username
     * @return
     */
    ResponseEntity<ResponseDto> getByUsername(String username);

    /**
     * Reset password by username.
     * @param userDto
     * @return
     */
    ResponseEntity<ResponseDto> resetPassword(UserDto userDto);

    /**
     * User login.
     * @param userDto
     * @return
     */
    ResponseEntity<ResponseDto> login(UserDto userDto);

}

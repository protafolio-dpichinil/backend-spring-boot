package cl.dpichinil.demo.backend.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private Boolean active;
}

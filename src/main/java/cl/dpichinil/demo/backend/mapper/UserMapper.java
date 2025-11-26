package cl.dpichinil.demo.backend.mapper;

import cl.dpichinil.demo.backend.dto.UserDto;
import cl.dpichinil.demo.backend.entity.UserEntity;

public class UserMapper {
    public static UserDto toDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setActive(entity.getActive());
        return dto;
    }

    public static UserEntity toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setActive(dto.getActive());
        return entity;
    }
}

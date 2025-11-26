package cl.dpichinil.demo.backend.mapper;

import cl.dpichinil.demo.backend.dto.UserDto;
import cl.dpichinil.demo.backend.entity.UserEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void toDto_handlesNull() {
        UserDto dto = UserMapper.toDto(null);
        assertThat(dto).isNull();
    }

    @Test
    void toEntity_handlesNull() {
        UserEntity entity = UserMapper.toEntity(null);
        assertThat(entity).isNull();
    }

    @Test
    void toDto_and_toEntity_roundtrip() {
        UserEntity e = new UserEntity();
        e.setId(10);
        e.setUsername("user1");
        e.setPassword("pass");
        e.setActive(true);

        UserDto dto = UserMapper.toDto(e);
        assertThat(dto.getId()).isEqualTo(10);
        assertThat(dto.getUsername()).isEqualTo("user1");

        UserEntity e2 = UserMapper.toEntity(dto);
        assertThat(e2.getId()).isEqualTo(e.getId());
        assertThat(e2.getUsername()).isEqualTo(e.getUsername());
        assertThat(e2.getPassword()).isEqualTo(e.getPassword());
        assertThat(e2.getActive()).isEqualTo(e.getActive());
    }
}

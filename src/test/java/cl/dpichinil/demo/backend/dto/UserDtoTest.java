package cl.dpichinil.demo.backend.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserDtoTest {

    @Test
    void setters_getters_and_equals_work() {
        UserDto a = new UserDto();
        a.setId(1);
        a.setUsername("u1");
        a.setPassword("p");
        a.setActive(true);

        assertThat(a.getId()).isEqualTo(1);
        assertThat(a.getUsername()).isEqualTo("u1");
        assertThat(a.getPassword()).isEqualTo("p");
        assertThat(a.getActive()).isTrue();

        UserDto b = new UserDto();
        b.setId(1);
        b.setUsername("u1");
        b.setPassword("p");
        b.setActive(true);

        assertThat(a).isEqualTo(b);
        assertThat(a.hashCode()).isEqualTo(b.hashCode());
        assertThat(a.toString()).contains("u1");
    }
}

package cl.dpichinil.demo.backend.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserEntityTest {

    @Test
    void constructors_getters_and_equals() {
        UserEntity a = new UserEntity();
        a.setId(1);
        a.setUsername("u1");
        a.setPassword("p");
        a.setActive(true);

        assertThat(a.getId()).isEqualTo(1);
        assertThat(a.getUsername()).isEqualTo("u1");

        UserEntity b = new UserEntity(1, "u1", "p", true);
        assertThat(a).isEqualTo(b);
        assertThat(a.hashCode()).isEqualTo(b.hashCode());
        assertThat(a.toString()).contains("u1");
    }
}

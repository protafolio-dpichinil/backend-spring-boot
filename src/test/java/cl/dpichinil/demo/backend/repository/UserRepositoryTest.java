package cl.dpichinil.demo.backend.repository;

import cl.dpichinil.demo.backend.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void save_and_findById_and_delete() {
        UserEntity u = new UserEntity();
        u.setUsername("repo-user");
        u.setPassword("p");
        u.setActive(true);

        UserEntity saved = userRepository.save(u);
        assertThat(saved.getId()).isNotNull();

        Optional<UserEntity> fetched = userRepository.findById(saved.getId());
        assertThat(fetched).isPresent();
        assertThat(fetched.get().getUsername()).isEqualTo("repo-user");

        userRepository.deleteById(saved.getId());
        Optional<UserEntity> afterDelete = userRepository.findById(saved.getId());
        assertThat(afterDelete).isEmpty();
    }

    @Test
    void findAll_returnsList() {
        userRepository.save(new UserEntity(null, "u1", "p", true));
        userRepository.save(new UserEntity(null, "u2", "p", true));

        assertThat(userRepository.findAll()).hasSizeGreaterThanOrEqualTo(2);
    }
}

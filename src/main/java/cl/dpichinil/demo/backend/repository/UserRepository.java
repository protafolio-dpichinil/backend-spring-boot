package cl.dpichinil.demo.backend.repository;

import cl.dpichinil.demo.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

}

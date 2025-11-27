package cl.dpichinil.demo.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id",nullable = false)
    private Integer id;

    @Column(name = "usr_username", nullable = false, unique = true)
    private String username;

    @Column(name = "usr_password", nullable = false)
    private String password;

    @Column(name = "usr_active", nullable = false)
    private Boolean active;

}

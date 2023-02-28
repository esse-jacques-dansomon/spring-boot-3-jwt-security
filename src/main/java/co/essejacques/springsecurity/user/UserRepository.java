package co.essejacques.springsecurity.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Author  : essejacques.co
 * Date    : 27/02/2023 22:16
 * Project : spring-security
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}


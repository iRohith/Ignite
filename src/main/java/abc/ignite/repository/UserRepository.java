package abc.ignite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import abc.ignite.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByRole(String role);
}

package ua.chernonog.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.chernonog.users.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
}

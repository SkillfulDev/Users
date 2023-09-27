package ua.chernonog.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.chernonog.users.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    List<UserEntity> findByBirthdateBetween(LocalDate from, LocalDate to);
}

package ua.chernonog.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ua.chernonog.users.entity.UserEntity;
import ua.chernonog.users.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
public class UsersRepositoryUnitTest {
    @Autowired
    private UserRepository userRepository;

    UserEntity userEntity;

    @Test
    void findAll_should_return_users_list() {
        // When
        List<UserEntity> employees = this.userRepository.findAll();
        // Then
        assertEquals(4, employees.size());
    }
    @Test
    void deleteById_should_delete_employee() {
        // When
        this.userRepository.deleteById(2L);
        Optional<UserEntity> deletedUserEntity = this.userRepository.findById(2L);
        // Then
        assertFalse(deletedUserEntity.isPresent());
    }
}

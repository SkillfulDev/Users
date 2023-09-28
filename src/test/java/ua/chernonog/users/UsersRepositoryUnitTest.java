package ua.chernonog.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ua.chernonog.users.entity.UserEntity;
import ua.chernonog.users.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

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
    @Test
    void save_should_update_existing_user() {
        // Given
        UserEntity existingUser = new UserEntity();
        existingUser.setId(3L);
        existingUser.setFirstName("FIRST_NAME");
        existingUser.setLastName("LAST_NAME");
        // When
        UserEntity updatedUser = this.userRepository.save(existingUser);
        // Then
        assertNotNull(updatedUser);
        assertEquals("FIRST_NAME", updatedUser.getFirstName());
        assertEquals("LAST_NAME", updatedUser.getLastName());
    }
    @Test
    void save_should_insert_new_employee() {
        // Given
        UserEntity newUser = new UserEntity();
        newUser.setFirstName("FIRST_NAME");
        newUser.setLastName("LAST_NAME");
        newUser.setEmail("test@gmail.com");
        newUser.setAddress("Lviv");
        newUser.setPhoneNumber("+3050545622");
        // When
        UserEntity persistedUser = this.userRepository.save(newUser);
        // Then
        assertNotNull(persistedUser);
        assertEquals(5, persistedUser.getId());
    }
    @Test
    void findAll_should_return_validUsers_list() {
        // When
        List<UserEntity> employees = this.userRepository
                .findByBirthdateBetween(LocalDate.of(1986,05,13),
                LocalDate.of(1987,05,13));
        // Then
        assertEquals(2, employees.size());
    }

}

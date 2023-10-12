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
        List<UserEntity> users = this.userRepository.findAll();
        assertEquals(4, users.size());
    }

    @Test
    void deleteById_should_delete_usere() {
        this.userRepository.deleteById(2L);
        Optional<UserEntity> deletedUserEntity = this.userRepository.findById(2L);
        assertFalse(deletedUserEntity.isPresent());
    }

    @Test
    void save_should_update_existing_user() {
        UserEntity existingUser = new UserEntity();
        existingUser.setId(3L);
        existingUser.setFirstName("Samson");
        existingUser.setLastName("Trento");
        UserEntity updatedUser = this.userRepository.save(existingUser);
        assertNotNull(updatedUser);
        assertEquals("Samson", updatedUser.getFirstName());
        assertEquals("Trento", updatedUser.getLastName());
    }

    @Test
    void save_should_insert_new_user() {
        UserEntity newUser = new UserEntity();
        newUser.setFirstName("Kris");
        newUser.setLastName("Wendy");
        newUser.setEmail("wendyt@gmail.com");
        newUser.setAddress("Lviv");
        newUser.setPhoneNumber("+304550545622");
        UserEntity persistedUser = this.userRepository.save(newUser);
        assertNotNull(persistedUser);
        assertEquals(5, persistedUser.getId());
    }

    @Test
    void findAll_should_return_validUsers_list() {
        List<UserEntity> users = this.userRepository
                .findByBirthdateBetween(LocalDate.of(1990, 05, 13),
                        LocalDate.of(2000, 05, 13));
        assertEquals(2, users.size());
    }
}

package Backend.RestaurantManagement.service;

import Backend.RestaurantManagement.model.User;
import Backend.RestaurantManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User provisionUser(String email, String fName, String lName) {
        return userRepository.findById(email).orElseGet(() -> {
            User newUser = User.builder()
                    .email(email)
                    .fName(fName)
                    .lName(lName)
                    .createdAt(LocalDateTime.now())
                    .build();
            return userRepository.save(newUser);
        });
    }
}

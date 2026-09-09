package com.ecoevent.service;

import com.ecoevent.entity.User;
import com.ecoevent.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("ROLE_PARTICIPANT");
        }
        return userRepository.save(user);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }

    public List<User> findAll() {
        return userRepository.findAllOrderByCreatedAtDesc();
    }

    public List<User> findByRole(String role) {
        return userRepository.findByRole(role);
    }

    public long countByRole(String role) {
        return userRepository.countByRole(role);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User toggleEnabled(Long id) {
        User user = findById(id);
        user.setEnabled(!user.isEnabled());
        return userRepository.save(user);
    }

    public User updateProfile(Long id, String fullName, String phone, String organization) {
        User user = findById(id);
        user.setFullName(fullName);
        user.setPhone(phone);
        user.setOrganization(organization);
        return userRepository.save(user);
    }

    public boolean changePassword(Long id, String oldPassword, String newPassword) {
        User user = findById(id);
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }
}

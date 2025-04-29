package com.tools.services;

import com.tools.dtos.UserDTO;
import com.tools.entities.User;
import com.tools.repositories.UserRepository;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UserService {

    @Inject
    private UserRepository userRepository;

    public void registerUser(UserDTO userDTO) throws Exception {
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new Exception("Email already registered.");
        }
        User user = new User(
            userDTO.getEmail(),
            userDTO.getPassword(),
            userDTO.getName(),
            userDTO.getBio(),
            userDTO.getRole()
        );
        userRepository.save(user);
    }

    public User loginUser(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new Exception("Invalid credentials.");
        }
        return user;
    }

    public void updateProfile(Long userId, UserDTO userDTO) throws Exception {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new Exception("User not found.");
        }
        user.setName(userDTO.getName());
        user.setBio(userDTO.getBio());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        userRepository.update(user);
    }
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}

package com.zomato.user.service;

import com.zomato.user.dto.AddressDTO;
import com.zomato.user.dto.UserLoginDTO;
import com.zomato.user.dto.UserProfileDTO;
import com.zomato.user.dto.UserRegistrationDTO;
import com.zomato.user.entity.User;
import com.zomato.user.entity.Address;
import com.zomato.user.exception.InvalidInputException;
import com.zomato.user.repository.UserRepository;
import com.zomato.user.exception.AuthenticationException;
import com.zomato.user.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserProfileDTO registerUser(@Valid UserRegistrationDTO registrationDTO) {
        if (registrationDTO.getEmail() == null || registrationDTO.getPassword() == null) {
            throw new InvalidInputException("Email and password are required for registration.");
        }

        if (userRepository.findByEmail(registrationDTO.getEmail()).isPresent()) {
            throw new InvalidInputException("User with this email already exists.");
        }

        User user = new User();
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(registrationDTO.getPassword());
        user.setName(registrationDTO.getName());
        user.setPhoneNumber(registrationDTO.getPhoneNumber());
        user.setAddress(null);

        User savedUser = userRepository.save(user);
        return convertToProfileDTO(savedUser);
    }

    private UserProfileDTO convertToProfileDTO(User savedUser) {
        UserProfileDTO profileDTO = new UserProfileDTO();
        profileDTO.setId(savedUser.getId());
        profileDTO.setName(savedUser.getName());
        profileDTO.setEmail(savedUser.getEmail());
        profileDTO.setPhoneNumber(savedUser.getPhoneNumber());

        if (savedUser.getAddress() != null) {
            Address address = savedUser.getAddress();
            AddressDTO addressDTO = new AddressDTO(address.getStreet(), address.getCity(), address.getState(), address.getZipCode());
            profileDTO.setActiveAddress(addressDTO);
        } else {
            profileDTO.setActiveAddress(null);
        }

        return profileDTO;
    }

    public String loginUser(@Valid UserLoginDTO loginDTO) {
        if (loginDTO.getEmail() == null || loginDTO.getPassword() == null) {
            throw new InvalidInputException("Email and password are required for login.");
        }

        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new AuthenticationException("Invalid email or password."));

        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new AuthenticationException("Invalid email or password.");
        }

        // In a real application, you would generate a JWT token here
        return "Login successful for user: " + user.getEmail();
    }

    public UserProfileDTO getCurrentUser() {
        // In a real application, you would get the current user's ID from the security context
        Long currentUserId = 1L; // Placeholder for demonstration

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + currentUserId));

        return convertToProfileDTO(user);
    }

    public UserProfileDTO updateProfile(@Valid UserProfileDTO profileDTO) {
        if (profileDTO.getId() == null) {
            throw new InvalidInputException("User ID is required to update profile.");
        }

        User user = userRepository.findById(profileDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + profileDTO.getId()));

        if (profileDTO.getName() != null) {
            user.setName(profileDTO.getName());
        }
        if (profileDTO.getEmail() != null) {
            user.setEmail(profileDTO.getEmail());
        }
        if (profileDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(profileDTO.getPhoneNumber());
        }
        if (profileDTO.getActiveAddress() != null) {
            Address address = new Address();
            address.setStreet(profileDTO.getActiveAddress().getStreet());
            address.setCity(profileDTO.getActiveAddress().getCity());
            address.setState(profileDTO.getActiveAddress().getState());
            address.setZipCode(profileDTO.getActiveAddress().getZipCode());
            user.setAddress(address);
        }

        User updatedUser = userRepository.save(user);
        return convertToProfileDTO(updatedUser);
    }

    public void changePassword(@Valid String newPassword) {
        // In a real application, you would get the current user's ID from the security context
        Long currentUserId = 1L; // Placeholder for demonstration

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + currentUserId));

        if (newPassword == null || newPassword.isEmpty()) {
            throw new InvalidInputException("New password cannot be empty.");
        }

        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public UserProfileDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        return convertToProfileDTO(user);
    }

    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        // In a real application, you would generate a password reset token and send it to the user's email
        System.out.println("Password reset initiated for user: " + user.getEmail());
    }

    public void resetPassword(String token, String newPassword) {
        // In a real application, you would validate the token and find the user associated with it
        Long userId = 1L; // Placeholder for demonstration

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (newPassword == null || newPassword.isEmpty()) {
            throw new InvalidInputException("New password cannot be empty.");
        }

        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public List<AddressDTO> getUserAddresses() {
        // In a real application, you would get the current user's ID from the security context
        Long currentUserId = 1L; // Placeholder for demonstration

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + currentUserId));

        if (user.getAddress() == null) {
            return Collections.emptyList();
        }

        Address address = user.getAddress();
        AddressDTO addressDTO = new AddressDTO(address.getStreet(), address.getCity(), address.getState(), address.getZipCode());
        return Collections.singletonList(addressDTO);
    }

    public AddressDTO addAddress(@Valid AddressDTO addressDTO) {
        // In a real application, you would get the current user's ID from the security context
        Long currentUserId = 1L; // Placeholder for demonstration

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + currentUserId));

        Address address = new Address();
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setZipCode(addressDTO.getZipCode());

        user.setAddress(address);
        userRepository.save(user);

        return addressDTO;
    }

    public AddressDTO updateAddress(Long id, @Valid AddressDTO addressDTO) {
        // In a real application, you would get the current user's ID from the security context
        Long currentUserId = 1L; // Placeholder for demonstration

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + currentUserId));

        if (user.getAddress() == null || !user.getAddress().getId().equals(id)) {
            throw new ResourceNotFoundException("Address not found with id: " + id);
        }

        Address address = user.getAddress();
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setZipCode(addressDTO.getZipCode());

        userRepository.save(user);

        return addressDTO;
    }

    public void deleteAddress(Long id) {
        // In a real application, you would get the current user's ID from the security context
        Long currentUserId = 1L; // Placeholder for demonstration

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + currentUserId));

        if (user.getAddress() == null || !user.getAddress().getId().equals(id)) {
            throw new ResourceNotFoundException("Address not found with id: " + id);
        }

        user.setAddress(null);
        userRepository.save(user);
    }
}

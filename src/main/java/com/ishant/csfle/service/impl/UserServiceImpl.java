package com.ishant.csfle.service.impl;

import com.ishant.csfle.dto.RegisterUserDTO;
import com.ishant.csfle.dto.UserLoginDTO;
import com.ishant.csfle.dto.UserLoginResponse;
import com.ishant.csfle.exception.custom.InvalidLoginCredsException;
import com.ishant.csfle.exception.custom.UserExistsException;
import com.ishant.csfle.exception.custom.UserNotFoundException;
import com.ishant.csfle.model.appDB.User;
import com.ishant.csfle.repository.appDB.UserRepository;
import com.ishant.csfle.repository.keyVault.DekVaultRepository;
import com.ishant.csfle.service.JWTService;
import com.ishant.csfle.service.UserService;
import com.ishant.csfle.util.RegexUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DekVaultRepository dekVaultRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public UserServiceImpl(UserRepository userRepository, DekVaultRepository dekVaultRepository, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.dekVaultRepository = dekVaultRepository;
    }

    @Override
    public void registerUser(RegisterUserDTO userRegistrationDetails) {
        try {
            String username = userRegistrationDetails.getUsername();
            String email = userRegistrationDetails.getEmail();
            String mobile = userRegistrationDetails.getMobile();
            String userExists = checkUserExists(username, email, mobile);

            if (userExists != null) {
                throw new UserExistsException(userExists);

            } else {
                saveNewUser(
                        userRegistrationDetails.getName(),
                        userRegistrationDetails.getEmail(),
                        userRegistrationDetails.getUsername(),
                        userRegistrationDetails.getPassword(),
                        userRegistrationDetails.getMobile()
                );
            }
        } catch (Exception e) {
            log.error("Error registering User: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public String checkUserExists(String username, String email, String mobile) {
        try {
            Optional<User> userByEmail = userRepository.findByEmail(email);
            Optional<User> userByUsername = userRepository.findByUsername(username);
            Optional<User> userByMobile = userRepository.findByMobile(mobile);

            if (userByEmail.isPresent()) {
                return "User of Email: " + email + " is already Registered";
            }

            if (userByUsername.isPresent()) {
                return "User of Username: " + username + " is already Registered";
            }

            if (userByMobile.isPresent()) {
                return "User of Mobile: " + mobile + " is already Registered";
            }

            return null;

        } catch (Exception e) {
            log.error("Error checking user already exists or not, cause: {}", e.getMessage());
            throw new RuntimeException("Error checking user already exists or not, cause: " + e.getMessage());
        }
    }

    private void saveNewUser(
            String name,
            String email,
            String username,
            String password,
            String mobile
    ) {
        try {
            User newUser = User.builder()
                    .name(name)
                    .email(email)
                    .username(username)
                    .password(password)
                    .pwdHash(passwordEncoder.encode(password))
                    .mobile(mobile)
                    .createdAt(Instant.now())
                    .build();
            userRepository.save(newUser);

        } catch (Exception e) {
            log.error("Error saving new User into DB: {}", e.getMessage());
            throw new RuntimeException("Error saving new User into DB: " + e.getMessage());
        }
    }

    @Override
    public UserLoginResponse loginUser(UserLoginDTO userLogin) {
        User user = findUserNameForLogin(userLogin.getUser());

        if (passwordEncoder.matches(userLogin.getPassword(), user.getPwdHash())) {
            user.setLastLogin(Instant.now());
            userRepository.save(user);

            return UserLoginResponse.builder()
                    .accessToken(jwtService.generateToken(user))
                    .username(user.getUsername())
                    .build();
        } else {
            throw new InvalidLoginCredsException("User Login or Password is wrong");
        }
    }


    private User findUserNameForLogin(String userString) {
        try {
            Optional<User> userOptional;

            if (RegexUtil.identify(userString).equalsIgnoreCase("mobile")) {
                userOptional = userRepository.findByMobile(userString);
                log.debug("Logged in by mobile");

            } else if (RegexUtil.identify(userString).equalsIgnoreCase("email")) {
                userOptional = userRepository.findByEmail(userString);
                log.debug("Logged in by email");

            } else {
                userOptional = userRepository.findByUsername(userString);
                log.debug("Logged in by username");

            }

            if (userOptional.isPresent()) {
                return userOptional.get();
            } else {
                throw new UserNotFoundException("No user found by " + userString);
            }

        } catch (Exception e) {
            log.error("Error finding user, cause: {}", e.getMessage());
            throw e;
        }

    }
}

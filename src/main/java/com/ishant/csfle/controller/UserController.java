package com.ishant.csfle.controller;

import com.ishant.csfle.dto.*;
import com.ishant.csfle.exception.custom.DekVaultNotFoundException;
import com.ishant.csfle.exception.custom.UserExistsException;
import com.ishant.csfle.exception.custom.UserNotFoundException;
import com.ishant.csfle.model.appDB.User;
import com.ishant.csfle.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Register User API, allows user to create a new User account based on Email, or mobile.
     * The app doesn't send a verification mail or otp, so mail or mobile can be imaginary.
     *
     * @return a register success or failed message
     */
    @PostMapping(value = "/registerUser")
    public ResponseEntity<ApiResponse<?>> registerUser(
            HttpServletRequest request,
            @RequestBody RegisterUserDTO userRegistrationDetails
    ) {
        ApiResponse<?> apiResponse = new ApiResponse<>();

        try {
            userService.registerUser(userRegistrationDetails);
            apiResponse.setMessage("User Registered");
            apiResponse.setHttpStatus(HttpStatus.CREATED);
            log.info("Register User API Successful '{}', Request Id: {}", request.getRequestURI(), apiResponse.getRequestId());

        } catch (UserExistsException e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setHttpStatus(HttpStatus.CONFLICT);
            log.error("Register User API Failed '{}', Request Id: {}, Cause: {}", request.getRequestURI(), apiResponse.getRequestId(), e.getMessage());

        } catch (Exception e) {
            apiResponse.setMessage("User Registration Failed");
            apiResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("Register User API Failed '{}', Request Id: {}, Cause: {}", request.getRequestURI(), apiResponse.getRequestId(), e.getMessage());

        }

        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }


    /**
     * The Login API expects username, email or mobile as user identifier key to find the user,
     * and the password of the registered user.
     *
     * @return An object containing accessToken and name of user
     */
    @PostMapping(value = "/loginUser")
    public ResponseEntity<ApiResponse<UserLoginResponse>> loginUser(
            HttpServletRequest request,
            @RequestBody UserLoginDTO userLogin
    ) {
        ApiResponse<UserLoginResponse> apiResponse = new ApiResponse<>();

        try {
            apiResponse.setData(userService.loginUser(userLogin));
            apiResponse.setMessage("User Logged In");
            apiResponse.setHttpStatus(HttpStatus.ACCEPTED);
            log.info("Login User API Successful '{}', Request Id: {}", request.getRequestURI(), apiResponse.getRequestId());

        } catch (UserNotFoundException e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            log.error("Login User API Failed '{}', Request Id: {}, Cause: {}", request.getRequestURI(), apiResponse.getRequestId(), e.getMessage());

        } catch (Exception e) {
            apiResponse.setMessage("User Login Failed");
            apiResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("Login User API Failed '{}', Request Id: {}, Cause: {}", request.getRequestURI(), apiResponse.getRequestId(), e.getMessage());

        }

        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }


    /**
     * Health Point to check the server authentication is working, requires accessToken
     * @return the details of authenticated user
     */
    @GetMapping(value = "/hp")
    public ResponseEntity<ApiResponse<User>> healthPoint() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Health point accessed by: " + user.getUsername());
        apiResponse.setData(user);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    /**
     * Add and update card details of currently of logged-in user;
     * @param cardDetails is the raw JSON request body containing card details to add.
     * @return success or failed response
     */
    @PatchMapping(value = "/updateCardDetails")
    public ResponseEntity<ApiResponse<?>> updateCard(
            HttpServletRequest request,
            @RequestBody CardDTO cardDetails
    ) {
        ApiResponse<?> apiResponse = new ApiResponse<>();

        try {
            userService.updateCardDetails(cardDetails);
            apiResponse.setMessage("Card Update Successful");
            apiResponse.setHttpStatus(HttpStatus.ACCEPTED);
            log.info("Card Update API Successful '{}', Request Id: {}", request.getRequestURI(), apiResponse.getRequestId());

        } catch (Exception e) {
            apiResponse.setMessage("Card Update Failed");
            apiResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("Card Update API Failed '{}', Request Id: {}, Cause: {}", request.getRequestURI(), apiResponse.getRequestId(), e.getMessage());
        }

        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }


    /**
     * Retrieves currently logged-in user details, as it is, not decryption
     * @return the same user details to user
     */
    @GetMapping(value = "/viewUser")
    public ResponseEntity<?> viewUser(
            HttpServletRequest request
    ) {
        ApiResponse<UserInfoDTO> apiResponse = new ApiResponse<>();
        try {
            apiResponse.setData(userService.fetchUserInfo());
            apiResponse.setMessage("Fetched user info successfully");
            apiResponse.setHttpStatus(HttpStatus.ACCEPTED);
            log.info("Card Update API Successful '{}', Request Id: {}", request.getRequestURI(), apiResponse.getRequestId());

        } catch (Exception e) {
            apiResponse.setMessage("Failed fetching user information");
            apiResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("View User API Failed '{}', Request Id: {}, Cause: {}", request.getRequestURI(), apiResponse.getRequestId(), e.getMessage());
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }


    /**
     * Retrieves currently logged-in user details, after decrypting the encrypted fields
     * @return the same user details to user
     */
    @GetMapping(value = "/viewUserDecrypted")
    public ResponseEntity<?> viewUserPlain(
            HttpServletRequest request
    ) {
        ApiResponse<UserInfoDTO> apiResponse = new ApiResponse<>();
        try {
            apiResponse.setData(userService.fetchUserInfoDecrypted());
            apiResponse.setMessage("Fetched user info successfully");
            apiResponse.setHttpStatus(HttpStatus.ACCEPTED);
            log.info("Card Update API Successful '{}', Request Id: {}", request.getRequestURI(), apiResponse.getRequestId());

        } catch (DekVaultNotFoundException e) {
            apiResponse.setMessage("Please refill your card details or contact team");
            apiResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("View User API Failed '{}', Request Id: {}, Cause: {}", request.getRequestURI(), apiResponse.getRequestId(), e.getMessage());

        } catch (Exception e) {
            apiResponse.setMessage("Failed fetching user information");
            apiResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("View User API Failed '{}', Request Id: {}, Cause: {}", request.getRequestURI(), apiResponse.getRequestId(), e.getMessage());
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }
}

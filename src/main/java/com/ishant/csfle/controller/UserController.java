package com.ishant.csfle.controller;

import com.ishant.csfle.dto.ApiResponse;
import com.ishant.csfle.dto.RegisterUserDTO;
import com.ishant.csfle.dto.UserLoginDTO;
import com.ishant.csfle.dto.UserLoginResponse;
import com.ishant.csfle.exception.custom.UserExistsException;
import com.ishant.csfle.exception.custom.UserNotFoundException;
import com.ishant.csfle.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            apiResponse.setHttpStatus(HttpStatus.OK);
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
            apiResponse.setHttpStatus(HttpStatus.OK);
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

//    @PutMapping(value = "/editUser")
//    public ResponseEntity<?> editUser() {
//
//    }
//
//    @GetMapping(value = "/viewUser")
//    public ResponseEntity<?> viewUser() {
//
//    }
//
//    @GetMapping(value = "/viewUserDecrypted")
//    public ResponseEntity<?> viewUserPlain() {
//
//    }
}

package com.ishant.csfle.controller;

import com.ishant.csfle.dto.ApiResponse;
import com.ishant.csfle.model.appDB.User;
import com.ishant.csfle.model.keyVault.DekVault;
import com.ishant.csfle.repository.appDB.UserRepository;
import com.ishant.csfle.repository.keyVault.DekVaultRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/test/v1")
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DekVaultRepository dekVaultRepository;



    @GetMapping(value = "/getAllKeys")
    public ResponseEntity<ApiResponse<List<DekVault>>> getAllDeks() {
        ApiResponse<List<DekVault>> apiResponse = new ApiResponse<>();

        try {
            apiResponse.setData(dekVaultRepository.findAll());
            apiResponse.setMessage("Got all Keys");
            apiResponse.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());

        } catch (Exception e) {
            log.error("Error getting all keys: {}", e.getMessage());
            apiResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            apiResponse.setMessage("Error getting all keys: " + e.getMessage());
            return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
        }
    }

    @GetMapping(value = "/getAllUsers")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        ApiResponse<List<User>> apiResponse = new ApiResponse<>();

        try {
            apiResponse.setData(userRepository.findAll());
            apiResponse.setMessage("Got all users");
            apiResponse.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());

        } catch (Exception e) {
            log.error("Error getting all users: {}", e.getMessage());
            apiResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            apiResponse.setMessage("Error getting all users: " + e.getMessage());
            return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
        }
    }
}

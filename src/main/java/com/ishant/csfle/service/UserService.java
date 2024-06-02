package com.ishant.csfle.service;

import com.ishant.csfle.dto.CardDTO;
import com.ishant.csfle.dto.RegisterUserDTO;
import com.ishant.csfle.dto.UserLoginDTO;
import com.ishant.csfle.dto.UserLoginResponse;

public interface UserService {

    void registerUser(RegisterUserDTO userRegistrationDetails);

    String checkUserExists(String username, String email, String mobile);

    UserLoginResponse loginUser(UserLoginDTO userLogin);

    void updateCardDetails(CardDTO cardDetails) throws Exception;
}

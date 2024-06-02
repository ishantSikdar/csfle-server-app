package com.ishant.csfle.service;

import com.ishant.csfle.dto.*;

public interface UserService {

    void registerUser(RegisterUserDTO userRegistrationDetails);

    String checkUserExists(String username, String email, String mobile);

    UserLoginResponse loginUser(UserLoginDTO userLogin);

    void updateCardDetails(CardDTO cardDetails) throws Exception;

    UserInfoDTO fetchUserInfo();

    UserInfoDTO fetchUserInfoDecrypted() throws Exception;
}

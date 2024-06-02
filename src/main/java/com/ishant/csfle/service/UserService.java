package com.ishant.csfle.service;

import com.ishant.csfle.dto.*;
import com.ishant.csfle.exception.custom.InvalidLoginCredsException;
import com.ishant.csfle.exception.custom.UserExistsException;
import com.ishant.csfle.exception.custom.UserNotFoundException;

public interface UserService {

    /**
     * Creates a new user using Email, Username and Mobile (Optional) if not registered
     * @param userRegistrationDetails contains the required details to create a new user
     * @throws UserExistsException is thrown when a user already exists by the given username, email or mobile
     */
    void registerUser(RegisterUserDTO userRegistrationDetails) throws UserExistsException;


    /**
     * Checks if the User exists by username, email or mobile, if the user does exist, it returns a message
     * @param username to search by which username
     * @param email to search by which email
     * @param mobile to search by which mobile
     * @return a string sentence message if user exists otherwise null
     */
    String checkUserExists(String username, String email, String mobile);


    /**
     * Verifies a registered user and response with a new JWT accessToken
     * @param userLogin contains username/email/mobile and a password
     * @return accessToken and username of the user
     * @throws InvalidLoginCredsException is thrown if the login credentials are wrong
     * @throws UserNotFoundException is thrown if the user is not registered
     */
    UserLoginResponse loginUser(UserLoginDTO userLogin) throws InvalidLoginCredsException, UserNotFoundException;


    /**
     * Add and update Card details of the user
     * @param cardDetails has the card number, cvv, and expiry date
     */
    void updateCardDetails(CardDTO cardDetails) throws Exception;


    /**
     * Fetches and send the user their details, as it is, without decryption
     * @return the user details (encrypted)
     */
    UserInfoDTO fetchUserInfo();


    /**
     * Fetches and send the user their details, after decrypting the card details
     * @return the user details (decrypted)
     */
    UserInfoDTO fetchUserInfoDecrypted() throws Exception;
}

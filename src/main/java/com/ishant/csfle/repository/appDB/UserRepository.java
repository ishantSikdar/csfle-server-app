package com.ishant.csfle.repository.appDB;

import com.ishant.csfle.model.appDB.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{ 'username' : ?0 }")
    Optional<User> findByUsername(String username);

    @Query("{ 'email' : ?0 }")
    Optional<User> findByEmail(String email);

    @Query("{ 'mobile' : ?0 }")
    Optional<User> findByMobile(String mobile);

    @Query("{ $or: [ {'username': ?0 }, {'email' : ?1 }, {'mobile' : ?2 } ] }")
    Optional<User> findByUsernameEmailMobile(String username, String email, String mobile);

}

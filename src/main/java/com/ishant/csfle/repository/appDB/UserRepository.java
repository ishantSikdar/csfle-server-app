package com.ishant.csfle.repository.appDB;

import com.ishant.csfle.model.appDB.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}

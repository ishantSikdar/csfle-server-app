package com.ishant.csfle.repository.keyVault;

import com.ishant.csfle.model.keyVault.DekVault;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DekVaultRepository extends MongoRepository<DekVault, String> {

    @Query("{ 'ref': ?0 }")
    Optional<DekVault> findByRef(String ref);
}

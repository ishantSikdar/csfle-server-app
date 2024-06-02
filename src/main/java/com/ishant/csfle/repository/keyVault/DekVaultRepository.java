package com.ishant.csfle.repository.keyVault;

import com.ishant.csfle.model.keyVault.DekVault;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DekVaultRepository extends MongoRepository<DekVault, String> {
}

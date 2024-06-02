package com.ishant.csfle.model.keyVault;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Builder
@Document(collection = "dekVault")
public class DekVault {
    @Id
    private String id;
    private String ref;
    private String dek;
    private Instant createdAt;
    private Instant updatedAt;
    private String updatedBy;
}

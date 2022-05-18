package com.quintet.apigateway.repositories;

import com.quintet.apigateway.model.NotificationKeys;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationkeyRepository extends MongoRepository<NotificationKeys, String> {
    void removeAllByUserMobileNumberAndRoleName(String number, String role, String token);
    List<NotificationKeys> findDistinctByUserMobileNumberAndRoleName(String number, String roleName);
}

package com.quintet.apigateway.repositories;

import com.quintet.apigateway.model.Role;
import org.apache.catalina.LifecycleState;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoleMongoRepository extends MongoRepository<Role, Integer> {
    Role findById(int id);
    List<Role> findAllBy();
    double countAllById();
    void deleteById(int id);
}

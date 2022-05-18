package com.quintet.apigateway.repositories;

import com.quintet.apigateway.model.Role;
import com.quintet.apigateway.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface UserMongoRepository extends MongoRepository<User, String> {
    User findByMobileNumberAndRolesContainingAndActiveIsTrue(String number, Role role);
    User findByMobileNumberAndRolesContainsAndActiveIsTrue(String number, Role role);
    User findByMobileNumberAndPasswordAndRolesContainingAndActiveIsTrue(String number,String password, Role role);
    //User findByMobileNumberAndPasswordAndActiveIsTrue(String number, String pass);
    boolean existsByMobileNumberAndRolesContains(String number, Role role);
    //@Query(value="{email : 'ashikmdshakilpranto@gmail.com'}", fields="{name : 1, _id : 0}")
    //List<User> findUser();
    User findByIdAndRolesContainingAndActiveIsTrue(String id, Role role);
    Page<User> findByIdIsNotAndRolesContainingAndRequestedIsFalseAndVendorRequestIsFalse(String admin,Role role,PageRequest pageRequest);
    Page<User> findAllByRolesContaining(String admin,Role role,PageRequest pageRequest);
    Page<User> findAllByRequestedIsTrueAndActiveIsFalse(PageRequest pageRequest);
    Page<User> findAllByVendorRequestIsTrueAndActiveIsFalse(PageRequest pageRequest);
    @Query(value= "{ '_id' : ?0 }")
    User findWholeUser(String id);
    void deleteById(String id);
    List<User> findAllByReferenceVendorId(String id);
    long countByRolesContaining(Role role);




}

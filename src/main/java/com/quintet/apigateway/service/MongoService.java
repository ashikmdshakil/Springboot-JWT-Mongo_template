package com.quintet.apigateway.service;

import com.quintet.apigateway.model.Role;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

@Service
@Scope(scopeName = "prototype")
public class MongoService {
    public static String generateId() {
        return new ObjectId().toString();
    }


    public static String generateOTP(){
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }

}

package com.quintet.apigateway.service;

import com.google.gson.Gson;
import com.quintet.apigateway.model.NotificationKeys;
import com.quintet.apigateway.model.Role;
import com.quintet.apigateway.model.User;
import com.quintet.apigateway.repositories.NotificationkeyRepository;
import com.quintet.apigateway.repositories.UserMongoRepository;
import com.quintet.apigateway.security.ApplicationUserDetails;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.ArrayList;

@Service
@Scope(scopeName = "prototype")
public class AuthenticationService {
    @Autowired
    private User user;
    @Autowired
    private Role role;
    @Autowired
    private NotificationKeys notificationKeys;
    @Autowired
    private UserMongoRepository userMongoRepository;
    @Autowired
    private NotificationkeyRepository notificationkeyRepository;
    @Autowired
    private JWTTokenUtils jwtTokenUtils;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private User realUser;
    @Autowired
    private Gson gson;


    public HttpServletResponse authenticateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = "";
        String userName = "";
        String password = "";
        String roleName = "";
        String notificationToken = "";
        String upd = request.getHeader("Authorization");
        String pair = new String(Base64.decodeBase64(upd.substring(6)));
        userName = pair.split(":")[0];
        password = pair.split(":")[1];
        roleName = request.getParameter("role");
        notificationToken = request.getParameter("token");
        if(roleName.equals("user")){
            role.setId(1);
            role.setName("user");
            var user = userMongoRepository.findByMobileNumberAndRolesContainsAndActiveIsTrue(userName, role);
            if(user != null && passwordEncoder.matches(password, user.getPassword())){
                String authToken = jwtTokenUtils.generateToken(new ApplicationUserDetails(user),"user");
                user.setJWTToken(authToken);
                token = authToken;
                response.setHeader("auth","authenticated");
                realUser = user;
            }
            else{
                token = "unauthenticated";
                response.setHeader("auth","unauthenticated");
            }
        }
        else if(roleName.equals("vendor")){
            role.setId(2);
            role.setName("vendor");
            var user = userMongoRepository.findByMobileNumberAndRolesContainsAndActiveIsTrue(userName, role);
            if(user != null && passwordEncoder.matches(password, user.getPassword())){
                String authToken = jwtTokenUtils.generateToken(new ApplicationUserDetails(user),"vendor");
                user.setJWTToken(authToken);
                token = authToken;
                response.setHeader("auth","authenticated");
                realUser = user;
            }
            else{
                token = "unauthenticated";
                response.setHeader("auth","unauthenticated");
            }
        }

        else if(roleName.equals("admin")){
            role.setId(3);
            role.setName("admin");
            var user = userMongoRepository.findByMobileNumberAndRolesContainsAndActiveIsTrue(userName, role);
            if(user != null && passwordEncoder.matches(password, user.getPassword())){
                String authToken = jwtTokenUtils.generateToken(new ApplicationUserDetails(user),"admin");
                user.setJWTToken(authToken);
                token = authToken;
                response.setHeader("auth","authenticated");
                realUser = user;
            }
            else{
                token = "unauthenticated";
                response.setHeader("auth","unauthenticated");
            }
        }

        else{
            token = "unauthenticated";
            response.setHeader("auth","unauthenticated");
        }

        if(realUser != null && response.getHeader("auth").equals("authenticated")){
            String userJsonString = this.gson.toJson(realUser);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            //response.setCharacterEncoding("UTF-8");
            out.print(userJsonString);
            out.flush();
        }
        else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        if (token != "unauthenticated" && role != null && notificationToken != "") {
            notificationKeys.setId(MongoService.generateId());
            notificationKeys.setNotificationKey(notificationToken);
            notificationKeys.setRoleName(roleName);
            notificationKeys.setUserMobileNumber(userName);
            notificationkeyRepository.save(notificationKeys);
        }
        return response;
    }


    public UserDetails getValidUserDetails(HttpServletRequest request){
        UserDetails userDetails = null;
        try {
            String token= request.getHeader("Authorization");
            String jwtToken = token.substring(7);
            String userName = jwtTokenUtils.getUsernameFromToken(jwtToken);
            String roleName = jwtTokenUtils.getRoleFromToken(jwtToken);
            if (roleName.equals("user")) {
                role.setId(1);
                role.setName("user");
                var user = userMongoRepository.findByMobileNumberAndRolesContainsAndActiveIsTrue(userName, role);
                userDetails = new ApplicationUserDetails(user);
            } else if (roleName.equals("vendor")) {
                role.setId(2);
                role.setName("vendor");
                var user = userMongoRepository.findByMobileNumberAndRolesContainsAndActiveIsTrue(userName, role);
                userDetails = new ApplicationUserDetails(user);
            }
            else if (roleName.equals("admin")) {
                role.setId(3);
                role.setName("admin");
                var user = userMongoRepository.findByMobileNumberAndRolesContainsAndActiveIsTrue(userName, role);
                userDetails = new ApplicationUserDetails(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDetails;
    }

    //To logout an user
    public void logoutUser(HttpSession httpSession, Principal principal, String role, String token) {
        SecurityContextHolder.clearContext();
        try {
            notificationkeyRepository.removeAllByUserMobileNumberAndRoleName(principal.getName(), role, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

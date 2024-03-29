package com.quintet.apigateway.security;

import com.quintet.apigateway.model.Role;
import com.quintet.apigateway.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ApplicationUserDetails implements UserDetails {
    private User user;
    private List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
    private String username;
    private String password;

    public ApplicationUserDetails(User user) {
        super();
        for (Role role : user.getRoles()) {
            this.authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        this.username = user.getMobileNumber();
        this.password = user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return this.authorities;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return this.password;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
}


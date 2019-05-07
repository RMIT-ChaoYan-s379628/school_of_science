
package com.rmit.gateway.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rmit.gateway.service.IUserService;
import com.rmit.main.library.gateway.enums.UserStatus;
import com.rmit.main.library.gateway.model.User;
import com.rmit.main.library.gateway.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository      userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        LOG.info("Authenticating {}", username);
        User user = userRepository.findOneByUserIdAndStatus(username, UserStatus.ACTIVE);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
        org.springframework.security.core.userdetails.User returnUser = new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantedAuthorities);
        return returnUser;
    }

    @Override
    public User findUserByUserId(String userId) {
        return userRepository.findOneByUserIdAndStatus(userId, UserStatus.ACTIVE);
    }

}

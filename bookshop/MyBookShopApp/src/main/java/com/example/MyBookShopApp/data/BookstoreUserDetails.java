package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.data.user.UserRoleEntity;
import com.example.MyBookShopApp.service.BookshpCartService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class BookstoreUserDetails implements UserDetails {

    private final UserEntity bookstoreUser;

    private final Logger logger = Logger.getLogger(BookshpCartService.class.getName());

    public BookstoreUserDetails(UserEntity bookstoreUser) {
        this.bookstoreUser = bookstoreUser;
    }

    public UserEntity getBookstoreUser() {
        return bookstoreUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
        simpleGrantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        List<UserRoleEntity> userRoleEntityList;
        try {
            userRoleEntityList = bookstoreUser.getUserRoleEntities();
        } catch (Exception e) {
            userRoleEntityList = new ArrayList<>();
            logger.info("ОШИБКА " + e.getMessage());
        }
        if (userRoleEntityList != null && userRoleEntityList.size() != 0) {
            for (UserRoleEntity userRole : userRoleEntityList) {
                simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(userRole.getUserRole()));
            }
        }
        return Collections.unmodifiableList(simpleGrantedAuthorityList);
    }

    @Override
    public String getPassword() {
        return bookstoreUser.getPassword();
    }

    @Override
    public String getUsername() {
        return bookstoreUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

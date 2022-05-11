package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.data.user.UserEntity;

public class PhoneNumberUserDetails extends BookstoreUserDetails {

    public PhoneNumberUserDetails(UserEntity bookstoreUser) {
        super(bookstoreUser);
    }

    @Override
    public String getUsername() {
        return getBookstoreUser().getPhone();
    }
}

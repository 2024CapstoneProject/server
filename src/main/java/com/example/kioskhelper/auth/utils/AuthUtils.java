package com.example.kioskhelper.auth.utils;

import com.example.kioskhelper.domain.dto.UserPrincipal;
import com.example.kioskhelper.domain.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {

    public static User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((UserPrincipal) authentication.getPrincipal()).toUser();
    }
}

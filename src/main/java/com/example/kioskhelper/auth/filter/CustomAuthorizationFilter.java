package com.example.kioskhelper.auth.filter;

import com.example.kioskhelper.domain.dto.UserPrincipal;
import com.example.kioskhelper.domain.entity.User;
import com.example.kioskhelper.repository.UserRepository;
import com.example.kioskhelper.service.EncryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {



    private final EncryptionService encryptionService;

    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String uid = request.getHeader("X-USER-ID");
        System.out.println("Authorization Header: " + uid); // 로그 추가
        if (uid != null) {
            String encryptedUid = encryptionService.encrypt(uid);
            // UserRepository를 통해 사용자 정보 조회
            User user = userRepository.findByUid(encryptedUid);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            UserPrincipal userPrincipal = new UserPrincipal(user.getUid(), user.getEmail(), user.getRole(), new ArrayList<>());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
            System.out.println("Authentication: " + authentication); // 로그 추가
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

}

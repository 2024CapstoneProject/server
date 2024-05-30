package com.example.kioskhelper.repository;

import com.example.kioskhelper.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUid(String name);

    User findByEmail(String email);
}

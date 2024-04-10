package com.example.kioskhelper.repository;

import com.example.kioskhelper.domain.entity.Protector;
import com.example.kioskhelper.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProtectorRepository  extends JpaRepository<Protector, Long> {
    @Query("SELECT p FROM Protector p JOIN FETCH p.guardian g JOIN FETCH p.ward w WHERE p.guardian = :guardian AND p.ward = :ward AND p.status = :status")
    Protector findByGuardianAndWardAndStatus(User guardian, User ward, Protector.Status status);
}

package com.example.kioskhelper.repository;

import com.example.kioskhelper.domain.entity.Chat;
import com.example.kioskhelper.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {


    //expired가 false인 것만 가져옴 , createAt 기준으로 내림차순 정렬
    @Query("select c from Chat c where c.user = :user and c.expired = false ")
    List<Chat> findByUser(User user);

    //expired가 false인 것만 가져옴 , createAt 기준으로 내림차순 정렬
    @Query("select c from Chat c where c.expired = false order by c.createdAt asc ,c.sessionId ")
    List<Chat> findNoExpiredChat();


    //expired가 false인 것만 가져옴 , createAt 기준으로 내림차순 정렬
    @Query("select c from Chat c where c.sessionId=:sessionId and c.expired = false")
    List<Chat> findBySessionId(String sessionId);

    @Query("select c from Chat c where c.expired = false")
    List<Chat> findAll();
}

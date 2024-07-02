package com.example.kioskhelper.repository;

import com.example.kioskhelper.domain.entity.Chat;
import com.example.kioskhelper.domain.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    //가장 최근 대화 하나의 세션id 가져오기
    @Query("SELECT c.sessionId FROM Chat c WHERE c.user = :user AND c.expired = false ORDER BY c.createdAt DESC ")
    List<String> findRecentChat(User user);


    @Query("SELECT c FROM Chat c WHERE c.user = :user AND c.expired = false ORDER BY c.createdAt DESC")
    List<Chat> findRecentChatList(User user);

    @Query("SELECT c FROM Chat c WHERE c.user = :user AND c.expired = false ORDER BY c.createdAt DESC")
    List<Chat> findRecentChatsByUserIdAndExpiredFalse(User user, Pageable pageable);


}

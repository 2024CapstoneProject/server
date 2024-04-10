package com.example.kioskhelper.controller;

import com.example.kioskhelper.domain.entity.User;
import com.example.kioskhelper.domain.entity.UserLocation;
import com.example.kioskhelper.service.UserLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/locations")
public class UserLocationController {
    private final UserLocationService service;



    @PostMapping
    public ResponseEntity<Void> saveUserLocation(@RequestBody UserLocation location) {
        service.saveUserLocation(location);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserLocation> getUserLatestLocation(@PathVariable Long userId) {
        User user = new User();
        UserLocation location = service.getUserLatestLocation(user,userId);
        if (location != null) {
            return ResponseEntity.ok(location);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
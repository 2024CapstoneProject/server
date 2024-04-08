package com.example.kioskhelper.map.controller;

import com.example.kioskhelper.map.domain.dto.UserPositionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/map")
public class MapController {

    @PostMapping(value="/position")
    public void saveUserPosition(@ModelAttribute UserPositionRequest userPosition){


    }

    @PutMapping(value="/update")
    public void updateUserPosition(@ModelAttribute UserPositionRequest userPositionRequest){

    }

    @GetMapping(value="/get")
    public void getUserPosition(@ModelAttribute String example){

    }
}



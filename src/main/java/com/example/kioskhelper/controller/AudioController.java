package com.example.kioskhelper.controller;


import com.example.kioskhelper.domain.dto.TranscriptionRequest;
import com.example.kioskhelper.domain.dto.WhisperTranscriptionResponse;
import com.example.kioskhelper.service.OpenAIClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class AudioController {

private final OpenAIClientService openAIClientService;


    @PostMapping(value = "/transcription/test", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public WhisperTranscriptionResponse createTranscription(@ModelAttribute TranscriptionRequest transcriptionRequest){
        return openAIClientService.createTranscription(transcriptionRequest);
    }





}
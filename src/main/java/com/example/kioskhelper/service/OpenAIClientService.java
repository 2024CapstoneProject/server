package com.example.kioskhelper.service;

import com.example.kioskhelper.config.OpenAIClient;
import com.example.kioskhelper.config.OpenAIClientConfig;
import com.example.kioskhelper.domain.dto.TranscriptionRequest;
import com.example.kioskhelper.domain.dto.WhisperTranscriptionRequest;
import com.example.kioskhelper.domain.dto.WhisperTranscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OpenAIClientService {

    private final OpenAIClient openAIClient;
    private final OpenAIClientConfig openAIClientConfig;

    public WhisperTranscriptionResponse createTranscription(TranscriptionRequest transcriptionRequest){
        WhisperTranscriptionRequest whisperTranscriptionRequest = WhisperTranscriptionRequest.builder()
                .model(openAIClientConfig.getAudioModel())
                .file(transcriptionRequest.getFile())
                .build();
        return openAIClient.createTranscription(whisperTranscriptionRequest);
    }

}
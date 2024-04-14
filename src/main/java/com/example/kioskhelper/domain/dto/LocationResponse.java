package com.example.kioskhelper.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse {
    /* val userId: Long,
    val latitude: Double,
    val longitude: Double*/
    private Long userId;
    private Double latitude;
    private Double longitude;
}

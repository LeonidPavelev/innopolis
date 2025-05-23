package ru.innopolis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EarthQuakeResponse {

    private Long id;
    private String title;
    private LocalDateTime time;
    private String place;
    private Double mag;
}

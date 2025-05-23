package ru.innopolis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.innopolis.dto.EarthQuakeCreateRequest;
import ru.innopolis.dto.EarthQuakeResponse;
import ru.innopolis.entity.EarthQuakeEntity;
import ru.innopolis.repository.EarthQuakeRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EarthQuakeService {

    private final EarthQuakeRepository earthQuakeRepository;


    public void addEarthQuakes(EarthQuakeCreateRequest request){
        List<EarthQuakeEntity> quakeEntities = request.getFeatures().stream()
                .map(e -> EarthQuakeEntity.builder()
                        .title(e.getProperties().getTitle())
                        .mag(e.getProperties().getMag())
                        .place(e.getProperties().getPlace())
                        .time(LocalDateTime.ofInstant(Instant.ofEpochMilli(e.getProperties().getTime()), ZoneOffset.UTC))
                        .build()).toList();
        earthQuakeRepository.saveAll(quakeEntities);
    }


    public List<EarthQuakeResponse> getEarthQuakes(Double mag){
        return earthQuakeRepository.findByMagAfter(mag).stream()
                .map(e -> EarthQuakeResponse.builder()
                        .id(e.getId())
                        .title(e.getTitle())
                        .place(e.getPlace())
                        .time(e.getTime())
                        .mag(e.getMag())
                        .build())
                .toList();
    }

    public List<EarthQuakeResponse> getEarthQuakesByPeriod(LocalDateTime start, LocalDateTime end){
        return earthQuakeRepository.findByTimeBetween(start, end).stream()
                .map(e -> EarthQuakeResponse.builder()
                        .id(e.getId())
                        .title(e.getTitle())
                        .place(e.getPlace())
                        .time(e.getTime())
                        .mag(e.getMag())
                        .build())
                .toList();
    }
}

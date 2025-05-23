package ru.innopolis.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.innopolis.dto.EarthQuakeCreateRequest;
import ru.innopolis.dto.EarthQuakeResponse;
import ru.innopolis.entity.EarthQuakeEntity;
import ru.innopolis.repository.EarthQuakeRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EarthQuakeServiceTest {

    @Mock
    private EarthQuakeRepository earthQuakeRepository;

    @InjectMocks
    private EarthQuakeService earthQuakeService;

    @Test
    void addEarthQuakes() {
        EarthQuakeCreateRequest request = new EarthQuakeCreateRequest();
        EarthQuakeCreateRequest.Feature feature = new EarthQuakeCreateRequest.Feature();
        EarthQuakeCreateRequest.Feature.Properties properties = new EarthQuakeCreateRequest.Feature.Properties();
        properties.setTime(1747998457360L);
        properties.setMag(2.5);
        properties.setPlace("Place");
        properties.setTitle("Title");
        feature.setProperties(properties);
        request.setFeatures(List.of(feature));

        earthQuakeService.addEarthQuakes(request);

        verify(earthQuakeRepository, times(1)).saveAll(anyList());
    }

    @Test
    void getEarthQuakes() {
        Double mag = 2.0;
        EarthQuakeEntity entity = EarthQuakeEntity.builder()
                .mag(2.5)
                .time(LocalDateTime.now())
                .place("Place")
                .title("Title")
                .build();
        when(earthQuakeRepository.findByMagAfter(mag)).thenReturn(List.of(entity));

        List<EarthQuakeResponse> response = earthQuakeService.getEarthQuakes(mag);

        assertEquals(1, response.size());
        assertEquals(2.5, response.get(0).getMag());
    }

    @Test
    void getEarthQuakesByPeriod() {
        LocalDateTime start = LocalDateTime.parse("2025-05-23T05:00:00");
        LocalDateTime end = LocalDateTime.parse("2025-05-23T07:00:00");
        EarthQuakeEntity entity = EarthQuakeEntity.builder()
                .time(start.plusMinutes(30))
                .build();
        when(earthQuakeRepository.findByTimeBetween(start, end)).thenReturn(List.of(entity));

        List<EarthQuakeResponse> response =
                earthQuakeService.getEarthQuakesByPeriod(start, end);

        assertEquals(1, response.size());
        assertEquals(start.plusMinutes(30), response.get(0).getTime());
    }
}
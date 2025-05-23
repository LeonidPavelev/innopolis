package ru.innopolis.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.innopolis.dto.EarthQuakeCreateRequest;
import ru.innopolis.dto.EarthQuakeResponse;
import ru.innopolis.service.EarthQuakeService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EarthQuakeControllerTest {

    @Mock
    private EarthQuakeService earthQuakeService;

    @InjectMocks
    private EarthQuakeController earthQuakeController;

    @Test
    void addEarthQuakes() {
        EarthQuakeCreateRequest request = new EarthQuakeCreateRequest();
        ResponseEntity<String> response = earthQuakeController.addEarthQuakes(request);

        assertEquals("Значения успешно сохранены!", response.getBody());
        verify(earthQuakeService, times(1)).addEarthQuakes(request);
    }

    @Test
    void getEarthQuakes() {
        Double mag = 2.0;
        List<EarthQuakeResponse> expectedResponse = List.of(
                EarthQuakeResponse.builder().mag(2.5).build()
        );
        when(earthQuakeService.getEarthQuakes(mag)).thenReturn(expectedResponse);

        ResponseEntity<List<EarthQuakeResponse>> response =
                earthQuakeController.getEarthQuakes(mag);

        assertEquals(expectedResponse, response.getBody());
        verify(earthQuakeService, times(1)).getEarthQuakes(mag);
    }

    @Test
    void getEarthQuakesByPeriod() {
        LocalDateTime start = LocalDateTime.parse("2025-05-23T05:00:00");
        LocalDateTime end = LocalDateTime.parse("2025-05-23T07:00:00");
        List<EarthQuakeResponse> expectedResponse = List.of(
                EarthQuakeResponse.builder().time(start.plusMinutes(30)).build()
        );
        when(earthQuakeService.getEarthQuakesByPeriod(start, end)).thenReturn(expectedResponse);

        ResponseEntity<List<EarthQuakeResponse>> response =
                earthQuakeController.getEarthQuakesByPeriod(start, end);

        assertEquals(expectedResponse, response.getBody());
        verify(earthQuakeService, times(1)).getEarthQuakesByPeriod(start, end);
    }
}
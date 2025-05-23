package ru.innopolis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.dto.EarthQuakeCreateRequest;
import ru.innopolis.dto.EarthQuakeResponse;
import ru.innopolis.service.EarthQuakeService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/earthquake")
public class EarthQuakeController {

    private final EarthQuakeService earthQuakeService;

    @PostMapping
    public ResponseEntity<String> addEarthQuakes(@RequestBody EarthQuakeCreateRequest request) {
        log.info("addEarthQuakes");
        earthQuakeService.addEarthQuakes(request);
        log.info("success addEarthQuakes");
        return ResponseEntity.ok("Значения успешно сохранены!");
    }

    @GetMapping("/{mag}")
    public ResponseEntity<List<EarthQuakeResponse>> getEarthQuakes(@PathVariable Double mag) {
        log.info("getEarthQuakes higher than: {}", mag);
        var response = earthQuakeService.getEarthQuakes(mag);
        log.info("getEarthQuakes success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/period")
    public ResponseEntity<List<EarthQuakeResponse>> getEarthQuakesByPeriod(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        log.info("getEarthQuakes between: {} and {}", start, end);
        var response = earthQuakeService.getEarthQuakesByPeriod(start, end);
        log.info("getEarthQuakesByPeriod success");
        return ResponseEntity.ok(response);
    }
}

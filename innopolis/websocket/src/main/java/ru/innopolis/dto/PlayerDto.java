package ru.innopolis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PlayerDto {
    private String name;
    private List<String> guesses;
    private boolean winner;
}

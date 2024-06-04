package com.codecool.men.controller.dto.note;

import java.time.LocalDate;

public record NewNoteDTO(
        String title,
        String text,
        long userId,
        LocalDate date
) {
}

package com.codecool.men.controller.dto.note;

public record NoteDTO (
        long noteId,
        String title,
        String text,
        boolean isFavorite
){}

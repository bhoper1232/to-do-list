package com.bhoper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record TaskUpdateRequest(Long id, String title, String description, Boolean completed,
                                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
                                LocalDate dueDate) {
}

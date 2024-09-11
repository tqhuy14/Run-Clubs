package com.rungroup.web.dto;

import com.rungroup.web.models.Event;
import com.rungroup.web.models.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ClubDto {
    private Long id;
    @NotEmpty(message = "Club title should not be empty")
    private String title;
    @NotEmpty(message = "Club photo should not be empty")
    @Size(max = 255, message = "Club photo URL must not exceed 255 characters")
    private String photoUrl;
    @NotEmpty(message = "Club content should not be empty")
    private String content;
    private UserEntity createdBy;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private List<EventDto> events;
}

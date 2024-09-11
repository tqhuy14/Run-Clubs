package com.rungroup.web.dto;



import com.rungroup.web.models.Club;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private Long id;
    @NotEmpty(message = "Event name should not be empty")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "Start time should not be null")
    @FutureOrPresent(message = "Start time should be in the future or present")
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "End time should not be null")
    @FutureOrPresent(message = "End time should be in the future or present")
    private LocalDateTime endTime;

    @NotEmpty(message = "Event type should not be empty")
    private String type;
    @NotEmpty(message = "Event photo should not be empty")
    @Size(max = 255, message = "Event photo URL must not exceed 255 characters")
    private String photoUrl;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Club club;
}

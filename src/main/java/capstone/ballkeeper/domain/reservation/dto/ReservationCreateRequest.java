package capstone.ballkeeper.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservationCreateRequest(
        @NotNull Long memberId,
        @NotNull Long itemId,
        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime startTime,
        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime endTime
) {}

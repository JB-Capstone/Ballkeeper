package capstone.ballkeeper.domain.reservation.dto;

import capstone.ballkeeper.domain.reservation.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationResponse(
        Long id,
        Long memberId,
        List<Long> itemIds,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime startTime,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime endTime,
        String status,
        String usageStatus,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime pickupTime,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime returnTime
) {
    public static ReservationResponse from(Reservation r) {
        List<Long> itemIds = r.getReservationItems()
                .stream()
                .map(ri -> ri.getItem().getId())
                .toList();
        return new ReservationResponse(
                r.getId(),
                r.getMember().getId(),
                itemIds,
                r.getStartTime(),
                r.getEndTime(),
                r.getStatus().name(),
                r.getUsageStatus().name(),
                r.getPickupTime(),
                r.getReturnTime()
        );
    }
}

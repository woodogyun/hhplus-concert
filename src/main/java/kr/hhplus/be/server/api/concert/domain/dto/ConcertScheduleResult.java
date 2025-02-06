package kr.hhplus.be.server.api.concert.domain.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ConcertScheduleResult {
    private long id; // scheduleId
    private LocalDateTime performanceDateAt; // performanceDateAt

    public ConcertScheduleResult(long id, LocalDateTime performanceDateAt) {
        this.id = id;
        this.performanceDateAt = performanceDateAt;
    }

    public static ConcertScheduleResult fromEntity(long id, LocalDateTime performanceDateAt) {
        return new ConcertScheduleResult(id, performanceDateAt);
    }

}
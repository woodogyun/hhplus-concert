package kr.hhplus.be.server.concert.domain.dto;

import kr.hhplus.be.server.common.SeatState;
import kr.hhplus.be.server.concert.domain.entity.Seat;
import lombok.Getter;

@Getter
public class SeatResult {
    private long id; // 좌석 ID
    private long price; // 금액
    private SeatState state; // 좌석 상태

    public SeatResult(long id, SeatState state, long price) {
        this.id = id;
        this.state = state;
        this.price = price;
    }

    // 정적 팩토리 메소드
    public static SeatResult fromEntity(Seat seat) {
        return new SeatResult(seat.getId(), seat.getState(), seat.getPrice());
    }

}

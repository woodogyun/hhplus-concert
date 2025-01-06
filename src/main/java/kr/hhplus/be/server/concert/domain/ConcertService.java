package kr.hhplus.be.server.concert.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConcertService {

    private final ConcertRepository concertRepository;

    //1. concertId 를 받아 예약 가능한 날짜 조회

    //2. scheduleId를 받아 좌석 정보 조회

    //3. 좌석 선택 시 임시 배정으로 상태 변경 및 만료기간 설정

}

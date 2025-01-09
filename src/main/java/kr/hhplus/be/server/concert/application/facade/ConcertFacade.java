package kr.hhplus.be.server.concert.application.facade;

import kr.hhplus.be.server.concert.domain.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertFacade {

    private final ConcertService concertService;

}

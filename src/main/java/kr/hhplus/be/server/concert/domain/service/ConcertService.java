package kr.hhplus.be.server.concert.domain.service;

import kr.hhplus.be.server.concert.infra.ConcertRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConcertService {

    private final ConcertRepositoryImpl concertRepository;

}

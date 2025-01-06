package kr.hhplus.be.server.concert.infra;

import kr.hhplus.be.server.concert.domain.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {
    private final ConcertRepository repository;
}

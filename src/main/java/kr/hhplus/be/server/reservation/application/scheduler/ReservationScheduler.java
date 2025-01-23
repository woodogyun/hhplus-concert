package kr.hhplus.be.server.reservation.application.scheduler;

import kr.hhplus.be.server.reservation.application.facade.ReservationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class ReservationScheduler {

    final private ReservationFacade reservationFacade;

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void checkExpiredReservations() {
        int processedCount = reservationFacade.processExpiredReservations();
        log.info("Expired reservations processed: {}", processedCount);
    }

}
package kr.hhplus.be.server.payment.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    /**
     * 포인트 충전
     */
    public void charge(int amount) {}

    /**
     * 포인트 조회
     */
    public void getPoint(long userId) {}

    /**
     * 구매
     */
    public void pay(long userId, long amount) {}

}

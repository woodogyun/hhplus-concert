package kr.hhplus.be.server.api.payment.application.dto;

public record PaymentRequest (
        Long reservationId,
        Long amount,
        Long userId
)
{}

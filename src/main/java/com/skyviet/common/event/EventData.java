package com.skyviet.common.event;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

public final class EventData {
    private EventData() {}

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class BookingCreated {
        private String bookingId;
        private String pnr;
        private String contactEmail;
        private List<SegmentInfo> segments;
        private int passengerCount;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class SegmentInfo {
        private Long flightId;
        private String fareClassCode;
        private int passengerCount;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class SeatsHeld {
        private String bookingId;
        private Long flightId;
        private List<String> seatNumbers;
        private BigDecimal totalAmount;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class SeatsHoldFailed {
        private String bookingId;
        private Long flightId;
        private String reason;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class PaymentSuccess {
        private String paymentId;
        private String bookingId;
        private BigDecimal amount;
        private String method;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class PaymentFailed {
        private String paymentId;
        private String bookingId;
        private String reason;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class BookingConfirmed {
        private String bookingId;
        private String pnr;
        private Long userId;
        private BigDecimal totalAmount;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class BookingCancelled {
        private String bookingId;
        private String pnr;
        private List<Long> flightIds;
    }
}

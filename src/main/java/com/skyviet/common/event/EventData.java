package com.skyviet.common.event;

import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public final class EventData {
    private EventData() {}

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class BookingConfirmedNotification {
        private String pnr;
        private String contactEmail;
        private BigDecimal totalAmount;
        private String currency;
        private List<SegmentInfo> segments;
        private List<PassengerInfo> passengers;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class BookingCancelledNotification {
        private String pnr;
        private String contactEmail;
        private String reason;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class PaymentSuccessNotification {
        private String pnr;
        private String contactEmail;
        private BigDecimal amount;
        private String method;
        private String transactionRef;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class PaymentFailedNotification {
        private String pnr;
        private String contactEmail;
        private String reason;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class FlightDelayedNotification {
        private Long flightId;
        private String flightNumber;
        private OffsetDateTime newDepartureTime;
        private List<String> affectedEmails;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class FlightCancelledNotification {
        private Long flightId;
        private String flightNumber;
        private String reason;
        private List<String> affectedEmails;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class CheckinReminderNotification {
        private String pnr;
        private String contactEmail;
        private String flightNumber;
        private OffsetDateTime departureTime;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class SegmentInfo {
        private String flightNumber;
        private String departureAirport;
        private String arrivalAirport;
        private OffsetDateTime departureTime;
        private OffsetDateTime arrivalTime;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class PassengerInfo {
        private String firstName;
        private String lastName;
        private String passengerType;
    }
}

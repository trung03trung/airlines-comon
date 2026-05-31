package com.skyviet.common.event;

public final class Topics {
    private Topics() {}

    public static final String BOOKING_CREATED = "booking.created";
    public static final String BOOKING_CONFIRMED = "booking.confirmed";
    public static final String BOOKING_CANCELLED = "booking.cancelled";
    public static final String SEATS_HELD = "seats.held";
    public static final String SEATS_HOLD_FAILED = "seats.hold_failed";
    public static final String PAYMENT_SUCCESS = "payment.success";
    public static final String PAYMENT_FAILED = "payment.failed";
}

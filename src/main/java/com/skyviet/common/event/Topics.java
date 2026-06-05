package com.skyviet.common.event;

public final class Topics {
    private Topics() {}

    public static final String NOTIFICATION_BOOKING_CONFIRMED = "notification.booking_confirmed";
    public static final String NOTIFICATION_BOOKING_CANCELLED = "notification.booking_cancelled";
    public static final String NOTIFICATION_PAYMENT_SUCCESS = "notification.payment_success";
    public static final String NOTIFICATION_PAYMENT_FAILED = "notification.payment_failed";
    public static final String NOTIFICATION_FLIGHT_DELAYED = "notification.flight_delayed";
    public static final String NOTIFICATION_FLIGHT_CANCELLED = "notification.flight_cancelled";
    public static final String NOTIFICATION_CHECKIN_REMINDER = "notification.checkin_reminder";
}

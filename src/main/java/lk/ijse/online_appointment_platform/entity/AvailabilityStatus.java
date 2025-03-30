package lk.ijse.online_appointment_platform.entity;

public enum AvailabilityStatus {
    PENDING,    // When an appointment is booked but not yet accepted
    BOOKING,    // When the appointment is officially booked
    CANCELLED,  // When an appointment is cancelled
    COMPLETED;  // When the appointment is completed
}
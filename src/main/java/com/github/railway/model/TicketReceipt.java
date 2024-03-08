package com.github.railway.model;

import com.github.railway.entity.Seat;
import com.github.railway.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Ankit Kashyap on 08-03-2024
 */
@Getter
@Setter
@ToString
public class TicketReceipt extends TicketPurchaseRequest {
    private String distance;
    private Double pricePaid;
    private LocalDateTime ticketCreationDate;
    private String seatNumber;
    private String section;

    public TicketReceipt(@NonNull String from, @NonNull String to, @NonNull User user, @NonNull String trainNumber, @NonNull String bookingDate) {
        super(from, to, user, trainNumber, bookingDate);
    }

    public TicketReceipt() {
        super();
    }
}

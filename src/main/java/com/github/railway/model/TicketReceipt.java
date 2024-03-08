package com.github.railway.model;

import com.github.railway.entity.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

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
    private Long ticketId;

    public TicketReceipt(@NonNull String from, @NonNull String to, @NonNull User user, @NonNull String trainNumber, @NonNull String bookingDate) {
        super(from, to, user, trainNumber, bookingDate);
    }

    public TicketReceipt() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TicketReceipt that = (TicketReceipt) o;

        if (!Objects.equals(distance, that.distance)) return false;
        if (!Objects.equals(pricePaid, that.pricePaid)) return false;
        if (!Objects.equals(ticketCreationDate, that.ticketCreationDate))
            return false;
        if (!Objects.equals(seatNumber, that.seatNumber)) return false;
        if (!Objects.equals(section, that.section)) return false;
        return Objects.equals(ticketId, that.ticketId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        result = 31 * result + (pricePaid != null ? pricePaid.hashCode() : 0);
        result = 31 * result + (ticketCreationDate != null ? ticketCreationDate.hashCode() : 0);
        result = 31 * result + (seatNumber != null ? seatNumber.hashCode() : 0);
        result = 31 * result + (section != null ? section.hashCode() : 0);
        result = 31 * result + (ticketId != null ? ticketId.hashCode() : 0);
        return result;
    }
}

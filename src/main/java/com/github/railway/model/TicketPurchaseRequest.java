package com.github.railway.model;

import com.github.railway.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

/**
 * @author Ankit Kashyap on 08-03-2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketPurchaseRequest {
    @NonNull
    private String from;
    @NonNull
    private String to;
    @NonNull
    private User user;
    @NonNull
    private  String trainNumber;
    @NonNull
    private String bookingDate;
}

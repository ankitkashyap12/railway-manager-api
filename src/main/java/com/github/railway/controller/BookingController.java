package com.github.railway.controller;


import com.github.railway.entity.Ticket;
import com.github.railway.entity.User;
import com.github.railway.exception.BookingException;
import com.github.railway.model.TicketPurchaseRequest;
import com.github.railway.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ankit Kashyap on 08-03-2024
 */
@RestController
public class BookingController {
    TicketService ticketService;
    @Autowired
    public BookingController(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    @PostMapping ("/book")
    public ResponseEntity<Ticket> bookTicket(@RequestBody  TicketPurchaseRequest ticketPurchaseRequest) throws BookingException {

        return  ResponseEntity.ok(ticketService.purchaseTicket(ticketPurchaseRequest));
    }

    @GetMapping("/viewBookings")
    public  ResponseEntity<List<Ticket>> viewBookings(@RequestBody User user){
        return ResponseEntity.ok(ticketService.viewBookings(user));
    }

    @GetMapping("/viewBookingsBySection/section/{section}")
    public  ResponseEntity<List<Ticket>> viewBookingsBySection(@PathVariable String section){
        return ResponseEntity.ok(ticketService.viewBookingsBySection(section));
    }

    @DeleteMapping("/cancelBooking/{ticketId}")
    public ResponseEntity<Boolean> cancelTicket(@PathVariable Long ticketId){
        return  ResponseEntity.ok(ticketService.cancelTicket(ticketId));
    }
}

package com.github.railway.service;

import com.github.railway.dao.TicketRepository;
import com.github.railway.entity.Ticket;
import com.github.railway.entity.User;
import com.github.railway.model.TicketPurchaseRequest;
import com.github.railway.model.TicketReceipt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Ankit Kashyap on 08-03-2024
 */
@Service
@Slf4j
public class TicketService {
    private final TicketRepository ticketRepository;

    private DistanceCalculator distanceCalculator;
    private FareCalculator fareCalculator;

    private  SeatAllocationService seatAllocationService;

    public TicketService(DistanceCalculator distanceCalculator, FareCalculator fareCalculator, SeatAllocationService seatAllocationService,
                         TicketRepository ticketRepository) {
        this.distanceCalculator = distanceCalculator;
        this.fareCalculator = fareCalculator;
        this.seatAllocationService = seatAllocationService;
        this.ticketRepository = ticketRepository;
    }

    public TicketReceipt purchaseTicket(TicketPurchaseRequest ticketPurchaseRequest){
        log.info("Received request to purchase ticket {}", ticketPurchaseRequest);
        Double distance = distanceCalculator.getDistance(ticketPurchaseRequest.getFrom(), ticketPurchaseRequest.getTo());
        Double price = fareCalculator.calculateFare(distance);
        TicketReceipt  ticketReceipt = new TicketReceipt();
        Optional<Ticket> bookedTicket = seatAllocationService.allocateSeat(ticketPurchaseRequest);
        if(bookedTicket.isPresent()) {
            Ticket ticket = bookedTicket.get();
            ticketReceipt.setPricePaid(price);
            ticketReceipt.setTrainNumber(ticketPurchaseRequest.getTrainNumber());
            ticketReceipt.setDistance(String.valueOf(distance)+" miles");
            ticketReceipt.setTicketCreationDate(LocalDateTime.now());
            ticketReceipt.setTo(ticketPurchaseRequest.getTo());
            ticketReceipt.setFrom(ticketPurchaseRequest.getFrom());
            ticketReceipt.setBookingDate(LocalDate.now().toString());
            ticketReceipt.setTicketCreationDate(LocalDateTime.now());
            ticketReceipt.setUser(ticketPurchaseRequest.getUser());
            ticketReceipt.setSection(ticket.getSeat().getSection());
            ticketReceipt.setSeatNumber(ticket.getSeat().getSeatNumber());
        }
        return ticketReceipt;
    }


    public List<Ticket> viewBookings(User user){
            return ticketRepository.findAllByUserUserId(user.getUserId());
    }

    public List<Ticket> viewBookingsBySection(String section){
        List<Ticket> bookings = ticketRepository.findAll();
        return bookings.stream().filter(booking -> booking.getSeat().getSection().equals(section)).collect(Collectors.toList());
    }
}

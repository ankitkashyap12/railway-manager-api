package com.github.railway.service;

import com.github.railway.dao.TicketRepository;
import com.github.railway.entity.Ticket;
import com.github.railway.entity.User;
import com.github.railway.exception.BookingException;
import com.github.railway.model.TicketPurchaseRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    private final DistanceCalculator distanceCalculator;
    private final FareCalculator fareCalculator;

    private final SeatAllocationService seatAllocationService;

    public TicketService(DistanceCalculator distanceCalculator, FareCalculator fareCalculator, SeatAllocationService seatAllocationService,
                         TicketRepository ticketRepository) {
        this.distanceCalculator = distanceCalculator;
        this.fareCalculator = fareCalculator;
        this.seatAllocationService = seatAllocationService;
        this.ticketRepository = ticketRepository;
    }

    public Ticket purchaseTicket(TicketPurchaseRequest ticketPurchaseRequest) throws BookingException {
        log.info("Received request to purchase ticket {}", ticketPurchaseRequest);
        Double distance = distanceCalculator.getDistance(ticketPurchaseRequest.getFrom(), ticketPurchaseRequest.getTo());
        Double price = fareCalculator.calculateFare(distance);
        Optional<Ticket> bookedTicket = seatAllocationService.allocateSeat(ticketPurchaseRequest,distance,price);
        if(bookedTicket.isEmpty()) {
            throw new BookingException("An unknown error occurred while booking,please retry later","BK001");
        }
        return bookedTicket.get();
    }


    public List<Ticket> viewBookings(User user){
            return ticketRepository.findAllByUserUserId(user.getUserId());
    }

    public List<Ticket> viewBookingsBySection(String section){
        List<Ticket> bookings = ticketRepository.findAll();
        return bookings.stream().filter(booking -> booking.getSeat().getSection().equals(section)).collect(Collectors.toList());
    }

    public Boolean cancelTicket(Long ticketId) {
        ticketRepository.deleteById(ticketId);
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        return ticketOptional.isPresent();

    }
}

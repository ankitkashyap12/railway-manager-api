package com.github.railway.service;


import com.github.railway.dao.TicketRepository;
import com.github.railway.dao.TrainRepository;
import com.github.railway.entity.Seat;
import com.github.railway.entity.Ticket;
import com.github.railway.entity.Train;
import com.github.railway.exception.BookingException;
import com.github.railway.model.TicketPurchaseRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
public class SeatAllocationService {

    private final TrainRepository trainRepository;
    private final TicketRepository ticketRepository;


    public SeatAllocationService(TrainRepository trainRepository, TicketRepository ticketRepository) {
        this.trainRepository = trainRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public Optional<Ticket> allocateSeat(TicketPurchaseRequest ticketPurchaseRequest, Double distance, Double price) throws BookingException {
        Optional<Train> trainOpt = trainRepository.findByTrainNumber(ticketPurchaseRequest.getTrainNumber());
        
        if (trainOpt.isEmpty()) {
            return Optional.empty();
        }
        
        Train train = trainOpt.get();
        
        // Assuming each bogie has a fixed number of seats (for example, 50 seats per bogie)
        // and we have 2 bogies, so train.seats should contain 100 seats.
        Optional<Seat> availableSeat = train.getSeats().stream()
                .filter(seat -> isSeatAvailable(seat, ticketPurchaseRequest.getBookingDate()))
                .findFirst();
                
        if (availableSeat.isEmpty()) {
           throw new BookingException("Ticket not booked as no seat available","BK002");
        }
        
        Seat seat = availableSeat.get();

        
        // Create and save the ticket
        Ticket ticket = new Ticket();
        ticket.setTicketId(new Random().nextLong(999999,99999999999L));
        ticket.setSeat(seat);
        ticket.setTrain(train);
        ticket.setUser(ticketPurchaseRequest.getUser());
        ticket.setJourneyDate(ticketPurchaseRequest.getBookingDate());
        ticket.setStatus("Booked");
        ticket.setToStation(ticketPurchaseRequest.getTo());
        ticket.setFromStation(ticketPurchaseRequest.getFrom());
        ticket.setBookingDate(LocalDateTime.now().toString());
        ticket.setDistance(distance);
        ticket.setPrice(price);
        ticketRepository.save(ticket);
        
        return Optional.of(ticket);
    }
    
    private boolean isSeatAvailable(Seat seat, String journeyDate) {
        // Here, we would check if the seat is already booked for the given journeyDate.

        // For simplicity in this example, let's assume all seats are available.
        Ticket ticket =ticketRepository.findBySeatAndJourneyDate(seat,journeyDate);
        return Objects.isNull(ticket);
    }
}

package com.github.railway.service;

import com.github.railway.dao.SeatRepository;
import com.github.railway.dao.TicketRepository;
import com.github.railway.dao.TrainRepository;
import com.github.railway.dao.UserRepository;
import com.github.railway.entity.Seat;
import com.github.railway.entity.Ticket;
import com.github.railway.entity.Train;
import com.github.railway.entity.User;
import com.github.railway.model.TicketPurchaseRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class SeatAllocationService {


    private TrainRepository trainRepository;
    

    private SeatRepository seatRepository;
    

    private TicketRepository ticketRepository;
    

    private UserRepository userRepository;

    public SeatAllocationService(TrainRepository trainRepository, SeatRepository seatRepository, TicketRepository ticketRepository, UserRepository userRepository) {
        this.trainRepository = trainRepository;
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Optional<Ticket> allocateSeat(TicketPurchaseRequest ticketPurchaseRequest) {
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
            return Optional.empty(); // No available seats
        }
        
        Seat seat = availableSeat.get();
//        Optional<User> userOpt = userRepository.findById(ticketPurchaseRequest.getUser().getUserId());
//        if (userOpt.isEmpty()) {
//            return Optional.empty();
//        }
//
//        User user = userOpt.get();
        
        // Create and save the ticket
        Ticket ticket = new Ticket();
        ticket.setTicketId(new Random().nextLong());
        ticket.setSeat(seat);
        ticket.setTrain(train);
        ticket.setUser(ticketPurchaseRequest.getUser());
        ticket.setJourneyDate(ticketPurchaseRequest.getBookingDate());
        ticket.setStatus("Booked");
        ticket.setToStation(ticketPurchaseRequest.getTo());
        ticket.setFromStation(ticketPurchaseRequest.getFrom());

        
        ticketRepository.save(ticket);
        
        return Optional.of(ticket);
    }
    
    private boolean isSeatAvailable(Seat seat, String journeyDate) {
        // Here, we would check if the seat is already booked for the given journeyDate.

        // For simplicity in this example, let's assume all seats are available.
        Ticket ticket =ticketRepository.findBySeatAndJourneyDate(seat,journeyDate);
        return true;
    }
}

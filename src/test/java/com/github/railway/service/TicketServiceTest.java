package com.github.railway.service;

import com.github.railway.dao.TicketRepository;
import com.github.railway.entity.Seat;
import com.github.railway.entity.Ticket;
import com.github.railway.entity.User;
import com.github.railway.exception.BookingException;
import com.github.railway.model.TicketPurchaseRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private DistanceCalculator distanceCalculator;

    @Mock
    private FareCalculator fareCalculator;

    @Mock
    private SeatAllocationService seatAllocationService;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void purchaseTicket_Successful() throws BookingException {
        TicketPurchaseRequest request = new TicketPurchaseRequest();
        request.setBookingDate("2023-04-11");
        request.setTo("Paris");
        request.setFrom("London");
        request.setTrainNumber("1122");
        User user = new User();
        request.setUser(user);
        Ticket expectedTicket = new Ticket();
        expectedTicket.setTicketId(Long.valueOf("1234556"));

        when(distanceCalculator.getDistance(anyString(), anyString())).thenReturn(100.0);
        when(fareCalculator.calculateFare(anyDouble())).thenReturn(150.0);
        when(seatAllocationService.allocateSeat(eq(request), anyDouble(), anyDouble())).thenReturn(Optional.of(expectedTicket));

        Ticket actualTicket = ticketService.purchaseTicket(request);

        assertNotNull(actualTicket);


        verify(distanceCalculator).getDistance(anyString(), anyString());
        verify(fareCalculator).calculateFare(anyDouble());
        verify(seatAllocationService).allocateSeat(eq(request), anyDouble(), anyDouble());
    }

    @Test
    @SneakyThrows
    void purchaseTicket_BookingException() {
        TicketPurchaseRequest request = new TicketPurchaseRequest();
        request.setBookingDate("2023-04-11");
        request.setTo("Paris");
        request.setFrom("London");
        request.setTrainNumber("1122");
        User user = new User();
        request.setUser(user);

        when(distanceCalculator.getDistance(anyString(), anyString())).thenReturn(100.0);
        when(fareCalculator.calculateFare(anyDouble())).thenReturn(150.0);
        when(seatAllocationService.allocateSeat(eq(request), anyDouble(), anyDouble())).thenReturn(Optional.empty());

        assertThrows(BookingException.class, () -> ticketService.purchaseTicket(request));

        verify(distanceCalculator).getDistance(anyString(), anyString());
        verify(fareCalculator).calculateFare(anyDouble());
        verify(seatAllocationService).allocateSeat(eq(request), anyDouble(), anyDouble());
    }

    @Test
    void cancelTicket_Successful() {
        Long ticketId = 1L;

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(new Ticket()));
        doNothing().when(ticketRepository).deleteById(any());
        Boolean result = ticketService.cancelTicket(ticketId);

        assertTrue(result);
//
    }

    @Test
    void viewBookings_Successful() {
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        List<Ticket> expectedTickets = List.of(new Ticket());

        when(ticketRepository.findAllByUserUserId(userId)).thenReturn(expectedTickets);

        List<Ticket> actualTickets = ticketService.viewBookings(user);

        assertNotNull(actualTickets);
        assertEquals(expectedTickets.size(), actualTickets.size());

        verify(ticketRepository).findAllByUserUserId(userId);
    }

    @Test
    void viewBookingsBySection_Successful() {
        String section = "A";
        Ticket ticket = new Ticket();
        Seat seat = new Seat();
        seat.setSection("A");
        ticket.setSeat(seat);
        List<Ticket> allTickets = List.of(ticket);
        List<Ticket> expectedTickets = List.of(allTickets.getFirst());

        when(ticketRepository.findAll()).thenReturn(allTickets);
        List<Ticket> actualTickets = ticketService.viewBookingsBySection(section);

        assertNotNull(actualTickets);
        assertTrue(actualTickets.containsAll(expectedTickets) && expectedTickets.containsAll(actualTickets));

        verify(ticketRepository).findAll();
    }

}

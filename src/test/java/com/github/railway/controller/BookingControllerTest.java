package com.github.railway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.railway.entity.Ticket;
import com.github.railway.entity.User;
import com.github.railway.exception.BookingException;
import com.github.railway.model.TicketPurchaseRequest;
import com.github.railway.service.TicketService;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void bookTicket_ReturnsTicket() throws Exception {
        TicketPurchaseRequest request = new TicketPurchaseRequest();
        request.setBookingDate("2023-04-11");
        request.setTo("Paris");
        request.setFrom("London");
        request.setTrainNumber("1122");
        User user = new User();
        request.setUser(user);
        Ticket expectedTicket = new Ticket();
        given(ticketService.purchaseTicket(any(TicketPurchaseRequest.class))).willReturn(expectedTicket);

        mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedTicket)));
    }

    @Test
    void viewBookings_ReturnsTicketsList() throws Exception {
        User user = new User(); // Populate this as needed
        List<Ticket> expectedTickets = Arrays.asList(new Ticket(), new Ticket()); // Populate as expected
        given(ticketService.viewBookings(any(User.class))).willReturn(expectedTickets);

        mockMvc.perform(get("/viewBookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedTickets)));
    }

    @Test
    void viewBookingsBySection_ReturnsTicketsList() throws Exception {
        String section = "A";
        List<Ticket> expectedTickets = Arrays.asList(new Ticket(), new Ticket()); // Populate as expected
        given(ticketService.viewBookingsBySection(any(String.class))).willReturn(expectedTickets);

        mockMvc.perform(get("/viewBookingsBySection/section/{section}", section))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedTickets)));
    }

    @Test
    void cancelTicket_ReturnsTrue() throws Exception {
        Long ticketId = 1L;
        given(ticketService.cancelTicket(any(Long.class))).willReturn(true);

        mockMvc.perform(delete("/cancelBooking/{ticketId}", ticketId))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}

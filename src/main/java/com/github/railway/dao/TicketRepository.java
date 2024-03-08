package com.github.railway.dao;

import com.github.railway.entity.Seat;
import com.github.railway.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket findBySeatAndJourneyDate(Seat seat, String journeyDate);

    @Query(value = "select * from Ticket where user_id=?", nativeQuery = true)
    List<Ticket> findAllByUserUserId(Long userId);
}
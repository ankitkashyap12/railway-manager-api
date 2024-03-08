package com.github.railway.dao;

import com.github.railway.entity.StationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationDetailRepository extends JpaRepository<StationDetail, String> {
    Optional<StationDetail> findByStationName(String stationName);
}

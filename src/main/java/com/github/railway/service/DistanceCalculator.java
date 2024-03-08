package com.github.railway.service;

import com.github.railway.dao.StationDetailRepository;
import com.github.railway.entity.StationDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

/**
 * @author Ankit Kashyap on 08-03-2024
 */
@Service
@Slf4j
public class DistanceCalculator {

    private final StationDetailRepository stationDetailRepository;
    Random rand = new Random();

    @Autowired
    public DistanceCalculator(StationDetailRepository stationDetailRepository) {
        this.stationDetailRepository = stationDetailRepository;
    }

    public Double getDistance(String station1, String station2) {
        log.info("calculating distance between {} and {} ", station1, station2);
        Optional<StationDetail> station1Details = stationDetailRepository.findByStationName(station1);
        Double lat1 = station1Details.map(StationDetail::getLatitude).orElse(null);
        Double long1 = station1Details.map(StationDetail::getLongitude).orElse(null);

        Optional<StationDetail> station2Details = stationDetailRepository.findByStationName(station2);
        Double lat2 = station2Details.map(StationDetail::getLatitude).orElse(null);
        Double long2 = station2Details.map(StationDetail::getLongitude).orElse(null);

        return calculateDistance(lat1, long1, lat2, long2);


    }


    /**
     * @param lat1  latitude of city1
     * @param long1 longitude of city 2
     * @param lat2  latitude of city 2
     * @param long2 longitude of city2
     * @return distance between these two stations in miles
     *
     * <p> Currently this method will just randomly give some distance in miles
     * in actual many complex algo can be used to calculate distance based on latitude and longitude,</p>
     */
    private Double calculateDistance(Double lat1, Double long1, Double lat2, Double long2) {
        log.info("lat and long for station1 {} ,{}", lat1, long1);
        log.info("lat and long for station2 {} ,{}", lat2, long2);
        return rand.nextDouble(777.00);

    }
}

package com.github.railway.service;

import com.github.railway.dao.StationDetailRepository;
import com.github.railway.entity.StationDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class DistanceCalculatorTest {

    @Mock
    private StationDetailRepository stationDetailRepository;

    @InjectMocks
    private DistanceCalculator distanceCalculator;

    private StationDetail station1Detail;
    private StationDetail station2Detail;

    @BeforeEach
    void setUp() {

        station1Detail = new StationDetail();
        station1Detail.setStationName("Station1");
        station1Detail.setLatitude(10.0);
        station1Detail.setLongitude(20.0);

        station2Detail = new StationDetail();
        station2Detail.setStationName("Station2");
        station2Detail.setLatitude(30.0);
        station2Detail.setLongitude(40.0);
    }

    @Test
    void getDistance_ReturnsNonNullForValidStations() {
        when(stationDetailRepository.findByStationName("Station1")).thenReturn(Optional.of(station1Detail));
        when(stationDetailRepository.findByStationName("Station2")).thenReturn(Optional.of(station2Detail));

        Double distance = distanceCalculator.getDistance("Station1", "Station2");

        assertNotNull(distance);
        assertTrue(distance >= 0);
        verify(stationDetailRepository).findByStationName("Station1");
        verify(stationDetailRepository).findByStationName("Station2");
    }

    @Test
    void getDistance_ReturnsNullWhenStation1NotFound() {
        when(stationDetailRepository.findByStationName("Station1")).thenReturn(Optional.empty());

        Double distance = distanceCalculator.getDistance("Station1", "Station2");

        assertNull(distance); // Since station1 details are not found, we expect the method to return null
        verify(stationDetailRepository).findByStationName("Station1");
    }

    @Test
    void getDistance_ReturnsNullWhenStation2NotFound() {
        when(stationDetailRepository.findByStationName("Station1")).thenReturn(Optional.of(station1Detail));
        when(stationDetailRepository.findByStationName("Station2")).thenReturn(Optional.empty());

        Double distance = distanceCalculator.getDistance("Station1", "Station2");

        assertNull(distance); // Since station2 details are not found, we expect the method to return null
        verify(stationDetailRepository).findByStationName("Station2");
    }
}

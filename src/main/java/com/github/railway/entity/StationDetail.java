package com.github.railway.entity;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Station_Details")
@Getter
@Setter
public class StationDetail {

    @Id
    private String stationCode; // Assuming station_code is unique and can be used as ID
    private String stationName;
    private String division;
    private double longitude;
    private double latitude;
}
package com.github.railway.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Train {
    @Id
    private Long trainId;
    private String trainName;
    private String trainNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "train")
    private Set<Seat> seats;

}

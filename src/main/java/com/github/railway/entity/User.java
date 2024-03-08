package com.github.railway.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

/**
 * @author Ankit Kashyap on 08-03-2024
 */
@Entity
@Getter
@Setter
@ToString
@Table(name ="USER_TABLE")
@NoArgsConstructor
public class User {

    @Id
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
}

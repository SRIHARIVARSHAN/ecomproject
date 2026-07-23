package com.springboot.ecomproject.model;

import com.springboot.ecomproject.enums.JobTitle;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Executive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private JobTitle jobTitle;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
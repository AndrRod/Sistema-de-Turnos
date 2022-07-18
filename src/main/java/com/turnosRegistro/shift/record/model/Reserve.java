package com.turnosRegistro.shift.record.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data @Entity @AllArgsConstructor @NoArgsConstructor
public class Reserve {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotBlank(message = "can't be null")
    private User user;
    @ManyToOne
    @JoinColumn(name = "company_id")
    @NotBlank(message = "can't be null")
    private Company company;
    @NotBlank(message = "can't be null")
    private LocalDate dateTurn;
    @ManyToOne
    @JoinColumn(name = "turn_id")
    @NotBlank(message = "can't be null")
    private Turn turn;
}

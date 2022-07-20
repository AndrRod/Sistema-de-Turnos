package com.turnosRegistro.shift.record.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.time.LocalDate;

@Data @Entity @AllArgsConstructor @NoArgsConstructor
public class Reserve {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    @Column(nullable = false)
    private LocalDate dateTurn;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "turn_id")
    private Turn turn;
}

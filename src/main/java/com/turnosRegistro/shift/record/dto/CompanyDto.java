package com.turnosRegistro.shift.record.dto;

import com.turnosRegistro.shift.record.enums.Day;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;

@Getter @Setter @AllArgsConstructor
public class CompanyDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private UserPartDto userCompany;
    private String name;
    @Pattern(regexp = "^(?:(?:00)?549?)?0?(?:11|[2368]\\d)(?:(?=\\d{0,2}15)\\d{2})??\\d{8}$", message = "number no valid")
    private String phoneNumber;
    private String description;
    @Email(regexp = "^[\\w!#$%&'+/=?`{|}~^-]+(?:\\.[\\w!#$%&'+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message="Email format error")
    private String email;
    @Size(max = 20, message = "The address can only have 20 characters")
    private String address;
    private String logoImage;
    @Size(min= 20, max = 20, message = "The cbu must have 20 characters")
    @Pattern(regexp = "[0-9]*", message = "the cbu only can include numbers")
    private String CBU;
    private Collection<TurnNotAvailableDto> turnNotAviables = new HashSet<>();
    private Collection<Day> daysOfWeekWeWork;
}

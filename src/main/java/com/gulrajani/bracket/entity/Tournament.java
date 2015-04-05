package com.gulrajani.bracket.entity;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;


@Getter
@Builder
@AllArgsConstructor()
@NoArgsConstructor
public class Tournament extends Entity {
    @JsonProperty
    private String name;
    @JsonProperty
    private LocalDate date;
    @JsonProperty
    private List<User> users;
}

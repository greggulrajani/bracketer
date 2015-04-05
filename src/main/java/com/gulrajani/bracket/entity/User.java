package com.gulrajani.bracket.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@AllArgsConstructor
@Getter
public class User extends Entity {
    private final String name;
    private final String email;
    
    public User() {
        super(0);
        name = "";
        email = "";
    }
    

}

package com.toedter.ms60min.thing;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Thing {
    @Id @GeneratedValue @JsonIgnore
    private Long id;
    private String name;
    private String color;

    public Thing(String name, String color) {
        this.name = name;
        this.color = color;
    }
}

package com.example.drohne23.drone;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Position {
    @JsonProperty("x")
    private double x;

    @JsonProperty("y")
    private double y;
    public Position(){

    }

    public Position(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @JsonProperty("z")
    private double z;

}

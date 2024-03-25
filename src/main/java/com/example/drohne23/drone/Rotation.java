package com.example.drohne23.drone;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Rotation
{
    @JsonProperty()
    private double x,y,z,w;
}

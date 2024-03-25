package com.example.drohne23.drone;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DroneData
{
    @JsonProperty("position")
    private Position position;

    @JsonProperty("geschwindigkeit")
    private double geschwindigkeit;

    @JsonProperty("hoehe")
    private double hoehe;

    @JsonProperty("drag")
    private float drag;

    @JsonProperty("useGravity")
    private boolean useGravity;

    @JsonProperty("mass")
    private float mass;

    @JsonProperty("scale")
    private Position scale;

    @JsonProperty("rotation")
    private Rotation rotation;

    @JsonProperty("time")
    private String time;

    @JsonProperty("aktion")
    private String aktion;

    @JsonProperty("wert")
    private float wert;

    @JsonProperty("bewegung")
    private boolean bewegung;

    @JsonProperty("collision")
    private boolean collision;

}

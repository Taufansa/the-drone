package com.thedrone.app.enums;

public class Constant {

    public enum DroneModel {
        Lightweight, Middleweight, Cruiserweight, Heavyweight;
    }

    public enum DroneState {
        IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING;
    }

    public static Integer maxWeight = 500;

    public final static String basePathV1 = "/api/v1";
}

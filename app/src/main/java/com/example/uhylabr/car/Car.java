package com.example.uhylabr.car;

/**
 * Created by UHYLABR on 4/2/2018.
 */

public class Car {
    private String make;
    private String model;
    private int year;
    private boolean hybrid;
    public String id;

    @Override
    public String toString() {
        return "Car{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", hybrid=" + hybrid +
                '}';
    }

    public Car(String id, String make, String model, int year, boolean hybrid) {
    this.id = id;
    this.make = make;
        this.model = model;
        this.year = year;
        this.hybrid = hybrid;
    }
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isHybrid() {
        return hybrid;
    }

    public void setHybrid(boolean hybrid) {
        this.hybrid = hybrid;
    }


}

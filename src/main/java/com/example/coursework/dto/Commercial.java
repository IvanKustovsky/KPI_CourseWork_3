package com.example.coursework.dto;

import java.time.LocalDate;
import java.util.Objects;

public class Commercial {
    private int commercial_id;
    private final int customer_id;
    private final int program_id;
    private final LocalDate air_date;
    private int duration;

    public Commercial(int commercial_id, int customer_id, int program_id, LocalDate air_date, int duration) {
        this.commercial_id = commercial_id;
        this.customer_id = customer_id;
        this.program_id = program_id;
        this.air_date = air_date;
        this.duration = duration;
    }

    public Commercial(int customer_id, int program_id, LocalDate air_date, int duration) {
        this.customer_id = customer_id;
        this.program_id = program_id;
        this.air_date = air_date;
        this.duration = duration;
    }

    public int getCommercial_id() {
        return commercial_id;
    }
    public void setCommercial_id(int commercial_id) {
        this.commercial_id = commercial_id;
    }
    public int getCustomer_id() {
        return customer_id;
    }
    public int getProgram_id() {
        return program_id;
    }
    public LocalDate getAir_date() {
        return air_date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commercial that = (Commercial) o;
        return commercial_id == that.commercial_id && customer_id == that.customer_id
                && program_id == that.program_id && duration == that.duration && Objects.equals(air_date, that.air_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commercial_id, customer_id, program_id, air_date, duration);
    }
}
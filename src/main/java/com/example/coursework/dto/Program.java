package com.example.coursework.dto;

import java.util.Objects;

public class Program {
    private int program_id;
    private String program_name;
    private double rating;
    private double rate;
    private int ad_limit_per_day;

    public Program(int program_id, String program_name, double rating, double rate, int ad_limit_per_day) {
        this.program_id = program_id;
        this.program_name = program_name;
        this.rating = rating;
        this.rate = rate;
        this.ad_limit_per_day = ad_limit_per_day;
    }

    public Program( String program_name, double rating, double rate, int ad_limit_per_day) {
        this.program_name = program_name;
        this.rating = rating;
        this.rate = rate;
        this.ad_limit_per_day = ad_limit_per_day;
    }

    public int getProgram_id() {
        return program_id;
    }

    public void setProgram_id(int program_id) {
        this.program_id = program_id;
    }

    public String getProgram_name() {
        return program_name;
    }

    public void setProgram_name(String program_name) {
        this.program_name = program_name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getAd_limit_per_day() {
        return ad_limit_per_day;
    }

    public void setAd_limit_per_day(int ad_limit_per_day) {
        this.ad_limit_per_day = ad_limit_per_day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program program = (Program) o;
        return program_id == program.program_id && Double.compare(program.rating, rating) == 0
                && Double.compare(program.rate, rate) == 0 && ad_limit_per_day == program.ad_limit_per_day
                && Objects.equals(program_name, program.program_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(program_id, program_name, rating, rate, ad_limit_per_day);
    }
}

package com.example.coursework.dto;

import java.util.Objects;

public class Agent {
    private int agent_id;
    private String agent_name;
    private String agent_surname;
    private double commission_rate;
    private double total_revenue;

    public Agent(int agentId, String agentName, String agentSurname, double commissionRate) {
        this.agent_id = agentId;
        this.agent_name = agentName;
        this.agent_surname = agentSurname;
        this.commission_rate = commissionRate;
    }
    public Agent(int agentId, String agentName, String agentSurname, double commissionRate, double total_revenue) {
        this.agent_id = agentId;
        this.agent_name = agentName;
        this.agent_surname = agentSurname;
        this.commission_rate = commissionRate;
        this.total_revenue = total_revenue;
    }

    public Agent(String agentName, String agentSurname, double commissionRate) {
        this.agent_name = agentName;
        this.agent_surname = agentSurname;
        this.commission_rate = commissionRate;
        this.total_revenue = 0;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getAgent_surname() {
        return agent_surname;
    }

    public void setAgent_surname(String agent_surname) {
        this.agent_surname = agent_surname;
    }

    public double getCommission_rate() {
        return commission_rate;
    }

    public void setCommission_rate(double commission_rate) {
        this.commission_rate = commission_rate;
    }

    public double getTotal_revenue() {
        return total_revenue;
    }

    public void setTotal_revenue(double total_revenue) {
        this.total_revenue = total_revenue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agent agent = (Agent) o;
        return agent_id == agent.agent_id && Double.compare(agent.commission_rate, commission_rate) == 0
                && Double.compare(agent.total_revenue, total_revenue) == 0 && Objects.equals(agent_name, agent.agent_name)
                && Objects.equals(agent_surname, agent.agent_surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agent_id, agent_name, agent_surname, commission_rate, total_revenue);
    }
}
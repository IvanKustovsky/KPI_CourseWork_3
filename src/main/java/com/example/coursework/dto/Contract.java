package com.example.coursework.dto;

import java.util.Objects;

public class Contract {
    private int contract_id;
    private int agent_id;
    private int commercial_id;
    private String agentFullName;
    private String companyDetails;
    private final double contract_amount;
    private double agentRate;
    private double agentCommission;

    public Contract(int contract_id, int agent_id, int commercial_id, double contract_amount) {
        this.contract_id = contract_id;
        this.agent_id = agent_id;
        this.commercial_id = commercial_id;
        this.contract_amount = contract_amount;
    }
    public Contract(int agent_id, int commercial_id, double contract_amount) {
        this.agent_id = agent_id;
        this.commercial_id = commercial_id;
        this.contract_amount = contract_amount;
    }

    public Contract(int contract_id, String agentFullName, String companyDetails,
                    double agentRate, double contract_amount, double agentCommission) {
        this.contract_id = contract_id;
        this.agentFullName = agentFullName;
        this.companyDetails = companyDetails;
        this.agentRate = agentRate;
        this.contract_amount = contract_amount;
        this.agentCommission = agentCommission;
    }
    public int getContract_id() {
        return contract_id;
    }
    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }
    public int getAgent_id() {
        return agent_id;
    }
    public int getCommercial_id() {
        return commercial_id;
    }
    public double getContract_amount() {
        return contract_amount;
    }

    public String getAgentFullName() {
        return agentFullName;
    }

    public String getCompanyDetails() {
        return companyDetails;
    }

    public double getAgentRate() {
        return agentRate;
    }

    public double getAgentCommission() {
        return agentCommission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return contract_id == contract.contract_id && agent_id == contract.agent_id
                && commercial_id == contract.commercial_id && Double.compare(contract.contract_amount, contract_amount) == 0
                && Double.compare(contract.agentRate, agentRate) == 0
                && Double.compare(contract.agentCommission, agentCommission) == 0
                && Objects.equals(agentFullName, contract.agentFullName)
                && Objects.equals(companyDetails, contract.companyDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contract_id, agent_id, commercial_id, agentFullName, companyDetails,
                contract_amount, agentRate, agentCommission);
    }
}

package com.example.coursework.database;

public class Const {
    // Tables
    public static final String CONTACT_PERSON_TABLE = "contact_person";
    public static final String CUSTOMER_TABLE = "customer";
    public static final String PROGRAM_TABLE = "program";
    public static final String COMMERCIAL_TABLE = "commercial";
    public static final String AGENT_TABLE = "agent";
    public static final String CONTRACT_TABLE = "contract";

    // Contact person
    public static final String CONTACT_PERSON_ID = "id";
    public static final String CONTACT_PERSON_NAME = "name";
    public static final String CONTACT_PERSON_SURNAME = "surname";

    // Customer
    public static final String CUSTOMER_ID = "customer_id";
    public static final String CUSTOMER_BANK_DETAILS = "bank_details";
    public static final String CUSTOMER_PHONE = "phone";
    public static final String CUSTOMER_EMAIL = "email";
    public static final String CUSTOMER_PASSWORD = "password";
    public static final String CUSTOMER_CONTACT_PERSON_ID = "contact_person_id";
    public static final String CUSTOMER_IS_ADMIN = "is_admin";

    // Program
    public static final String PROGRAM_ID = "program_id";
    public static final String PROGRAM_NAME = "program_name";
    public static final String PROGRAM_RATING = "rating";
    public static final String PROGRAM_AD_RATE = "rate";
    public static final String PROGRAM_AD_LIMIT_PER_DAY = "ad_limit_per_day";

    // Commercial
    public static final String COMMERCIAL_ID = "commercial_id";
    public static final String COMMERCIAL_CUSTOMER_ID = "customer_id";
    public static final String COMMERCIAL_PROGRAM_ID = "program_id";
    public static final String COMMERCIAL_AIR_DATE = "air_date";
    public static final String COMMERCIAL_DURATION = "duration";

    // Agent
    public static final String AGENT_ID = "agent_id";
    public static final String AGENT_NAME = "agent_name";
    public static final String AGENT_SURNAME = "agent_surname";
    public static final String AGENT_COMMISSION_RATE = "commission_rate";
    public static final String AGENT_TOTAL_REVENUE = "total_revenue";
    public static final String AGENT_DETAILS = "agent_details";

    // Contract
    public static final String CONTRACT_ID = "contract_id";
    public static final String CONTRACT_AGENT_ID = "agent_id";
    public static final String CONTRACT_COMMERCIAL_ID = "commercial_id";
    public static final String CONTRACT_AMOUNT = "contract_amount";
    public static final String CONTRACT_AGENT_FULL_NAME = "agentFullName";
    public static final String CONTRACT_AGENT_RATE = "agentRate";
    public static final String CONTRACT_AGENT_COMMISSION = "agentCommission";
    public static final String CONTRACT_COMPANY_DETAILS = "companyDetails";


    // CustomerOrder
    public static final String CUSTOMER_ORDER_CUSTOMER_ID = "commercialId";
    public static final String CUSTOMER_ORDER_CUSTOMER_BANK_DETAILS = "bankDetails";
    public static final String CUSTOMER_ORDER_PROGRAM_NAME = "programName";
    public static final String CUSTOMER_ORDER_PROGRAM_RATING = "programRating";
    public static final String CUSTOMER_ORDER_PROGRAM_RATE = "programRate";
    public static final String CUSTOMER_ORDER_AIR_DATE = "airDate";
    public static final String CUSTOMER_ORDER_DURATION = "adDuration";
    public static final String CUSTOMER_ORDER_CONTRACT_AMOUNT = "contractAmount";
    public static final String CUSTOMER_ORDER_AGENT_DETAILS = "agentDetails";
}


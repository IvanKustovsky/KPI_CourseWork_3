package com.example.coursework.dao.contract;

import com.example.coursework.dao.BaseDao;
import com.example.coursework.dto.Contract;

import java.util.List;

public interface ContractDao extends BaseDao<Contract> {
    double calculateContractAmount(int adDuration, int programId);
    List<Contract> findAllWithDetails();
}

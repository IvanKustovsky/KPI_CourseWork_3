package com.example.coursework.dao.agent;

import com.example.coursework.dao.BaseDao;
import com.example.coursework.dto.Agent;

import java.util.List;

public interface AgentDao extends BaseDao<Agent> {
   List<String> findAllAgentsDetails();
   void updateAgentRevenue();
}

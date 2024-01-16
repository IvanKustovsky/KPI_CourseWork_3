package com.example.coursework.dao.commercial;

import com.example.coursework.dao.BaseDao;
import com.example.coursework.dto.Commercial;

import java.time.LocalDate;

public interface CommercialDao extends BaseDao<Commercial> {
    int getAdCountPerDay(int program_id, LocalDate date);
}

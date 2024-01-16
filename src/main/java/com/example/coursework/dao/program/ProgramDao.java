package com.example.coursework.dao.program;

import com.example.coursework.dao.BaseDao;
import com.example.coursework.dto.Program;

public interface ProgramDao extends BaseDao<Program> {
    Program findByName(String name);

}

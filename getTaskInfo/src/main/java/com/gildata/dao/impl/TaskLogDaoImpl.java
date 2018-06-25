package com.gildata.dao.impl;

import com.gildata.dao.TaskLogDao;
import com.gildata.domain.TaskLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by LiChao on 2018/6/14
 */
@Repository(value = "taskLogDao")
public class TaskLogDaoImpl implements TaskLogDao {


    @Autowired
    @Qualifier("taskcCnfigJdbcTemplate")
    private JdbcTemplate taskcCnfigJdbcTemplate;


    @Override
    public List<TaskLog> getTaskLogs(String sql){
        List<TaskLog> taskLogs = taskcCnfigJdbcTemplate.query(sql,new Object[]{},new BeanPropertyRowMapper(TaskLog.class));
        return taskLogs;
    }



}

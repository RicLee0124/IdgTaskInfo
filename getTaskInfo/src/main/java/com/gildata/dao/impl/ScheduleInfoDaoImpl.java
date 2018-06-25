package com.gildata.dao.impl;

import com.gildata.dao.ScheduleInfoDao;
import com.gildata.domain.ScheduleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by LiChao on 2018/6/14
 */
@Repository(value = "scheduleInfoDao")
public class ScheduleInfoDaoImpl implements ScheduleInfoDao {


    @Autowired
    @Qualifier("taskcCnfigJdbcTemplate")
    private JdbcTemplate taskcCnfigJdbcTemplate;

    @Autowired
    @Qualifier("taskinfoJdbcTemplate")
    private JdbcTemplate taskinfoJdbcTemplate;


    @Override
    public List<ScheduleInfo> getScheduleInfos() {
        String sql = "select ti.TaskID,ti.CustomerId,si.SchedulingID,si.SchedulingExpression from cfg_Task_Scheduling ts left join cfg_TaskInfo ti on ti.TaskID = ts.TaskID left join cfg_SchedulingInfo si \n" +
                "on si.SchedulingID = ts.SchedulingID";
        List<ScheduleInfo> scheduleInfos = taskcCnfigJdbcTemplate.query(sql,new Object[]{},new BeanPropertyRowMapper(ScheduleInfo.class));
        return scheduleInfos;
    }

    @Override
    public void insertScheduleInfos(List<ScheduleInfo> scheduleInfoLlst) {
        String sql = "insert into IdgLog(taskId,customerId,scheduleingId,SchedulingExpression,executionDate) values(?,?,?,?,?)";

    }


    @Override
    public List<ScheduleInfo> getIdgSchedule() {
        String sql = "select taskId,customerId,scheduleingId,SchedulingExpression from IdgLog group by taskId,customerId,scheduleingId,SchedulingExpression";
        List<ScheduleInfo> scheduleInfos = taskinfoJdbcTemplate.query(sql,new Object[]{},new BeanPropertyRowMapper(ScheduleInfo.class));
        return scheduleInfos;
    }
}

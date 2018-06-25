package com.gildata.dao.impl;

import com.gildata.dao.IdgLogDao;
import com.gildata.domain.IdgLog;
import com.gildata.domain.ScheduleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LiChao on 2018/6/14
 */
@Repository(value = "idgLogDao")
public class IdgLogDaoImpl implements IdgLogDao{


    @Autowired
    @Qualifier("taskinfoJdbcTemplate")
    private JdbcTemplate taskinfoJdbcTemplate;


    @Override
    public void insertSchduleInfo(List<ScheduleInfo> scheduleInfos) {
        String insertSql = "insert into IdgLog(taskId,customerId,scheduleingId,SchedulingExpression,executionDate) values(?,?,?,?,?)";
        final List<IdgLog> idgLogs = new ArrayList<>();
        for(ScheduleInfo scheduleInfo : scheduleInfos){
            List<String> cronDates = scheduleInfo.getCronDates();
            for(String cronDate : cronDates){
                IdgLog idgLog = new IdgLog();
                idgLog.setTaskId(scheduleInfo.getTaskId());
                idgLog.setScheduleingId(scheduleInfo.getSchedulingID());
                idgLog.setCustomerId(scheduleInfo.getCustomerId());
                idgLog.setSchedulingExpression(scheduleInfo.getSchedulingExpression());
                idgLog.setExecutionDate(cronDate);
                idgLogs.add(idgLog);
            }
        }
        taskinfoJdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                IdgLog idgLog = idgLogs.get(i);
                preparedStatement.setLong(1,idgLog.getTaskId());
                preparedStatement.setLong(2,idgLog.getCustomerId());
                preparedStatement.setLong(3,idgLog.getScheduleingId());
                preparedStatement.setString(4,idgLog.getSchedulingExpression());
                preparedStatement.setString(5,idgLog.getExecutionDate());
            }

            @Override
            public int getBatchSize() {
                return idgLogs.size();
            }
        });
    }

    @Override
    public List<IdgLog> getIdgLogs(Long taskId, Long scheduleingId) {
        String querySql = "select *from IdgLog where taskId="+taskId+" and scheduleingId="+scheduleingId;
        List<IdgLog> idgLogs = taskinfoJdbcTemplate.query(querySql,new Object[]{},new BeanPropertyRowMapper(IdgLog.class));
        return idgLogs;
    }

    @Override
    public void update(IdgLog idgLog) {
        String updateSql = "update IdgLog set taskLogId = ?,startDate= ?,endDate= ?,status = ?," +
                "totalTime= ?,XDJobID = ?,MQMsgCreatedDate= ? where id = ?";
        taskinfoJdbcTemplate.update(updateSql,idgLog.getTaskLogId(),idgLog.getStartDate()
                ,idgLog.getEndDate(),idgLog.getStatus(),idgLog.getTotalTime(),
                idgLog.getXDJobID(),idgLog.getMQMsgCreatedDate(),idgLog.getId());
    }

    @Override
    public List<IdgLog> getTop1IdgLogs() {
        String sql = "select top 1 *from IdgLog where taskLogId  IS NOT NULL or XDJobID IS NOT NULL order by taskLogId desc";
        List<IdgLog> idgLogs = taskinfoJdbcTemplate.query(sql,new Object[]{},new BeanPropertyRowMapper(IdgLog.class));
        return idgLogs;
    }

    @Override
    public boolean isInsertedCron() {
        String executionDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " 00:00:01";
        String sql = "select top 10 *from IdgLog where executionDate>'"+executionDate+"'";
        List<IdgLog> idgLogs = taskinfoJdbcTemplate.query(sql,new Object[]{},new BeanPropertyRowMapper(IdgLog.class));
        if(idgLogs.size()>0){
            return true;
        }
        return false;
    }


    @Override
    public void delete(String sql){
        taskinfoJdbcTemplate.execute(sql);
    }
}

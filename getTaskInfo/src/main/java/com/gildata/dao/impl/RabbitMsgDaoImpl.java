package com.gildata.dao.impl;

import com.gildata.dao.RabbitMsgDao;
import com.gildata.domain.RabbitMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LiChao on 2018/6/14
 */
@Repository
public class RabbitMsgDaoImpl implements RabbitMsgDao {

    @Autowired
    private JdbcTemplate rabbitmsgJdbcTemplate;



    @Override
    public List<RabbitMsg> getRabbitMsgs(final Map<Long,List<Long>> params) {
        final String sql = "select *from dbo.interfaceTaskInfo where taskid = ? and JOB_EXECUTION_ID = ?";
        List<RabbitMsg> rabbitMsgs = new ArrayList<>();
        for(Map.Entry<Long,List<Long>> entry : params.entrySet()){
            final Long taskId = entry.getKey();
            List<Long> xdJobIds = entry.getValue();
            for(Long xdJobId : xdJobIds){
                try{
                    RabbitMsg rabbitMsg = rabbitmsgJdbcTemplate.queryForObject(sql, new RowMapper<RabbitMsg>() {
                        @Override
                        public RabbitMsg mapRow(ResultSet resultSet, int i) throws SQLException {
                            RabbitMsg rabbitMsg = new RabbitMsg();
                            rabbitMsg.setTaskId(Long.valueOf(resultSet.getString("taskId")));
                            rabbitMsg.setCreatedDate(resultSet.getString("createdDate"));
                            rabbitMsg.setJOB_EXECUTION_ID(resultSet.getString("JOB_EXECUTION_ID"));
                            return rabbitMsg;
                        }
                    },taskId,xdJobId);
                    rabbitMsgs.add(rabbitMsg);
                }catch (Exception e){
                    System.out.println("taskId: "+taskId+", xdJobId"+xdJobId);
                    e.printStackTrace();
                }
            }
        }
        return rabbitMsgs;
    }


    @Override
    public void delete(String sql){
        rabbitmsgJdbcTemplate.execute(sql);
    }
}

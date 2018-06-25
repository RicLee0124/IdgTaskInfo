package com.gildata.dao;

import com.gildata.domain.RabbitMsg;

import java.util.List;
import java.util.Map;

/**
 * Created by LiChao on 2018/6/14
 */
public interface RabbitMsgDao {
    public List<RabbitMsg> getRabbitMsgs(Map<Long,List<Long>> params);

    public void delete(String sql);
}

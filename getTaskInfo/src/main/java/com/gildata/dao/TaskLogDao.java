package com.gildata.dao;

import com.gildata.domain.TaskLog;

import java.util.List;

/**
 * Created by LiChao on 2018/6/14
 */
public interface TaskLogDao {

    public List<TaskLog> getTaskLogs(String sql);
}

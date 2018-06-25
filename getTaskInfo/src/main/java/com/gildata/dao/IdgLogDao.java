package com.gildata.dao;

import com.gildata.domain.IdgLog;
import com.gildata.domain.ScheduleInfo;

import java.util.List;

/**
 * Created by LiChao on 2018/6/14
 */
public interface IdgLogDao {

    public void insertSchduleInfo(List<ScheduleInfo> scheduleInfos);

    public List<IdgLog> getIdgLogs(Long taskId,Long scheduleingId);

    public void update(IdgLog idgLog);

    public List<IdgLog> getTop1IdgLogs();

    public boolean isInsertedCron();

    public void delete(String sql);
}

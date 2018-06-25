package com.gildata.dao;

import com.gildata.domain.ScheduleInfo;

import java.util.List;

/**
 * Created by LiChao on 2018/6/14
 */
public interface ScheduleInfoDao {

    public List<ScheduleInfo> getScheduleInfos();

    public void insertScheduleInfos(List<ScheduleInfo> scheduleInfoLlst);

    public List<ScheduleInfo> getIdgSchedule();

}

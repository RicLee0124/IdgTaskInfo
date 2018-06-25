package com.gildata.service;

import com.gildata.dao.ScheduleInfoDao;
import com.gildata.domain.ScheduleInfo;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by LiChao on 2018/6/14
 */
@Service
public class ScheduleInfoService {


    @Autowired
    private ScheduleInfoDao scheduleInfoDao;


    public List<ScheduleInfo> getExecutionDate(boolean isTomorow){
        Map<Long,List<String>> cronDatesMap = new HashMap<>();
        List<ScheduleInfo> scheduleInfos = scheduleInfoDao.getScheduleInfos();
        for(ScheduleInfo scheduleInfo : scheduleInfos){
            if(!cronDatesMap.containsKey(scheduleInfo.getSchedulingID())){
                cronDatesMap.put(scheduleInfo.getSchedulingID(),getDateByCron(scheduleInfo.getSchedulingExpression(),isTomorow));
            }
        }
        for(ScheduleInfo scheduleInfo : scheduleInfos){
            if(cronDatesMap.containsKey(scheduleInfo.getSchedulingID())){
                scheduleInfo.setCronDates(cronDatesMap.get(scheduleInfo.getSchedulingID()));
            }
        }
        return scheduleInfos;
    }


    public List<String> getDateByCron(String cronExpression,boolean isTomorow){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        List<String> crontimes = null;
        Date nextTime = null;
        try {
            if(isTomorow){
                nextTime = new Date(df.parse(df2.format(new Date()) + " 00:00:00").getTime()+24*3600*1000);
            }else{
                nextTime = df.parse(df2.format(new Date()) + " 00:00:00");
            }
            Date to = new Date(nextTime.getTime() + 24*3600*1000);
            CronExpression expression;
            crontimes = new ArrayList<>();
            expression = new CronExpression(cronExpression);
            for(;nextTime.getTime()<=to.getTime();){
                nextTime = expression.getNextValidTimeAfter(nextTime);
                if(nextTime.getTime()>=to.getTime()) break;
                crontimes.add(df.format(nextTime));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return crontimes;
    }


}

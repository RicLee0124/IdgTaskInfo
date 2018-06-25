package com.gildata.service;

import com.gildata.dao.IdgLogDao;
import com.gildata.dao.RabbitMsgDao;
import com.gildata.dao.TaskLogDao;
import com.gildata.domain.IdgLog;
import com.gildata.domain.RabbitMsg;
import com.gildata.domain.ScheduleInfo;
import com.gildata.domain.TaskLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by LiChao on 2018/6/14
 */
@Service
public class IdgLogService {

    private final Logger logger = LoggerFactory.getLogger(IdgLogService.class);

    public static long taskLogId = 0;

//    private final Lock lock = new ReentrantLock();

    @Autowired
    private IdgLogDao idgLogDao;

    @Autowired
    private TaskLogDao taskLogDao;

    @Autowired
    private RabbitMsgDao rabbitMsgDao;

    @Autowired
    private ScheduleInfoService scheduleInfoService;

    public void init(){
        //判断是否将cron表达式插入过
        if(!idgLogDao.isInsertedCron()){
            logger.debug("method init was executed insertSchduleInfo....");
            List<ScheduleInfo> scheduleInfos = scheduleInfoService.getExecutionDate(false);
            idgLogDao.insertSchduleInfo(scheduleInfos);
        }
        //判断是否填充过数据
        List<IdgLog> idgLogs = idgLogDao.getTop1IdgLogs();
        if(idgLogs.size()>0){
            taskLogId = idgLogs.get(0).getTaskLogId();
            logger.debug("IdgLogService method init taskLogId: {}, taskLog: {}",taskLogId,idgLogs.get(0));
            return;
        }
        //没有填充过数据，填充今天到目前为止所有数据
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " 00:00:00";
        String sql = "select ID,TaskID,StartDate,EndDate,Status,TotalTime,XDJobID,Performer,PerformerType from cfg_TaskLog where EndDate>'"+dateStr+"' order by ID desc";
        List<TaskLog> taskLogs = taskLogDao.getTaskLogs(sql);
        if(taskLogs.size()>0){
            TaskLog taskLog = taskLogs.get(0);
            taskLogId = taskLog.getId();
            logger.debug("IdgLogService method init taskLogId: {}, taskLog: {}",taskLogId,taskLog);
            insertIdgLog(taskLogs);
        }
    }


    //每分钟执行一次
    @Scheduled(cron = "0 */1 * * * ?")
    public void fillIdgLog(){
        if(taskLogId!=0){
            logger.debug("method fillIdgLog was executed...");
            String sql = "select ID,TaskID,StartDate,EndDate,Status,TotalTime,XDJobID,Performer,PerformerType from cfg_TaskLog where ID > '"+taskLogId+"' order by ID desc";
            List<TaskLog> taskLogs = taskLogDao.getTaskLogs(sql);
            if(taskLogs.size()>0){
                TaskLog taskLog = taskLogs.get(0);
                taskLogId = taskLog.getId();
                logger.debug("IdgLogService method fillIdgLog taskLogId: {}, taskLog: {}",taskLogId,taskLog);
                insertIdgLog(taskLogs);
            }
        }
    }


    //每天晚上23点，填充第二天的cron表达式数据
    @Scheduled(cron = "0 0 23 * * ?")
    public void insertExecutionDate(){
        logger.debug("method insertExecutionDate was executed ....");
        List<ScheduleInfo> scheduleInfos = scheduleInfoService.getExecutionDate(true);
        idgLogDao.insertSchduleInfo(scheduleInfos);
    }


    //删除数据
    @Scheduled(cron = "0 0 1 * * ?")
    public void deleteData() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        //IdgLog只保留一周的数据
        String dateStr = df.format(new Date(df.parse(df2.format(new Date()) + " 00:00:00").getTime()-7*24*3600*1000));
        String delSql = "delete from IdgLog where createdDate<'"+dateStr+"'";
        idgLogDao.delete(delSql);
        //rabbitMsg只保留最近十五天的数据
        dateStr = df.format(new Date(df.parse(df2.format(new Date()) + " 00:00:00").getTime()-15*24*3600*1000));
        delSql = "delete from dbo.chunkrequest where createdDate<'"+dateStr+"'";
        rabbitMsgDao.delete(delSql);
        delSql = "delete fromd dbo.chunkresponse where createdDate<'"+dateStr+"'";
        rabbitMsgDao.delete(delSql);
    }



    //每间隔五分钟更新一次调度信息
//    @Scheduled(cron = "0 */5 * * * ?")
//    public void updateSchedule(){
//        //当在填充数据时，不允许更新调度信息
//        lock.lock();
//        try{
//            //只更新当前时间之后的调度信息
//        }finally {
//            lock.unlock();
//        }
//    }



    public void insertIdgLog(List<TaskLog> taskLogs) {
        List<TaskLog> ts = new ArrayList<>();
        Map<Long,List<Long>> params = new HashMap<>();
        Map<Long,List<TaskLog>> taskIdsMap = new HashMap<>();
        for(TaskLog taskLog : taskLogs){
            if(!taskLog.getPerformerType().equals("UserDo")){
                ts.add(taskLog);
                if(params.containsKey(taskLog.getTaskID())){
                    taskIdsMap.get(taskLog.getTaskID()).add(taskLog);
                    if(taskLog.getXDJobID()!=null){
                        params.get(taskLog.getTaskID()).add(Long.valueOf(taskLog.getXDJobID()));
                    }
                }else{
                    List<Long> xdJobIds = new ArrayList<>();
                    List<TaskLog> taskLogs1 = new ArrayList<>();
                    taskLogs1.add(taskLog);
                    taskIdsMap.put(taskLog.getTaskID(),taskLogs1);
                    if(taskLog.getXDJobID()!=null){
                        xdJobIds.add(Long.valueOf(taskLog.getXDJobID()));
                        params.put(taskLog.getTaskID(),xdJobIds);
                    }
                }
            }
        }
        List<RabbitMsg> rabbitMsgs = rabbitMsgDao.getRabbitMsgs(params);
        for(RabbitMsg rabbitMsg : rabbitMsgs){
            if(taskIdsMap.containsKey(rabbitMsg.getTaskId())){
                List<TaskLog> taskLogs2 = taskIdsMap.get(rabbitMsg.getTaskId());
                for(TaskLog taskLog : taskLogs2){
                    if(taskLog.getXDJobID().equals(rabbitMsg.getJOB_EXECUTION_ID())){
                        taskLog.setMqCreatedDate(rabbitMsg.getCreatedDate());
                    }
                }
            }
        }
        try {
            updateIdgLog(ts);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public void updateIdgLog(List<TaskLog> taskLogs) throws ParseException {
        int count = 0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        try
        {
            for(TaskLog taskLog : taskLogs){
                List<IdgLog> idgLogs = idgLogDao.getIdgLogs(taskLog.getTaskID(),Long.valueOf(taskLog.getPerformer()));
                //存在调度被修改的情况，后续需要处理
                if(idgLogs.size()==0){
                    logger.debug("idgLogservice method updateIdgLog idgLogs.size() = 0 : " + taskLog);
                    continue;
                }
                if(idgLogs.size()==1) {
                    count+=1;
                    update(idgLogs.get(0),taskLog);
                }else{
                    Map<Long,IdgLog> map = new HashMap<>();
                    for(IdgLog idgLog : idgLogs){
                        map.put(df.parse(idgLog.getExecutionDate()).getTime(),idgLog);
                    }
                    Set<Long> keyset = map.keySet();
                    List<Long> keys = new ArrayList<>();
                    keys.addAll(keyset);
                    Collections.sort(keys);
                    //数据库所在服务器时间不一致
                    long taskLogTime = df.parse(taskLog.getMqCreatedDate()).getTime()+150*1000;
                    int flag = 0;
                    for(int i=0;i<keys.size();i++){
                        if(taskLogTime<keys.get(i)){
                            flag = i;
                            break;
                        }
                        flag = i;
                    }
                    if(flag == 0) {
                        //flag=0的情况有多种，size=0;size>0,但是rabbitmq创建时间小于flag=0对应的值。
                        logger.debug("idgLogservice method updateIdgLog flag = 0 :" + taskLog+" :" + map.get(keys.get(flag)));
                        count+=1;
                        update(map.get(keys.get(flag)),taskLog);
                    }else{
                        count+=1;
                        //存在一种情况，所有的时间都要比taskLogTime要小
                        if(taskLogTime>keys.get(flag)){
                            update(map.get(keys.get(flag)),taskLog);
                        }else{
                            //与较大的间隔小于三十秒或者较小的间隔比较大的间隔大三分钟并且与较大的间隔小于一分钟，选择较大的
                            if((keys.get(flag)-taskLogTime)<30*1000||(((taskLogTime-keys.get(flag-1))-(keys.get(flag)-taskLogTime))>3*60*1000&&(keys.get(flag)-taskLogTime)<1*60*1000)){
                                //时间点更加靠近keys.get(flag)
                                update(map.get(keys.get(flag)),taskLog);
                            }else{
                                update(map.get(keys.get(flag-1)),taskLog);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            logger.debug("Exception : {}",e.getMessage());
        }
        logger.debug("count = "+count);
    }

    public void update(IdgLog idgLog,TaskLog taskLog){
        //打印被覆盖的日志
        if(idgLog.getStatus()!=null){
            logger.debug("idgLogservice method update idgLog!=null "+ idgLog);
        }
        idgLog.setTaskLogId(taskLog.getId());
        idgLog.setStartDate(taskLog.getStartDate());
        idgLog.setEndDate(taskLog.getEndDate());
        idgLog.setStatus(taskLog.getStatus());
        idgLog.setTotalTime(taskLog.getTotalTime());
        idgLog.setXDJobID(taskLog.getXDJobID());
        idgLog.setMQMsgCreatedDate(taskLog.getMqCreatedDate());
        idgLogDao.update(idgLog);
    }
}

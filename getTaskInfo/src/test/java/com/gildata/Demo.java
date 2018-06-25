package com.gildata;

import com.gildata.dao.IdgLogDao;
import com.gildata.dao.RabbitMsgDao;
import com.gildata.dao.ScheduleInfoDao;
import com.gildata.dao.TaskLogDao;
import com.gildata.domain.ScheduleInfo;
import com.gildata.domain.TaskLog;
import com.gildata.service.IdgLogService;
import com.gildata.service.ScheduleInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LiChao on 2018/6/14
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Demo {

    private final Logger log = LoggerFactory.getLogger(Demo.class);

    public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    public static SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private ScheduleInfoDao scheduleInfoDao;

    @Autowired
    private ScheduleInfoService scheduleInfoService;

    @Autowired
    private TaskLogDao taskLogDao;

    @Autowired
    private IdgLogDao idgLogDao;

    @Autowired
    private IdgLogService idgLogService;

    @Autowired
    private RabbitMsgDao rabbitMsgDao;


    @Test
    public void test1(){
        List<ScheduleInfo> scheduleInfos = scheduleInfoDao.getScheduleInfos();
        for(ScheduleInfo scheduleInfo :  scheduleInfos){
            System.out.println(scheduleInfo);
        }
    }

    @Test
    public void test3(){
        List<ScheduleInfo> scheduleInfos = scheduleInfoService.getExecutionDate(true);
        idgLogDao.insertSchduleInfo(scheduleInfos);
    }

    @Test
    public void test4(){
        List<TaskLog> taskLogs = taskLogDao.getTaskLogs("");
        for(TaskLog taskLog : taskLogs){
            System.out.println(taskLog);
        }
    }

    @Test
    public void test5(){
        idgLogService.init();
    }


    @Test
    public void testCron() throws ParseException {
        Date nextTime = new Date(df.parse(df2.format(new Date()) + " 00:00:00").getTime()+24*3600*1000);
        System.out.println(nextTime);
        Date to = new Date(nextTime.getTime() + 24*3600*1000);
        System.out.println(to);
        CronExpression expression;
        List<Date> crontimes = new ArrayList<>();
        expression = new CronExpression("0 0/10 8-10 * * ?");
        for(;nextTime.getTime()<=to.getTime();){
            nextTime = expression.getNextValidTimeAfter(nextTime);
            if(nextTime.getTime()>=to.getTime()) break;
            crontimes.add(nextTime);
        }
        for(int i=0;i<crontimes.size();i++){
            System.out.println(df.format(crontimes.get(i)));
        }
    }


    public boolean isAllNum(String str){
        Pattern numpat = Pattern.compile("[0-9]{1,}");
        Matcher matcher = numpat.matcher(str);
        if(matcher.matches()){
            return true;
        }else {
            return false;
        }
    }


    public Long parseXDJobParams(String xdJobParams){
        xdJobParams = xdJobParams.replaceAll("\n", "");
        String regex = "schedulingID=(.*), random";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(xdJobParams);
        if(m.find()){
            if(isAllNum(m.group(1)));
            return Long.valueOf(m.group(1));
        }
        return null;
    }

    @Test
    public void testLogger(){
        log.debug("hello world! this is my first log....");
    }

    @Test
    public void testDate(){
        System.out.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S")).format(new Date()));
    }


    @Test
    public void deleteData() throws ParseException {
        //rabbitMsg只保留最近十五天的数据
        String dateStr = df.format(new Date(df.parse(df2.format(new Date()) + " 00:00:00").getTime()-15*24*3600*1000));
        String delSql = "delete from dbo.chunkrequest where createdDate<'"+dateStr+"'";
        rabbitMsgDao.delete(delSql);
        delSql = "delete fromd dbo.chunkresponse where createdDate<'"+dateStr+"'";
        rabbitMsgDao.delete(delSql);
    }

}

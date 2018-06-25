package com.gildata;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LiChao on 2018/6/11
 */
public class RegexDemo {

    public static String str1 = "note='cfg_TaskInfo:  TaskId= 4560 CustomerId= 10427 SysTaskNameId= 11232 TaskName= 恒生PBOX_恒生PBOX_指数基本信息 FileName= PBOX_ZSXX_{yyyymmdd} FileType= EXCEL2007 IfCompre= Y IfFirstRowByName= Y IfDoThreeDayTask= N IfNoCreateEmptyFile= N IfMonitDataNum= N MinDataNum= null IfMonitFileSize= N MinFileSize= null MaxFileSize= null IfIssued= N IfUse= Y TaskSqls=[cfg_Task_Sql:  Id= 11589 TaskID= 4560 SerialNum= 10 SerialName= PBOX_ZSXX SpiltChar= , Sqlstr= DECLARE @EndDate DATETIME SET @EndDate = #EndDate# EXEC dbo.HSJY_PBOX_ZSXX @EndDate] Directorys = 恒生PBOX'";

    @Test
    public void test1(){
        String sr = "dada ada adad adsda ad asdda adr3 fas daf fas fdsf 234 adda";
        //包含两个匹配组，一个是三个字符，一个是匹配四个字符
        Pattern pet = Pattern.compile("\\b(\\w{3}) *(\\w{4})\\b");
        Matcher match = pet.matcher(sr);
        int countAll = match.groupCount();//2
        while (match.find()) {
            System.out.print("匹配组结果：");
            for (int i = 0; i < countAll; i++) {
                System.out.print(String.format("\n\t第%s组的结果是:%s",i+1,match.group(i + 1)));
            }
            System.out.print("\n匹配的整个结果:");
            System.out.println(match.group());
        }
    }


    @Test
    public void test2(){
        //包含两个匹配组，一个是三个字符，一个是匹配四个字符
        //TaskId= [0-9]*
        Pattern pet = Pattern.compile("TaskName= (.*) FileName");
        Matcher match = pet.matcher(str1);
        while (match.find()) {
            System.out.println(match.group());
        }
    }

    @Test
    public void test3(){
        String str = "note='cfg_TaskInfo:  TaskId= 4560 CustomerId= 10427 SysTaskNameId= 11232 TaskName= 恒生PBOX_恒生PBOX_指数基本信息 FileName= PBOX_ZSXX_{yyyymmdd} FileType= EXCEL2007 IfCompre= Y IfFirstRowByName= Y IfDoThreeDayTask= N IfNoCreateEmptyFile= N IfMonitDataNum= N MinDataNum= null IfMonitFileSize= N MinFileSize= null MaxFileSize= null IfIssued= N IfUse= Y TaskSqls=[cfg_Task_Sql:  Id= 11589 TaskID= 4560 SerialNum= 10 SerialName= PBOX_ZSXX SpiltChar= , Sqlstr= DECLARE @EndDate DATETIME SET @EndDate = #EndDate# EXEC dbo.HSJY_PBOX_ZSXX @EndDate] Directorys = 恒生PBOX'";
        String regex = "note='cfg_TaskInfo:  TaskId= (.*) CustomerId= (.*) SysTaskNameId= 11232 TaskName= (.*) FileName= (.*) FileType(.*)Directorys = (.*)'";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        int count = 0;
        if(m.find()){
            count = m.groupCount();
        }
        for(int i=1;i<=count;i++){
            System.out.println(m.group(i));
        }

    }

    @Test
    public void test4(){
        String str = "在接收数据(Fut_WRStatByOption)时出现对象名 'JYDB_ZSZQ.dbo.Fut_WRStatByOption' 无效。错误(step=4)！";
        String regex = "在接收数据(.*)时出现对象名(.*)无效";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if(m.find()){
            System.out.println("匹配");
        }
        System.out.println(m.group(1));
        System.out.println(m.group(2));
    }



    @Test
    public void test5(){
        String str = "InterFaceTaskInfo{TaskId=4771, CustomerId=10398, SysTaskNameId=11350, TaskName='金手指估值_金手指估值_开放式基金净值信息表2.5', FileName='PAR_OPENFUND_INFO{yyyymmdd}', FileType=DBF, IfCompre='Y', IfFirstRowByName='Y', IfDoThreeDayTask='N', IfNoCreateEmptyFile='N', IfMonitDataNum='N', MinDataNum=null, IfMonitFileSize='N', MinFileSize=null, MaxFileSize=null, IfIssued='N', IfUse='Y', IfSpecifiedFileName='N', SpecifiedSql='null', Charset='GBK', IfCreateToTempDir='N', ifCapitalFileType=false, dbInfoID=38, STEP_EXECUTION_ID=429434, JOB_EXECUTION_ID=187401, JOB_EXECUTION_PARAMS='{schedulingID=289, random=0.4952367218026874, performer=289, run.id=1}', Performer=289, PerformerType='Scheduling', FilePach='/opt/gildata/gdaas/pivotal/share/InterFaceDBData', tempFilePath='/opt/gildata/gdaas/pivotal/share/tempInterFaceDBData', Directorys=[银河基金金手指估值], TaskSqls.size=1, dbfFields.size=13, emailLists=[data_kf_db@gildata.com, productyw@gildata.com]}";
        String regex = "TaskId=(.*), CustomerId=(.*), SysTaskNameId=(.*), TaskName=(.*), FileName=(.*), FileType(.*)JOB_EXECUTION_ID=(.*), JOB_EXECUTION_PARAMS(.*)schedulingID=(.*), random(.*)Directorys=(.*), TaskSqls(.*)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        int count = 0;
        if(m.find()){
            count = m.groupCount();
        }
        for(int i=1;i<=count;i++){
            System.out.println(m.group(i));
        }

    }


    @Test
    public void test6(){
        Map<String,String> map = new HashMap<>();
        String str = "InterFaceTaskInfo{TaskId=4601, CustomerId=10206, SysTaskNameId=11138, TaskName='新华基金_恒生O32_停复牌信息表', FileName='csitfp4fund{yyyymmdd}001_2300', FileType=TXT, IfCompre='Y', IfFirstRowByName='N', IfDoThreeDayTask='N', IfNoCreateEmptyFile='Y', IfMonitDataNum='N', MinDataNum=null, IfMonitFileSize='N', MinFileSize=null, MaxFileSize=null, IfIssued='N', IfUse='Y', IfSpecifiedFileName='Y', SpecifiedSql='DECLARE @EndDate DATETIME SET @EndDate = '#EndDate#' \n" +
                "SELECT CONVERT(DATETIME,MIN(TradingDate)) FROM JYDB..QT_TradingDayNew WHERE TradingDate>@EndDate AND IfTradingDay = 1 AND SecuMarket = 83', Charset='GBK', IfCreateToTempDir='N', ifCapitalFileType=false, dbInfoID=40, STEP_EXECUTION_ID=448721, JOB_EXECUTION_ID=199037, JOB_EXECUTION_PARAMS='{schedulingID=222, random=0.8823005519259892, performer=222, run.id=1}', Performer=222, PerformerType='Scheduling', FilePach='/opt/gildata/gdaas/pivotal/share/InterFaceDBData', tempFilePath='/opt/gildata/gdaas/pivotal/share/tempInterFaceDBData', Directorys=[新华基金O32], TaskSqls.size=1, dbfFields.size=0, emailLists=[data_kf_db@gildata.com, productyw@gildata.com]}";
        str =str.replaceAll("\n", "");
        String regex = "TaskId=(.*), CustomerId=(.*), SysTaskNameId=(.*), TaskName=(.*), FileName=(.*), FileType(.*)STEP_EXECUTION_ID=(.*), JOB_EXECUTION_ID=(.*), JOB_EXECUTION_PARAMS=(.*)Directorys=(.*), TaskSqls(.*)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        int count = 0;
        if(m.find()){
            map.put("TaskId",m.group(1));
            map.put("CustomerId",m.group(2));
            if (m.group(4).length() > 3) {
                map.put("TaskName", m.group(4).substring(1, m.group(4).length() - 1));
            } else {
                map.put("TaskName", "");
            }
            if(m.group(5).length()>3){
                map.put("FileName",m.group(5).substring(1,m.group(5).length()-1));
            }else{
                map.put("FileName","");
            }
            map.put("STEP_EXECUTION_ID",m.group(7));
            map.put("JOB_EXECUTION_ID",m.group(8));
            if(m.group(10).length()>3){
                map.put("Directorys",m.group(10).substring(1,m.group(10).length()-1));
            }else{
                map.put("Directorys","");
            }
        }
        System.out.println(map);
    }


    @Test
    public void test7(){
        Map<String,String> map = new HashMap<>();
        String str = "cfg_TaskInfo:  TaskId= 3570 CustomerId= 10323 SysTaskNameId= 10850 TaskName= 中邮人寿_恒生估值_开放式基金净值 FileName= PAR_OPENFUND_INFO{yyyymmdd} FileType= DBF IfCompre= Y IfFirstRowByName= Y IfDoThreeDayTask= N IfNoCreateEmptyFile= N IfMonitDataNum= N MinDataNum= null IfMonitFileSize= N MinFileSize= null MaxFileSize= null IfIssued= N IfUse= Y TaskSqls=[cfg_Task_Sql:  Id= 12384 TaskID= 3570 SerialNum= null SerialName= null SpiltChar= , Sqlstr= DECLARE @EndDate DATETIME  SET @EndDate = #EndDate#   EXEC  dbo.HSJY_ZYRSBX_HSGZ_PAR_OPENFUND_INFO @EndDate] Directorys = 中邮人寿恒生估值";
        str = str.replaceAll("\n", "");
        String regex = "TaskId= (.*) CustomerId= (.*) SysTaskNameId= (.*) TaskName= (.*) FileName= (.*) FileType=(.*)Directorys = (.*)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if(m.find()){
            for(int i=1;i<=m.groupCount();i++){
                System.out.println(m.group(i));
            }
        }
    }


    @Test
    public void test8(){
        String str = "{schedulingID=296, random=0.6235225428711438, performer=296, run.id=1}";
        String regex = "schedulingID=(.*), random";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if(m.find()){
            for(int i=1;i<=m.groupCount();i++){
                System.out.println(m.group(i));
            }
        }
    }

    @Test
    public void test9(){
        String strNum = "";
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher(strNum);
        if(matcher.matches()){
            System.out.println("all num");
        }else{
            System.out.println("contains character");
        }
    }

    @Test
    public void test10(){
        //2018-06-13 18:30:47.0
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date = null;
        try {
            date = sdf.parse("2018-06-13 18:30:47.0");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}

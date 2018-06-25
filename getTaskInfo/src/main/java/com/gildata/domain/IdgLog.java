package com.gildata.domain;

/**
 * Created by LiChao on 2018/6/14
 */
public class IdgLog {

    private Long id;
    private Long taskId;
    private Long customerId;
    private Long taskLogId;
    private Long scheduleingId;
    private String SchedulingExpression;
    private String executionDate;
    private String startDate;
    private String endDate;
    private String status;
    private String totalTime;
    private String XDJobID;
    private String MQMsgCreatedDate;
    private String createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getTaskLogId() {
        return taskLogId;
    }

    public void setTaskLogId(Long taskLogId) {
        this.taskLogId = taskLogId;
    }

    public Long getScheduleingId() {
        return scheduleingId;
    }

    public void setScheduleingId(Long scheduleingId) {
        this.scheduleingId = scheduleingId;
    }

    public String getSchedulingExpression() {
        return SchedulingExpression;
    }

    public void setSchedulingExpression(String schedulingExpression) {
        SchedulingExpression = schedulingExpression;
    }

    public String getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(String executionDate) {
        this.executionDate = executionDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getXDJobID() {
        return XDJobID;
    }

    public void setXDJobID(String XDJobID) {
        this.XDJobID = XDJobID;
    }

    public String getMQMsgCreatedDate() {
        return MQMsgCreatedDate;
    }

    public void setMQMsgCreatedDate(String MQMsgCreatedDate) {
        this.MQMsgCreatedDate = MQMsgCreatedDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "IdgLog{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", customerId=" + customerId +
                ", taskLogId=" + taskLogId +
                ", scheduleingId=" + scheduleingId +
                ", SchedulingExpression='" + SchedulingExpression + '\'' +
                ", executionDate='" + executionDate + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status='" + status + '\'' +
                ", totalTime=" + totalTime +
                ", XDJobID=" + XDJobID +
                ", MQMsgCreatedDate='" + MQMsgCreatedDate + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}

package com.gildata.domain;

/**
 * Created by LiChao on 2018/6/11
 */
public class TaskLog {

    private Long id;
    private Long taskID;
    private String startDate;
    private String endDate;
    private String status;
    private String totalTime;
    private String XDJobID;
    private String performerType;
    private String performer;
    private String mqCreatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskID() {
        return taskID;
    }

    public void setTaskID(Long taskID) {
        this.taskID = taskID;
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

    public String getPerformerType() {
        return performerType;
    }

    public void setPerformerType(String performerType) {
        this.performerType = performerType;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getMqCreatedDate() {
        return mqCreatedDate;
    }

    public void setMqCreatedDate(String mqCreatedDate) {
        this.mqCreatedDate = mqCreatedDate;
    }

    @Override
    public String toString() {
        return "TaskLog{" +
                "id=" + id +
                ", taskID=" + taskID +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status='" + status + '\'' +
                ", totalTime='" + totalTime + '\'' +
                ", XDJobID='" + XDJobID + '\'' +
                ", performerType='" + performerType + '\'' +
                ", performer='" + performer + '\'' +
                ", mqCreatedDate='" + mqCreatedDate + '\'' +
                '}';
    }
}

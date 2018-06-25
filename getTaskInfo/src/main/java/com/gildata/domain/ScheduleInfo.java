package com.gildata.domain;

import java.util.List;

/**
 * Created by LiChao on 2018/6/14
 */
public class ScheduleInfo {

    private Long taskId;
    private Long customerId;
    private Long schedulingID;
    private String schedulingExpression;
    private List<String> cronDates;

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

    public Long getSchedulingID() {
        return schedulingID;
    }

    public void setSchedulingID(Long schedulingID) {
        this.schedulingID = schedulingID;
    }

    public String getSchedulingExpression() {
        return schedulingExpression;
    }

    public void setSchedulingExpression(String schedulingExpression) {
        this.schedulingExpression = schedulingExpression;
    }

    public List<String> getCronDates() {
        return cronDates;
    }

    public void setCronDates(List<String> cronDates) {
        this.cronDates = cronDates;
    }

    @Override
    public String toString() {
        return "ScheduleInfo{" +
                "taskId=" + taskId +
                ", customerId=" + customerId +
                ", schedulingID=" + schedulingID +
                ", schedulingExpression='" + schedulingExpression + '\'' +
                ", cronDates=" + cronDates +
                '}';
    }
}

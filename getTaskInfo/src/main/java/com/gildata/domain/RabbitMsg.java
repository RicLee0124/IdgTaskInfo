package com.gildata.domain;


/**
 * Created by LiChao on 2018/6/12
 */
public class RabbitMsg {

    private Long taskId;
    private String JOB_EXECUTION_ID;
    private String createdDate;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getJOB_EXECUTION_ID() {
        return JOB_EXECUTION_ID;
    }

    public void setJOB_EXECUTION_ID(String JOB_EXECUTION_ID) {
        this.JOB_EXECUTION_ID = JOB_EXECUTION_ID;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "RabbitMsg{" +
                "taskId=" + taskId +
                ", JOB_EXECUTION_ID='" + JOB_EXECUTION_ID + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}

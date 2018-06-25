package com.gildata.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


@Configuration
public class ScheduleConfig {

  private final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);

  @Bean
  public SchedulerFactoryBean strategyScheduler() {
    SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
    schedulerFactory.setSchedulerName("strategyScheduler");

    schedulerFactory.setAutoStartup(false);
    return schedulerFactory;
  }

}

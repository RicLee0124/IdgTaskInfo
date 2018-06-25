package com.gildata;

import com.gildata.service.IdgLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * Created by LiChao on 2018/6/11
 */
@Component
public class StartUpRunner implements CommandLineRunner{

    @Autowired
    private IdgLogService idgLogService;


    @Override
    public void run(String... strings) throws Exception {
        //初始化数据库
        idgLogService.init();
    }
}

package moe.cnkirito.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import zipkin.server.EnableZipkinServer;
import zipkin.storage.mysql.MySQLStorage;

import javax.sql.DataSource;

/**
 * Created by xujingfeng on 2017/11/7.
 */
@SpringBootApplication
@EnableZipkinServer
public class ZipkinServerApp {

    @Bean
    public MySQLStorage mySQLStorage(DataSource datasource) {
        return MySQLStorage.builder().datasource(datasource).executor(Runnable::run).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ZipkinServerApp.class, args);
    }

}

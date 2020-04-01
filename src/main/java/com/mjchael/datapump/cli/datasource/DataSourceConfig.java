package com.mjchael.datapump.cli.datasource;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Data
@Configuration
@ConfigurationProperties(prefix="db")
public class DataSourceConfig {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
    private DataSourceBuilder dataSourceBuilder;

    private String connect;

    @Bean
    public DataSource getDataSource() {
        dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("oracle.jdbc.OracleDriver");
        dataSourceBuilder.url("jdbc:oracle:thin:@/" + getTNS());
        dataSourceBuilder.username(getUsername());
        dataSourceBuilder.password(getPassword());
        return dataSourceBuilder.build();
    }

    public String getUsername(){
        return getConnect().split("[/]")[0];
    }

    public String getPassword(){
        return getConnect().split("[@]")[0].split("[/]")[1];
    }

    public String getTNS(){
        return getConnect().split("[@]")[1];
    }
}
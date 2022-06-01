package org.zzssg.smarthome.restserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.zzssg.smarthome.restserver.configs.InfluxDbConfig;

/*
 Example usage:
 http://localhost:9000/temp-humid?id=123&t=23.1&h=55.3
 */
@Slf4j
@SpringBootApplication
public class RestserverApplication {


	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(RestserverApplication.class, args);
		InfluxDbConfig influxDbConfig = ctx.getBean(InfluxDbConfig.class);
		System.out.println("influxDbConfig.getHost() => " + influxDbConfig.getHost());
	}

}

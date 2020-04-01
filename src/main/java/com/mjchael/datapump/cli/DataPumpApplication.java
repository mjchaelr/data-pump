package com.mjchael.datapump.cli;

import com.mjchael.datapump.cli.strategy.Context;
import com.mjchael.datapump.core.Core;
import org.hibernate.AssertionFailure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackageClasses = Core.class)
@EnableJpaRepositories(basePackageClasses = Core.class)
@ComponentScan(basePackageClasses = {Core.class, Cli.class})
@SpringBootApplication
public class DataPumpApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(CommandLineRunner.class);

	@Autowired
	public Context context;

	public static void main(String[] args) {
		if (hasValidArguments(args)) {
			SpringApplication.run(DataPumpApplication.class, args);
		}
	}

	public static boolean hasValidArguments(String ... args){
		if (args.length == 0){
			logger.error("Parameter has not been specified");
			return false;
		}
		if (!args[0].regionMatches(0 , "--db.connect", 0, 12)){
			logger.error("Invalid Connection String - Database connection format : --db.connect=hr/hr@xe");
			logger.error(new StringBuilder().append("Connection arguments: ").append(args[0]).toString());
			return false;
		}
		return true;
	}

	@Override
	public void run(String... args) {
		context.executeStrategy(args);
	}


}

package com.vsp.payment.sftp.example.test.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.vsp.batch.test.config.TestVspSpringBatchConfigurer;
import com.vsp.payment.sftp.example.config.JobConfiguration;

@Configuration
@EnableBatchProcessing

@Import(value={JobConfiguration.class, TestVspSpringBatchConfigurer.class})
@ComponentScan(basePackages = { "com.vsp.payment.sftp.example.test" })
public class TestConfiguration {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
		return new PropertySourcesPlaceholderConfigurer();
	}
	
}

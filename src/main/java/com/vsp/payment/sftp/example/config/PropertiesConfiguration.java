package com.vsp.payment.sftp.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vsp.batch.il.util.PreferencesFactoryBean;

@Configuration
public class PropertiesConfiguration {

	@Bean
	public PreferencesFactoryBean emailProperties() {
	PreferencesFactoryBean preferencesFactory = new PreferencesFactoryBean();
	preferencesFactory.setDomain("batchemail");
	preferencesFactory.setApplication("SendFilesToSAP");
	return preferencesFactory;
	}
	
	@Bean
	public PreferencesFactoryBean ftpProperties() {
	PreferencesFactoryBean preferencesFactory = new PreferencesFactoryBean();
	preferencesFactory.setDomain("ftp");
	preferencesFactory.setApplication("SendFilesToSAP");
	return preferencesFactory;
	}
	
	@Bean
	public PreferencesFactoryBean batchProperties() {
	PreferencesFactoryBean preferencesFactory = new PreferencesFactoryBean();
	preferencesFactory.setDomain("batch");
	preferencesFactory.setApplication("SendFilesToSAP");
	return preferencesFactory;
	}

}

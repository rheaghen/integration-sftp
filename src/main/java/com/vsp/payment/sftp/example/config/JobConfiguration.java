package com.vsp.payment.sftp.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;

import com.vsp.batch.failurenotification.BatchMonitoringNotifier;
import com.vsp.batch.failurenotification.EmailMonitoringNotifierImpl;
import com.vsp.batch.failurenotification.MonitoringExecutionListener;

@Configuration
@PropertySources(
	@PropertySource(value= {
		"classpath:/com/vsp/SendFilesToSAP/resources/batch.properties",
		"classpath:/com/vsp/SendFilesToSAP/resources/ftp.properties",
		"classpath:/com/vsp/SendFilesToSAP/resources/batchemail.properties"
	})
)
@EnableIntegration
@Import(PropertiesConfiguration.class)
@ImportResource("sftp-integration.xml")
public class JobConfiguration  {
	@Autowired JobBuilderFactory jobFactory;
	@Autowired StepBuilderFactory stepFactory;

	@Value("${ftp.payment.dev.remoteDir}")
	String remoteDir;
	

	@Bean(name="SendFilesToSAP")
	public Job instantiateJob() {
		return jobFactory
			.get("SendFilesToSAP")
			.listener(monitoringJobListener())
			.incrementer(new RunIdIncrementer())
			.flow(ftpLsStep())
			.end()
			.build();
	}
	
	@Bean
	public Step ftpLsStep(){ return stepFactory.get("ViewDirectory").tasklet(viewDirectoryTasklet(null,null)).build();}
	
	@Bean
	public Tasklet viewDirectoryTasklet(
			@Qualifier("input" ) MessageChannel  input,
			@Qualifier("output") PollableChannel output) {
		return new Tasklet() {
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				input.send(new GenericMessage<String>(remoteDir));
				System.out.println(output.receive(10000).getPayload().toString());
				return RepeatStatus.FINISHED;
			}
		};
	}
	
	@Bean
	public BatchMonitoringNotifier monitoringNotifier(){ return new EmailMonitoringNotifierImpl(); }

	@Bean
	public JobExecutionListener monitoringJobListener(){
		MonitoringExecutionListener monitoringJobListener = new MonitoringExecutionListener();
		monitoringJobListener.setBatchMonitoringNotifier(monitoringNotifier());
		return monitoringJobListener;
	}
}

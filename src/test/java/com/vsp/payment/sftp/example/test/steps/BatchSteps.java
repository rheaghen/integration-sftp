package com.vsp.payment.sftp.example.test.steps;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchSteps {

	private JobExecution jobExecution;
	private JobLauncherTestUtils jobLauncherTestUtils;
	
	@Autowired
	public BatchSteps(JobLauncherTestUtils jobLauncherTestUtils) {
		this.jobLauncherTestUtils = jobLauncherTestUtils;
	}

	@BeforeScenario
	public void setUp(){
		this.jobExecution = null;
	}
	
	@Given("the input data is loaded")
	public void givenTheInputDataIsLoaded() {
		//TODO: Load input data here!
	}

	@When("the batch job $jobName is run")
	public void whenTheBatchJobIsRun(String jobName) throws Exception {
		this.jobExecution = jobLauncherTestUtils.launchJob(getJobParameters());
	}
		
	JobParameters getJobParameters(){
		Map<String,JobParameter> testJobParametersMap = new HashMap<>();
		JobParameters testJobParameters = new JobParameters(testJobParametersMap);
		return new RunIdIncrementer().getNext(testJobParameters);
	}

	@Then("the job execution should be $batchStatus")
	public void thenTheJobExecutionShouldBe(String batchStatus) {
		String actualStatus = jobExecution.getStatus().toString();
		Assert.assertEquals(batchStatus, actualStatus);
		
	}

	@Then("the output data should match the expected data")
	public void thenTheOutputDataShouldMatchTheExpectedData() {
		//TODO: Assert output here!
	}
	
}

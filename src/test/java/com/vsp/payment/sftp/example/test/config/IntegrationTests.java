package com.vsp.payment.sftp.example.test.config;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public class IntegrationTests extends JUnitStories {

	@Autowired
	ApplicationContext context;

	@Override
	public Configuration configuration() {
		return new MostUsefulConfiguration()
				.useStoryLoader(new LoadFromClasspath(this.getClass()))
				.useStoryReporterBuilder(
						new StoryReporterBuilder()
								.withDefaultFormats()
								.withFormats(Format.CONSOLE, Format.HTML,
										Format.STATS).withFailureTrace(true))
				.usePendingStepStrategy(new FailingUponPendingStep());
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		Assert.assertNotNull(context);

		return new SpringStepsFactory(configuration(), context);
	}

	protected List<String> storyPaths() {
		return new StoryFinder().findPaths(
				"src/test/resources",
				Collections.singletonList("**/*.story"),
				Collections.singletonList(""), "");
	}
	
	@AfterClass
	public static void afterClass() {
		if (Boolean.getBoolean("jbehave.showresults") && Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			if (desktop.isSupported(Desktop.Action.BROWSE)) {
				try {
					File reports = new File("target/jbehave/view/reports.html");
					if (reports.exists()) {
						desktop.browse(reports.toURI());
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}
}

package br.com.rperatello.bankcoreapi.config;

import java.text.ParseException;
import java.util.Properties;
import java.util.logging.Logger;

import org.quartz.*;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import br.com.rperatello.bankcoreapi.jobs.AccountTransactionNotificationJob;
import br.com.rperatello.bankcoreapi.jobs.AutowiringSpringBeanJobFactory;


@Configuration
public class QuartzConfig {
	
	@Autowired
    private Environment environment;
	
	@Autowired
	ApplicationContext applicationContext;
	
	private Logger logger = Logger.getLogger(QuartzConfig.class.getName());
	
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws Exception {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.afterPropertiesSet();
        schedulerFactoryBean.setQuartzProperties(getQuartzProperties());
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
        schedulerFactoryBean.setJobFactory(jobFactory());
        return schedulerFactoryBean;
    }
    
    @Bean
    public JobFactory jobFactory() {
      AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
      jobFactory.setApplicationContext(applicationContext);
      return jobFactory;
    }
    
    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) throws Exception {
    	logger.info("QuartzConfig | start scheduler");
    	var scheduler = schedulerFactoryBean.getScheduler();    	
    	JobKey jobKey = new JobKey("accountTransactionNotificationJob", "accountTransactionNotificationJob-group");
    	if (!scheduler.checkExists(jobKey))
	    	scheduler.scheduleJob(jobDetail1(), trigger1(jobDetail1(), "trigger1"));
    	scheduler.start();
		return scheduler;
    }

    @Bean
    public JobDetail jobDetail1() {
        var job = createJobDetail(AccountTransactionNotificationJob.class, "accountTransactionNotificationJob");
        return job.getObject();
    }
    
    @Bean
    public Trigger trigger1(JobDetail job, String name) throws ParseException {
    	logger.info("QuartzConfig | adds trigger1");
    	var trigger = createTriggerWithCron(job, name, "0/30 * * ? * *");
        return trigger.getObject();
    }

    private JobDetailFactoryBean createJobDetail(Class<? extends Job> jobClass, String name) {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(jobClass);
        jobDetailFactory.setDurability(true);
        jobDetailFactory.setName(name);
        jobDetailFactory.setGroup(name + "-group");
        jobDetailFactory.afterPropertiesSet();
        return jobDetailFactory;
    }

    @SuppressWarnings("unused")
	private SimpleTriggerFactoryBean createTrigger(JobDetail job, String name, long interval) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(job);
        trigger.setStartDelay(0L);
        trigger.setRepeatInterval(interval);
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        trigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
        trigger.setName(name);
        trigger.setGroup(name + "-group");
        trigger.afterPropertiesSet();
        return trigger;
    }
    
    private CronTriggerFactoryBean createTriggerWithCron(JobDetail job, String name, String cron) throws ParseException {
    	CronTriggerFactoryBean  trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(job);
        trigger.setStartDelay(0L);
        trigger.setCronExpression(cron);
        trigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
        trigger.setName(name);
        trigger.setGroup(name + "-group");
        trigger.afterPropertiesSet();
        return trigger;
    }    

    
    public Properties getQuartzProperties() {
        ClassPathResource resource = new ClassPathResource("application.yml");
        YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
        yamlFactory.setResources(resource);
        Properties yamlProperties = yamlFactory.getObject();
        Properties quartzProperties = new Properties();
        for (String propertyName : yamlProperties.stringPropertyNames()) {
            if (propertyName.startsWith("spring.quartz.properties.")) {
                String quartzPropertyName = propertyName.substring("spring.quartz.properties.".length());
                String quartzPropertyValue = environment.getProperty(propertyName);
                quartzProperties.put(quartzPropertyName, quartzPropertyValue);
            }
        }
        return quartzProperties;
    }  

}

   
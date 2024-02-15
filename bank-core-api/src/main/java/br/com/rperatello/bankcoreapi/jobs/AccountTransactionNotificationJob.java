package br.com.rperatello.bankcoreapi.jobs;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.rperatello.bankcoreapi.services.INotificationService;

@DisallowConcurrentExecution
public class AccountTransactionNotificationJob implements Job {
	
	@Autowired
	INotificationService notificationService;
	
	private Logger logger = Logger.getLogger(AccountTransactionNotificationJob.class.getName());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {    	
    	
    	try { 
    		logger.info("AccountTransactionNotificationJob started ...");
    		var result = notificationService.processCustomerTransactionNotificationNotSent().get();
		} catch (InterruptedException e) {
			logger.log(Level.SEVERE, String.format("NotificationService | Error: %s", e.getMessage()));
			e.printStackTrace();
		} catch (ExecutionException e) {
			logger.log(Level.SEVERE, String.format("NotificationService | Error: %s", e.getMessage()));
		} catch (Exception e) {
	    	logger.log(Level.SEVERE, String.format("NotificationService | Error: %s", e.getMessage()));
	    } finally {
	    	logger.info("AccountTransactionNotificationJob finished ..."); 
	    }
    	
    }
    
}
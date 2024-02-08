package br.com.rperatello.bankcoreapi.model;

public class CustomerTransactionNotificationResponse {
	
	private boolean messageSent;

	public CustomerTransactionNotificationResponse() {}

	public boolean isMessageSent() {
		return messageSent;
	}

	public void setMessageSent(boolean messageSent) {
		this.messageSent = messageSent;
	}
	
}

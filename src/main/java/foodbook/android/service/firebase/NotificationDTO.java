package foodbook.android.service.firebase;

import java.io.Serializable;

public class NotificationDTO implements Serializable{
	
	private String notificationType;

	
	/**
	 * used for info notifications wihtout any action
	 */
	private String infoMessage;
	private long inviteReservationId;
	private String title;
	
	
	public NotificationDTO() {
		// TODO Auto-generated constructor stub
	}


	public String getNotificationType() {
		return notificationType;
	}


	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}



	public String getInfoMessage() {
		return infoMessage;
	}
	
	


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public void setInfoMessage(String infoMessage) {
		this.infoMessage = infoMessage;
	}


	public long getInviteReservationId() {
		return inviteReservationId;
	}


	public void setInviteReservationId(long inviteReservationId) {
		this.inviteReservationId = inviteReservationId;
	}
	
	
	
}

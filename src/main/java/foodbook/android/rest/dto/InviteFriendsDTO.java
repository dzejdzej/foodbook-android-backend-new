package foodbook.android.rest.dto;

import java.util.List;

public class InviteFriendsDTO {

	 private List<String> invitedFriends;
	    private long reservationId;

	    public InviteFriendsDTO() {

	    }

	    public List<String> getInvitedFriends() {
	        return invitedFriends;
	    }

	    public void setInvitedFriends(List<String> invitedFriends) {
	        this.invitedFriends = invitedFriends;
	    }

	    public long getReservationId() {
	        return reservationId;
	    }

	    public void setReservationId(long reservationId) {
	        this.reservationId = reservationId;
	    }
	
	
}

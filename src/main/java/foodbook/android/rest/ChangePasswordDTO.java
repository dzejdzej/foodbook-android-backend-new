package foodbook.android.rest;

public class ChangePasswordDTO {


    private long userId;
    private String newPassword;

    public ChangePasswordDTO() {

    }

    public ChangePasswordDTO(long userId, String newPassword) {
        this.userId = userId;
        this.newPassword = newPassword;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
	
}

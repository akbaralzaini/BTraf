package project.akbaralzaini.btraf.models.ticket;

import com.google.gson.annotations.SerializedName;

public class MessageTicket {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

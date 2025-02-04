package fim.project.warehousingsystemmobile.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryLastIdResponse {
    @SerializedName("responses")
    @Expose
    private Boolean responses;

    @SerializedName("lastIdHistory")
    @Expose
    private String lastIdHistory;

    public Boolean getResponses() {
        return responses;
    }

    public void setResponses(Boolean responses) {
        this.responses = responses;
    }

    public String getLastIdHistory() {
        return lastIdHistory;
    }

    public void setLastIdHistory(String lastIdHistory) {
        this.lastIdHistory = lastIdHistory;
    }
}

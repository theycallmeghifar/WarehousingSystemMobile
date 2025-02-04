package fim.project.warehousingsystemmobile.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryListResponse {

    @SerializedName("responses")
    @Expose
    private Boolean responses;

    @SerializedName("result")
    @Expose
    private List<HistoryDataResponse> result;

    public Boolean getResponses() {
        return responses;
    }

    public void setResponses(Boolean responses) {
        this.responses = responses;
    }

    public List<HistoryDataResponse> getResult() {
        return result;
    }

    public void setResult(List<HistoryDataResponse> result) {
        this.result = result;
    }
}

package fim.project.warehousingsystemmobile.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BarangListResponse {

    @SerializedName("responses")
    @Expose
    private Boolean responses;

    @SerializedName("result")
    @Expose
    private List<BarangDataResponse> result;

    public Boolean getResponses() {
        return responses;
    }

    public void setResponses(Boolean responses) {
        this.responses = responses;
    }

    public List<BarangDataResponse> getResult() {
        return result;
    }

    public void setResult(List<BarangDataResponse> result) {
        this.result = result;
    }
}

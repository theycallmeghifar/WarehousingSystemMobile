package fim.project.warehousingsystemmobile.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailBarangKeluarListResponse {

    @SerializedName("responses")
    @Expose
    private Boolean responses;

    @SerializedName("result")
    @Expose
    private List<MainItemDataResponse> result;

    public Boolean getResponses() {
        return responses;
    }

    public void setResponses(Boolean responses) {
        this.responses = responses;
    }

    public List<MainItemDataResponse> getResult() {
        return result;
    }

    public void setResult(List<MainItemDataResponse> result) {
        this.result = result;
    }
}

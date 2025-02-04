package fim.project.warehousingsystemmobile.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailScanBarangKeluarResponse {

    @SerializedName("minQuantity")
    @Expose
    private String minQuantity;

    @SerializedName("responses")
    @Expose
    private Boolean responses;

    public String getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(String minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Boolean getResponses() {
        return responses;
    }

    public void setResponses(Boolean responses) {
        this.responses = responses;
    }
}

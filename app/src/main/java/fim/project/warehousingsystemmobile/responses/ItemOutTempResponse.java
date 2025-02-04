package fim.project.warehousingsystemmobile.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemOutTempResponse {

    @SerializedName("itemCode")
    @Expose
    private String itemCode;

    @SerializedName("responses")
    @Expose
    private Boolean responses;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Boolean getResponses() {
        return responses;
    }

    public void setResponses(Boolean responses) {
        this.responses = responses;
    }
}

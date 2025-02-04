package fim.project.warehousingsystemmobile.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainItemTempResponse {

    @SerializedName("mainItemCode")
    @Expose
    private String mainItemCode;

    @SerializedName("mainItemName")
    @Expose
    private String mainItemName;

    @SerializedName("responses")
    @Expose
    private Boolean responses;

    public String getMainItemCode() {
        return mainItemCode;
    }

    public void setMainItemCode(String mainItemCode) {
        this.mainItemCode = mainItemCode;
    }

    public String getMainItemName() {
        return mainItemName;
    }

    public void setMainItemName(String mainItemName) {
        this.mainItemName = mainItemName;
    }

    public Boolean getResponses() {
        return responses;
    }

    public void setResponses(Boolean responses) {
        this.responses = responses;
    }
}

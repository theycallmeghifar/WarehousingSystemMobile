package fim.project.warehousingsystemmobile.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaletTempResponse {

    @SerializedName("idPalet")
    @Expose
    private String idPalet;

    @SerializedName("responses")
    @Expose
    private Boolean responses;

    public String getIdPalet() {
        return idPalet;
    }

    public void setIdPalet(String idPalet) {
        this.idPalet = idPalet;
    }

    public Boolean getResponses() {
        return responses;
    }

    public void setResponses(Boolean responses) {
        this.responses = responses;
    }
}

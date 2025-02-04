package fim.project.warehousingsystemmobile.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class BarangDataResponse {

    @SerializedName("position")
    @Expose
    private String position;

    @SerializedName("itemCode")
    @Expose
    private String itemCode;

    @SerializedName("itemName")
    @Expose
    private String itemName;

    @SerializedName("jumlahItem")
    @Expose
    private String jumlahItem;

    @SerializedName("netQuantity")
    @Expose
    private String netQuantity;

    @SerializedName("deskripsiPalet")
    @Expose
    private String deskripsiPalet;

    @SerializedName("deskripsiRak")
    @Expose
    private String deskripsiRak;

    @SerializedName("deskripsiLemari")
    @Expose
    private String deskripsiLemari;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getJumlahItem() {
        return jumlahItem;
    }

    public void setJumlahItem(String jumlahItem) {
        this.jumlahItem = jumlahItem;
    }

    public String getNetQuantity() {
        return netQuantity;
    }

    public void setNetQuantity(String netQuantity) {
        this.netQuantity = netQuantity;
    }

    public String getDeskripsiPalet() {
        return deskripsiPalet;
    }

    public void setDeskripsiPalet(String deskripsiPalet) {
        this.deskripsiPalet = deskripsiPalet;
    }

    public String getDeskripsiRak() {
        return deskripsiRak;
    }

    public void setDeskripsiRak(String deskripsiRak) {
        this.deskripsiRak = deskripsiRak;
    }

    public String getDeskripsiLemari() {
        return deskripsiLemari;
    }

    public void setDeskripsiLemari(String deskripsiLemari) {
        this.deskripsiLemari = deskripsiLemari;
    }
}

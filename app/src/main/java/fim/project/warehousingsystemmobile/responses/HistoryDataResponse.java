package fim.project.warehousingsystemmobile.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class HistoryDataResponse {

    @SerializedName("purchaseOrder")
    @Expose
    private String purchaseOrder;

    @SerializedName("deliveryNote")
    @Expose
    private String deliveryNote;

    @SerializedName("deskripsiPalet")
    @Expose
    private String deskripsiPalet;

    @SerializedName("deskripsiRak")
    @Expose
    private String deskripsiRak;

    @SerializedName("deskripsiLemari")
    @Expose
    private String deskripsiLemari;

    @SerializedName("mainItemCode")
    @Expose
    private String mainItemCode;

    @SerializedName("mainItemName")
    @Expose
    private String mainItemName;

    @SerializedName("itemCode")
    @Expose
    private String itemCode;

    @SerializedName("itemName")
    @Expose
    private String itemName;

    @SerializedName("jumlahMasuk")
    @Expose
    private String jumlahMasuk;

    @SerializedName("jumlahKeluar")
    @Expose
    private String jumlahKeluar;

    @SerializedName("tanggal")
    @Expose
    private String tanggal;

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
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

    public String getJumlahMasuk() {
        return jumlahMasuk;
    }

    public void setJumlahMasuk(String jumlahMasuk) {
        this.jumlahMasuk = jumlahMasuk;
    }

    public String getJumlahKeluar() {
        return jumlahKeluar;
    }

    public void setJumlahKeluar(String jumlahKeluar) {
        this.jumlahKeluar = jumlahKeluar;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

}

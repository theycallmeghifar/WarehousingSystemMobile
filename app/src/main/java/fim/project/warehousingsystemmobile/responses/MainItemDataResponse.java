package fim.project.warehousingsystemmobile.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class MainItemDataResponse {

    @SerializedName("mainItemCode")
    @Expose
    private String mainItemCode;

    @SerializedName("itemCode")
    @Expose
    private String itemCode;

    @SerializedName("idPalet")
    @Expose
    private String idPalet;

    @SerializedName("idBarang")
    @Expose
    private String idBarang;

    @SerializedName("itemName")
    @Expose
    private String itemName;

    @SerializedName("jumlahItem")
    @Expose
    private String jumlahItem;

    @SerializedName("deskripsiPalet")
    @Expose
    private String deskripsiPalet;

    @SerializedName("deskripsiRak")
    @Expose
    private String deskripsiRak;

    @SerializedName("deskripsiLemari")
    @Expose
    private String deskripsiLemari;

    @SerializedName("jumlahInput")
    @Expose
    private String jumlahInput;

    @SerializedName("maxBarang")
    @Expose
    private String maxBarang;

    @SerializedName("ipAddress")
    @Expose
    private String ipAddress;

    @SerializedName("gpio1")
    @Expose
    private String gpio1;

    @SerializedName("gpio2")
    @Expose
    private String gpio2;

    @SerializedName("gpio3")
    @Expose
    private String gpio3;

    @SerializedName("gpioStatus")
    @Expose
    private String gpioStatus;

    @SerializedName("status")
    @Expose
    private String status;

    public String getMainItemCode() {
        return mainItemCode;
    }

    public void setMainItemCode(String mainItemCode) {
        this.mainItemCode = mainItemCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getIdPalet() {
        return idPalet;
    }

    public void setIdPalet(String idPalet) {
        this.idPalet = idPalet;
    }

    public String getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(String idBarang) {
        this.idBarang = idBarang;
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

    public String getJumlahInput() {
        return jumlahInput;
    }

    public void setJumlahInput(String jumlahInput) {
        this.jumlahInput = jumlahInput;
    }

    public String getMaxBarang() {
        return maxBarang;
    }

    public void setMaxBarang(String maxBarang) {
        this.maxBarang = maxBarang;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getGpio1() {
        return gpio1;
    }

    public void setGpio1(String gpio1) {
        this.gpio1 = gpio1;
    }

    public String getGpio2() {
        return gpio2;
    }

    public void setGpio2(String gpio2) {
        this.gpio2 = gpio2;
    }

    public String getGpio3() {
        return gpio3;
    }

    public void setGpio3(String gpio3) {
        this.gpio3 = gpio3;
    }

    public String getGpioStatus() {
        return gpioStatus;
    }

    public void setGpioStatus(String gpioStatus) {
        this.gpioStatus = gpioStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

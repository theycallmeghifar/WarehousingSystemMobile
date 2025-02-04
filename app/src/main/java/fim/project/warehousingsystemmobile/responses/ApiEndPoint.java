package fim.project.warehousingsystemmobile.responses;

import
        retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiEndPoint {

    @FormUrlEncoded
    @POST("read_item.php")
    Call<ItemInTempResponse> getItem(@Field("itemCode") String itemCode);

    @FormUrlEncoded
    @POST("update_gpio_on_palet_barang_masuk.php")
    Call<DetailBarangMasukDataResponse> updatePaletGpioOnBarangMasuk(@Field("itemCode") String itemCode);

    @FormUrlEncoded
    @POST("update_gpio_off_palet_barang_masuk.php")
    Call<StatusResponse> updatePaletGpioOffBarangMasuk(@Field("idBarang") String idBarang,
                                                       @Field("idPalet") String idPalet,
                                                       @Field("itemCode") String itemCode,
                                                       @Field("qtyInput") String qtyInput,
                                                       @Field("purchaseOrder") String purchaseOrder,
                                                       @Field("deliveryNote") String deliveryNote);

    @FormUrlEncoded
    @POST("complete_barang_masuk.php")
    Call<StatusResponse> completeBarangMasuk(@Field("itemCode") String itemCode);

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FormUrlEncoded
    @POST("read_main_item.php")
    Call<MainItemTempResponse> getMainItem(@Field("mainItemCode") String mainItemCode);

    @FormUrlEncoded
    @POST("get_main_item_barang_keluar.php")
    Call<DetailBarangKeluarListResponse> getMainItemBarangKeluar(@Field("mainItemCode") String mainItemCode,
                                                                 @Field("jumlahInput") String jumlahInput);

    @FormUrlEncoded
    @POST("update_gpio_on_palet_barang_keluar.php")
    Call<DetailScanBarangKeluarResponse> updatePaletGpioOnBarangKeluar(@Field("mainItemCode") String mainItemCode);

    @FormUrlEncoded
    @POST("update_gpio_off_palet_barang_keluar.php")
    Call<StatusResponse> updatePaletGpioOffBarangKeluar(@Field("idBarang") String idBarang,
                                                        @Field("mainItemCode") String mainItemCode,
                                                        @Field("itemCode") String itemCode,
                                                        @Field("idPalet") String idPalet,
                                                        @Field("qtyInput") String qtyInput);

    @FormUrlEncoded
    @POST("update_item_barang_keluar.php")
    Call<StatusResponse> updateItemBarangKeluar(@Field("mainItemCode") String mainItemCode,
                                                @Field("itemCode") String itemCode,
                                                @Field("qtyInput") String qtyInput);

    @FormUrlEncoded
    @POST("complete_barang_keluar.php")
    Call<StatusResponse> completeBarangKeluar(@Field("mainItemCode") String mainItemCode);

    @FormUrlEncoded
    @POST("cancel_barang_keluar.php")
    Call<StatusResponse> cancelBarangKeluar(@Field("itemCode") String itemCode,
                                            @Field("qtyInput") String qtyInput,
                                            @Field("lastIdHistory") String lastIdHistory);

    @FormUrlEncoded
    @POST("last_id_history.php")
    Call<HistoryLastIdResponse> lastIdHistory(@Field("mainItemCode") String mainItemCode,
                                             @Field("itemCode") String itemCode,
                                             @Field("jumlah") String jumlah);

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FormUrlEncoded
    @POST("read_palet_barang.php")
    Call<PaletTempResponse> getPalet(@Field("idPalet") String idPalet,
                                     @Field("idPaletCompare") String idPaletCompare);

    @FormUrlEncoded
    @POST("read_item_scan.php")
    Call<ItemOutTempResponse> getItem(@Field("itemCode") String itemCode,
                                     @Field("itemCodeCompare") String itemCodeCompare);

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FormUrlEncoded
    @POST("history_barang.php")
    Call<HistoryListResponse> getHistory(@Field("tanggalAwal") String tanggalAwal,
                                         @Field("tanggalAkhir") String tanggalAkhir);

    @FormUrlEncoded
    @POST("get_item.php")
    Call<BarangListResponse> getBarang(@Field("itemCode") String itemCode);

    @FormUrlEncoded
    @POST("ubah_jumlah_item.php")
    Call<StatusResponse> ubahJumlahItem(@Field("itemCode") String itemCode,
                                        @Field("jumlahSebelum") String jumlahSebelum,
                                        @Field("jumlahSesudah") String jumlahSesudah,
                                        @Field("username") String username);

    @FormUrlEncoded
    @POST("login.php")
    Call<StatusResponse> loginProcess(@Field("username") String username,
                                      @Field("password") String password);

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    
}

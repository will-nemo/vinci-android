package io.anua.vinci.interfaces;

import java.util.Map;

import io.anua.vinci.model.IEXResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IEXStockInterface {

    /**************************
     * GET Requests
     *************************/

    /* When called performs the GET Request for the default stock data
     *
     * @attribute getDefaultStocks
     * @method getDefaultStocks
     * @param {@link String} - string of symbols to search for
     * @returns {@link Call <{@link Map<{@link String}, {@link IEXResponse}>>}
     */
    @GET("stock/market/batch?types=quote,logo")
    Call<Map<String,IEXResponse>> getDefaultStocks(@Query("symbols") String symbols);
}

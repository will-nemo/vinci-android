package io.anua.vinci.builder;

import android.os.Bundle;

import java.util.ArrayList;

import io.anua.vinci.constants.Vinci_MetadataConstants;
import io.anua.vinci.model.IEXResponse;

public class StockObjectBuilder {

    /**************************
     * Public Methods
     *************************/

    /* Builds the stock Object to be displayed
     *
     * @method buildStockObject
     * @param {@link IEXResponse}
     * @param {@link ArrayList {@link String}}
     * @param {@link Boolean} - isHomeScreen - if true then automatically a user stock
     * @return {@link Bundle}
     * @public
     */
    public static Bundle buildStockObject(IEXResponse iexObject,
                                    ArrayList<String> defaultUserStocks, Boolean isHomeScreen){
        Bundle bundle = new Bundle();

        if(isHomeScreen) {
            bundle.putBoolean(Vinci_MetadataConstants.IS_USER_STOCK, true);
        }
        else {
            bundle.putBoolean(Vinci_MetadataConstants.IS_USER_STOCK, userHasStock(defaultUserStocks, iexObject.getQuote().getStockSymbol()));
        }

        bundle.putStringArrayList(Vinci_MetadataConstants.DEFAULT_USER_STOCKS, defaultUserStocks);

        bundle.putString(Vinci_MetadataConstants.COMPANY_NAME, iexObject.getQuote().getCompanyName());
        bundle.putString(Vinci_MetadataConstants.LOGO_URL, iexObject.getLogo().getLogoURL());

        bundle.putLong(Vinci_MetadataConstants.MARKET_CAP, iexObject.getQuote().getMarketCapValue());

        bundle.putFloat(Vinci_MetadataConstants.OPEN_VALUE, iexObject.getQuote().getOpenValue());
        bundle.putFloat(Vinci_MetadataConstants.CLOSE_VALUE, iexObject.getQuote().getCloseValue());
        bundle.putFloat(Vinci_MetadataConstants.HIGH_VALUE, iexObject.getQuote().getHighValue());
        bundle.putFloat(Vinci_MetadataConstants.LOW_VALUE, iexObject.getQuote().getLowValue());
        bundle.putFloat(Vinci_MetadataConstants.WEEK_52_HIGH, iexObject.getQuote().getWeek52High());
        bundle.putFloat(Vinci_MetadataConstants.WEEK_52_LOW, iexObject.getQuote().getWeek52Low());

        return bundle;
    }

    /**************************
     * Private Methods
     *************************/

    /* Checks if user has stock saved already
     *
     * @method userHasStock
     * @param {@link ArrayList {@link String}}
     * @param {@link String}
     * @return {@link Boolean}
     * @private
     */
    private static Boolean userHasStock(ArrayList<String> defaultUserStocks, String stock){
        return defaultUserStocks.contains(stock);
    }
}

package io.anua.vinci.utils;

public class StockAdapterUtil {

    /**************************
     * Constants
     *************************/

    private static String TRILLION_STRING = " T";
    private static String BILLION_STRING = " BIL";
    private static String MILLION_STRING = " MIL";
    private static String THOUSAND_STRING = " K";

    /**************************
     * Public Methods
     *************************/

    /* Builds the Market Cap value formatted
     *
     * @method formatMarketCapValue
     * @param {@link String}
     * @return {@link String}
     * @public
     */
    public static String formatMarketCapValue(String marketCapValue){
        String substring, formattedString, originalString;
        int length = marketCapValue.length();
        originalString = marketCapValue.substring(0, 4);

        if(length % 3 == 1) {
            substring = originalString.substring(0, 1);
            formattedString = substring + "." + originalString.substring(1, 3);
            return formatMarketCapValueString(formattedString, length);
        }else if(length % 3 == 2){
            substring = originalString.substring(0, 2);
            formattedString = substring + "." + originalString.substring(2,4);
            return formatMarketCapValueString(formattedString, length);
        }else{
            return formatMarketCapValueString(originalString.substring(0,3), length);
        }
    }

    /* Formats the market cap value based on string length
     *
     * @method formatMarketCapValueString
     * @param {@link String}
     * @param {@link int}
     * @return {@link String}
     * @public
     */
    public static String formatMarketCapValueString(String marketCapValue, int length){
        if(length > 12){
            return marketCapValue + TRILLION_STRING;
        }else if(length > 9){
            return marketCapValue + BILLION_STRING;
        } else if(length > 6){
            return marketCapValue + MILLION_STRING;
        }else{
            return marketCapValue + THOUSAND_STRING;
        }
    }

    /* Formats the primary stock markets
     *
     * @method formatPrimaryMarket
     * @param {@link String}
     * @return {@link String}
     * @public
     */
    public static String formatPrimaryMarket(String primaryMarket){
        switch(primaryMarket){
            case "Nasdaq Global Select":
                return  "NASDAQ";
            case "NASDAQ Global Market":
                return  "NASDAQ";
            case "New York Stock Exchange":
                return "NYSE";
            default:
                return primaryMarket;
        }
    }
}

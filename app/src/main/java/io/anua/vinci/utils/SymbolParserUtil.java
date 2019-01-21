package io.anua.vinci.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class SymbolParserUtil {

    /**************************
     * Public Methods
     *************************/

    /* parses the string of symbols passed in
     * uses the delimiter ','
     *
     * @method parseSymbols
     * @param {@link String}
     * @returns {@link String[]}
     */
    public static String[] parseSymbols(String symbols) {
        if(symbols != null) {
            return symbols.split(",");
        }
        return null;
    }

    /* capitalizes String array of symbols
     *
     * @method capitalizeSymbols
     * @param {@link String[]}
     * @returns {@link ArrayList<{@link String}>}
     */
    public static ArrayList<String> capitalizeSymbols(String[] symbols) {
        if(symbols != null){
            for(int i = 0; i < symbols.length; i++){
                symbols[i] = capitalizeSymbol(symbols[i]);
            }
            return new ArrayList<>(Arrays.asList(symbols));
        }
        return null;
    }

    /* capitalizes a single symbol
     *
     * @method capitalizeSymbol
     * @param {@link String}
     * @returns {@link String}
     */
    public static String capitalizeSymbol (String symbol) {
        return symbol.toUpperCase();
    }
}

package io.anua.vinci.utils.test;

import org.junit.Test;

import io.anua.vinci.utils.StockAdapterUtil;
import static junit.framework.Assert.assertEquals;

public class StockAdapterUtilTest {

    /**************************
     * Constants
     *************************/

    private static final String FAKE_MARKET_CAP = "1.00";

    /**************************
     * Tests
     *************************/

    @Test
    public void formatMarketCapValueString_formatStringMil() {
        String answer = "1.00 MIL";
        assertEquals(StockAdapterUtil.formatMarketCapValueString(FAKE_MARKET_CAP, 7), answer);
    }

    @Test
    public void formatMarketCapValueString_formatStringThousand() {
        String answer = "1.00 K";
        assertEquals(StockAdapterUtil.formatMarketCapValueString(FAKE_MARKET_CAP, 6), answer);
    }

    @Test
    public void formatMarketCapValueString_formatStringBil() {
        String answer = "1.00 BIL";
        assertEquals(StockAdapterUtil.formatMarketCapValueString(FAKE_MARKET_CAP, 10), answer);
    }

    @Test
    public void formatMarketCapValueString_formatStringTril() {
        String answer = "1.00 T";
        assertEquals(StockAdapterUtil.formatMarketCapValueString(FAKE_MARKET_CAP, 13), answer);
    }

    @Test
    public void formatMarketCapValue_parseValueTril() {
        String answer = "1.00 T";
        assertEquals(StockAdapterUtil.formatMarketCapValue("1000000000000"), answer);
    }

    @Test
    public void formatMarketCapValue_parseValueTenTril() {
        String answer = "10.00 T";
        assertEquals(StockAdapterUtil.formatMarketCapValue("10000000000000"), answer);
    }

    @Test
    public void formatMarketCapValue_parseValueHundredTril() {
        String answer = "100 T";
        assertEquals(StockAdapterUtil.formatMarketCapValue("100000000000000"), answer);
    }

    @Test
    public void formatMarketCapValue_parseValueBil() {
        String answer = "1.00 BIL";
        assertEquals(StockAdapterUtil.formatMarketCapValue("1000000000"), answer);
    }

    @Test
    public void formatMarketCapValue_parseValueTenBil() {
        String answer = "10.00 BIL";
        assertEquals(StockAdapterUtil.formatMarketCapValue("10000000000"), answer);
    }

    @Test
    public void formatMarketCapValue_parseValueHundredBil() {
        String answer = "100 BIL";
        assertEquals(StockAdapterUtil.formatMarketCapValue("100000000000"), answer);
    }

    @Test
    public void formatMarketCapValue_parseValueMil() {
        String answer = "1.00 MIL";
        assertEquals(StockAdapterUtil.formatMarketCapValue("1000000"), answer);
    }

    @Test
    public void formatMarketCapValue_parseValueTenMil() {
        String answer = "10.00 MIL";
        assertEquals(StockAdapterUtil.formatMarketCapValue("10000000"), answer);
    }

    @Test
    public void formatMarketCapValue_parseValueHundredMil() {
        String answer = "100 MIL";
        assertEquals(StockAdapterUtil.formatMarketCapValue("100000000"), answer);
    }

    @Test
    public void formatMarketCapValue_parseValueThousand() {
        String answer = "1.00 K";
        assertEquals(StockAdapterUtil.formatMarketCapValue("1000"), answer);
    }

    @Test
    public void formatMarketCapValue_parseValueTenThousand() {
        String answer = "10.00 K";
        assertEquals(StockAdapterUtil.formatMarketCapValue("10000"), answer);
    }

    @Test
    public void formatMarketCapValue_parseValueHundredThousand() {
        String answer = "100 K";
        assertEquals(StockAdapterUtil.formatMarketCapValue("100000"), answer);
    }
}

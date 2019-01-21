package io.anua.vinci.utils.test;

import org.junit.Test;
import java.util.ArrayList;
import static junit.framework.Assert.assertEquals;
import io.anua.vinci.utils.SymbolParserUtil;
import static org.junit.Assert.assertArrayEquals;

public class SymbolParserUtilTest {

    /**************************
     * Constants
     *************************/

    private static final String FAKE_SYMBOLS = "aapl,tsla,fb";
    private static final String[] FAKE_PARSED_ARRAY = {"aapl", "tsla", "fb"};

    /**************************
     * Tests
     *************************/

    /*  makes sure that a string of symbols will be parsed correctly
     */
    @Test
    public void parseSymbols_parseDelimiterCorrectly() {
        String[] answer = {"aapl", "tsla", "fb"};
        assertArrayEquals(SymbolParserUtil.parseSymbols(FAKE_SYMBOLS), answer);
    }

    /*  makes sure that an array of symbols will be capitalized correctly
     */
    @Test
    public void capitalizeSymbols_parseSymbolsComeOutCorrectly() {
        ArrayList<String> answer = new ArrayList<>();
        answer.add("AAPL");
        answer.add("TSLA");
        answer.add("FB");
        assertEquals(SymbolParserUtil.capitalizeSymbols(FAKE_PARSED_ARRAY), answer);
    }
}

package io.anua.vinci.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import io.anua.vinci.R;
import io.anua.vinci.constants.Vinci_MetadataConstants;
import io.anua.vinci.utils.StockAdapterUtil;

public class StockObjectActivity extends AppCompatActivity {

    /**************************
     * Private Members
     *************************/

    private TextView stockSymbol, companyName, openValue, closeValue, highValue,
            lowValue, week52High, week52Low, marketCap, changeValue;

    /**************************
     * LifeCycle Methods
     *************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_object);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        stockSymbol = findViewById(R.id.stock_symbol);
        companyName = findViewById(R.id.company_name);
        openValue = findViewById(R.id.open_value);
        closeValue = findViewById(R.id.close_value);
        highValue = findViewById(R.id.high_value);
        lowValue = findViewById(R.id.low_value);
        week52High = findViewById(R.id.week_52_high);
        week52Low = findViewById(R.id.week_52_low);
        marketCap = findViewById(R.id.marketcap_value);
        changeValue = findViewById(R.id.change_value);

        handleIntent(getIntent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**************************
     * Private Methods
     *************************/

    /* Handles the intent and grabs the stock object values and sets them to text view
     *
     * @method handleIntent
     * @param {@link Intent}
     * @private
     */
    private void handleIntent(Intent intent) {
            Bundle stockObjectBundle = intent.getBundleExtra(Vinci_MetadataConstants.STOCK_OBJECT);

            if (stockObjectBundle != null) {
                stockSymbol.setText(stockObjectBundle.getString(Vinci_MetadataConstants.STOCK_SYMBOL));
                companyName.setText(stockObjectBundle.getString(Vinci_MetadataConstants.COMPANY_NAME));
                openValue.setText(formatFloatValue(stockObjectBundle.getFloat(Vinci_MetadataConstants.OPEN_VALUE), Vinci_MetadataConstants.OPEN_VALUE));
                closeValue.setText(formatFloatValue(stockObjectBundle.getFloat(Vinci_MetadataConstants.CLOSE_VALUE), Vinci_MetadataConstants.CLOSE_VALUE));
                highValue.setText(formatFloatValue(stockObjectBundle.getFloat(Vinci_MetadataConstants.HIGH_VALUE), Vinci_MetadataConstants.HIGH_VALUE));
                lowValue.setText(formatFloatValue(stockObjectBundle.getFloat(Vinci_MetadataConstants.LOW_VALUE), Vinci_MetadataConstants.LOW_VALUE));
                week52High.setText(formatFloatValue(stockObjectBundle.getFloat(Vinci_MetadataConstants.WEEK_52_HIGH), Vinci_MetadataConstants.WEEK_52_HIGH_DISPLAY));
                week52Low.setText(formatFloatValue(stockObjectBundle.getFloat(Vinci_MetadataConstants.WEEK_52_LOW), Vinci_MetadataConstants.WEEK_52_LOW_DISPLAY));
                marketCap.setText(formatMarketCap(stockObjectBundle.getLong(Vinci_MetadataConstants.MARKET_CAP)));

                formatChangeValue(stockObjectBundle.getDouble(Vinci_MetadataConstants.CHANGE_VALUE));
            }
    }

    /* Formats float values to be displayed
     *
     * @method formatFloatValue
     * @param {@link Float}
     * @param {@link String}
     * @private
     * @returns {@link String}
     */
    private String formatFloatValue(Float floatValue, String valueType) {
        if(floatValue != null){
            return valueType + ": " + floatValue.toString();
        }
        return valueType + ": -";
    }

    /* Formats market cap value to be displayed
     *
     * @method formatMarketCap
     * @param {@link Long}
     * @private
     * @returns {@link String}
     */
    private String formatMarketCap(Long marketCap) {
        if(marketCap != null){
            return Vinci_MetadataConstants.MARKET_CAP_DISPLAY + ": " + StockAdapterUtil.formatMarketCapValue(marketCap.toString());
        }
        return Vinci_MetadataConstants.MARKET_CAP_DISPLAY + ": -";
    }

    /* Sets color of change value text based on value
     *
     * @method formatChangeValue
     * @param {@link Double}
     * @private
     */
    private void formatChangeValue(Double changeValueCheck) {
        if(changeValueCheck != null){
            if(changeValueCheck < 0) {
                changeValue.setTextColor(Color.RED);
            }else {
                changeValue.setTextColor(Color.GREEN);
            }

            changeValue.setText(changeValueCheck.toString());
        }
        else {
            changeValue.setText("-");
        }
    }
}

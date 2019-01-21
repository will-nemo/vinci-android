package io.anua.vinci.activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.anua.vinci.R;
import io.anua.vinci.adapter.SearchedStockAdapter;
import io.anua.vinci.constants.Vinci_MetadataConstants;
import io.anua.vinci.interfaces.IEXStockInterface;
import io.anua.vinci.listener.StockAdapterListener;
import io.anua.vinci.model.IEXResponse;
import io.anua.vinci.model.Quote;
import io.anua.vinci.utils.SymbolParserUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StockSearchActivity extends AppCompatActivity implements StockAdapterListener {

    /**************************
     * Private Members
     *************************/

    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private SearchedStockAdapter searchedStockAdapter;

    /**************************
     * LifeCycle Methods
     *************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_search);

        progressDialog = new ProgressDialog(this, R.style.AppCompatProgressDialogStyle);
        progressDialog.setMessage(Vinci_MetadataConstants.LOADING_DIALOG);
        progressDialog.show();

        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    /**************************
     * Private Methods
     *************************/

    /*  Handles the search query carried by the intent and loads with it
     *
     * @method handleIntent
     * @param {@link Intent}
     * @private
     */
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            loadDefaultStocks(query);
        }
    }

    /**************************
     * Public Methods
     *************************/

    /* loads the IEXResponse Objects into the Recycler View
     *
     * @method loadDefaultStocks
     * @param {@link String} - has the user stocks
     * @public
     */
    public void loadDefaultStocks(final String userStocks) {
        //TODO: Function should search for stocks, not pull up stocks based of symbols
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Vinci_MetadataConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IEXStockInterface request = retrofit.create(IEXStockInterface.class);
        Call<Map<String, IEXResponse>> call = request.getDefaultStocks(userStocks);

        call.enqueue(new Callback<Map<String, IEXResponse>>() {
            @Override
            public void onResponse(Call<Map<String, IEXResponse>> call, Response<Map<String, IEXResponse>> response) {
                if (response.isSuccessful()) {
                    Map<String, IEXResponse> iexResponse = response.body();
                    String[] parseSymbols = SymbolParserUtil.parseSymbols(userStocks);
                    String capitalizedSymbol = SymbolParserUtil.capitalizeSymbol(userStocks);

                    ArrayList<String> symbolList = new ArrayList<String>();
                    symbolList.add(capitalizedSymbol);

                    List<IEXResponse> iexStocks = buildIEXResponseList(iexResponse, symbolList);
                    if (iexStocks != null && iexResponse.size() > 0) {
                        progressDialog.dismiss();
                        //TODO: Create Search adapter to show this is a different format
                        searchedStockAdapter = new SearchedStockAdapter(StockSearchActivity.this, iexStocks, StockSearchActivity.this);
                        recyclerView.setAdapter(searchedStockAdapter);
                    } else {
                        progressDialog.dismiss();
                        Log.d("Error", "IEX Stock list is: null");
                    }
                } else {
                    progressDialog.dismiss();
                    switch (response.code()) {
                        case 404:
                            Log.e("Error", "404: Stock was not found");
                            //TODO: When error received, take user to error screen
                            break;
                        case 500:
                            Log.e("Error", "Server is broken :/");
                            break;
                        default:
                            Log.e("Error", "UNKNOWN ERROR");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, IEXResponse>> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("Error", t.getMessage());
            }
        });
    }

    //TODO: Delete this after new way to search stocks
    /* Builds the list of quotes to display in the recycler view
     *
     * @method buildIEXResponseList
     * @param {@link Map<{@link String}, {@link IEXResponse}>}
     * @return {@link List<{@link IEXResponse}>}
     * @public
     */
    public List<IEXResponse> buildIEXResponseList(Map<String, IEXResponse> iexResponseMap, ArrayList<String> symbols){
        final List<IEXResponse> iexList = new ArrayList<>();
        IEXResponse iexResponse;
        for (int i = 0; i < symbols.size(); i++) {
            iexList.add((IEXResponse) iexResponseMap.get(symbols.get(i)));
        }
        return iexList;
    }

    @Override
    public void onStockSelected(Quote result) {
        //TODO: Add ability to open stock for more information
    }
}

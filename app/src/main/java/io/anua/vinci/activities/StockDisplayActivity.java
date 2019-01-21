package io.anua.vinci.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import io.anua.vinci.R;
import io.anua.vinci.adapter.StockAdapter;
import io.anua.vinci.interfaces.IEXStockInterface;
import io.anua.vinci.listener.StockAdapterListener;
import io.anua.vinci.model.IEXResponse;
import io.anua.vinci.model.Quote;
import io.anua.vinci.model.User;
import io.anua.vinci.utils.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StockDisplayActivity extends AppCompatActivity implements StockAdapterListener {

    /**************************
     * Constants
     *************************/

    //EXAMPLE OF THE STOCKS THAT ARE PARSED FROM THE FIRESTORE
    public static String DEFAULT_SYMBOLS = "aapl,aap,fb,tsla,crsp";
    public static String BASE_URL = "https://api.iextrading.com/1.0/";
    public static String LOADING_DIALOG = "Loading....";

    /**************************
     * Private Members
     *************************/

    private RecyclerView recyclerView;
    private StockAdapter stockAdapter;
    private ProgressDialog progressDialog;
    private FloatingActionButton floatingActionButton;

    private FirebaseFirestore firebaseFirestoreService;

    /**************************
     * LifeCycle Methods
     *************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_stock);

        floatingActionButton = findViewById(R.id.fab);

        progressDialog = new ProgressDialog(this, R.style.AppCompatProgressDialogStyle);
        progressDialog.setMessage(LOADING_DIALOG);
        progressDialog.show();

        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        findUserStocks();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StockDisplayActivity.this, StockSearchActivity.class));
            }
        });
    }

    /**************************
     * Public Methods
     *************************/

    /* loads the user object for firebase firestore
     *
     * @method findUserStocks
     * @public
     */
    public void findUserStocks() {
        firebaseFirestoreService = FirebaseFirestore.getInstance();

        DocumentReference docRef = firebaseFirestoreService.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User userObject = documentSnapshot.toObject(User.class);
                if(userObject != null){
                    loadDefaultStocks(userObject.getUserStocks());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //TODO: Add failure for this
                Toast.makeText(StockDisplayActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* loads the IEXResponse Objects into the Recycler View
     *
     * @method loadDefaultStocks
     * @param {@link String} - has the user stocks
     * @public
     */
    public void loadDefaultStocks(final String userStocks) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
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
                    ArrayList<String> capitalizedSymbols = SymbolParserUtil.capitalizeSymbols(parseSymbols);

                    List<IEXResponse> iexStocks = buildIEXResponseList(iexResponse, capitalizedSymbols);
                    if (iexStocks != null) {
                        progressDialog.dismiss();
                        View view = findViewById(R.id.fab);
                        view.setVisibility(View.VISIBLE);
                        stockAdapter = new StockAdapter(StockDisplayActivity.this, iexStocks, StockDisplayActivity.this);
                        recyclerView.setAdapter(stockAdapter);
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

    /* Takes the user to the stock profile and shows more
     * information
     *
     * @method onStockSelected
     * @param {@link Quote}
     * @public
     */
    @Override
    public void onStockSelected(Quote result) {
        //TODO: Add ability to open stock for more information
    }

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
}

package io.anua.vinci.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.anua.vinci.R;
import io.anua.vinci.listener.StockAdapterListener;
import io.anua.vinci.model.IEXResponse;
import io.anua.vinci.utils.StockAdapterUtil;

public class SearchedStockAdapter extends RecyclerView.Adapter<SearchedStockAdapter.StockViewHolder>  {

    /**************************
     * Private Members
     *************************/

    private StockAdapterListener listener;
    private Context context;
    private List<IEXResponse> stockList;

    /**************************
     * Constructor
     *************************/

    public SearchedStockAdapter(Context context, List<IEXResponse> searchedStocks, StockAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.stockList = searchedStocks;
    }

    /**************************
     * LifeCycle Methods
     *************************/

    @Override
    public StockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.searched_stock_row, parent, false);
        return new StockViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StockViewHolder holder, final int position) {
        final IEXResponse searchedStock = stockList.get(position);

        holder.stockSymbol.setText(searchedStock.getQuote().getStockSymbol());
        holder.companyName.setText(searchedStock.getQuote().getCompanyName());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onStockSelected(stockList.get(position));
            }
        });
    }

    /**************************
     * Public Methods
     *************************/

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    /**************************
     * View Holder
     *************************/

    public class StockViewHolder extends RecyclerView.ViewHolder {

        View adapterView;
        public TextView stockSymbol, companyName;
        public RelativeLayout relativeLayout;

        public StockViewHolder(View view) {
            super(view);
            adapterView = view;
            stockSymbol = view.findViewById(R.id.stock_symbol);
            companyName = view.findViewById(R.id.company_name);
            relativeLayout = view.findViewById(R.id.relative_layout);
        }
    }
}
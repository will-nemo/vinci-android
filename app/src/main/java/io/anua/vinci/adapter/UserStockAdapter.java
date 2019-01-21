package io.anua.vinci.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.anua.vinci.R;
import io.anua.vinci.listener.StockAdapterListener;
import io.anua.vinci.model.IEXResponse;
import io.anua.vinci.utils.StockAdapterUtil;

public class UserStockAdapter extends RecyclerView.Adapter<UserStockAdapter.StockViewHolder>  {

    /**************************
     * Private Members
     *************************/

    private StockAdapterListener listener;
    private Context context;
    private List<IEXResponse> stockList;

    /**************************
     * Constructor
     *************************/

    public UserStockAdapter(Context context, List<IEXResponse> defaultStocks, StockAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.stockList = defaultStocks;
    }

    /**************************
     * LifeCycle Methods
     *************************/

    @Override
    public StockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_stock_row, parent, false);
        return new StockViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StockViewHolder holder, final int position) {
        String capValue;
        Float realTimePrice;
        final Double changeValueCheck;

        final IEXResponse stockQuote = stockList.get(position);
        holder.stockSymbol.setText(stockQuote.getQuote().getStockSymbol());
        holder.companyName.setText(stockQuote.getQuote().getCompanyName());


        holder.primaryStockExchange.setText(StockAdapterUtil.formatPrimaryMarket(stockQuote.getQuote().getPrimaryExchange()));

        realTimePrice = stockQuote.getQuote().getRealtimePrice();
        if(realTimePrice != null) {
            holder.realtimePrice.setText(realTimePrice.toString());
        }
        else {
            holder.realtimePrice.setText(stockQuote.getQuote().getCloseValue().toString());

        }

        capValue = StockAdapterUtil.formatMarketCapValue(stockQuote.getQuote().getMarketCapValue().toString());
        holder.marketCapValue.setText(capValue);

        changeValueCheck = stockQuote.getQuote().getChangeValue();

        if(changeValueCheck < 0) {
            holder.changeValue.setTextColor(Color.RED);
        }else {
            holder.changeValue.setTextColor(Color.GREEN);
        }

        holder.changeValue.setText(changeValueCheck.toString());

//
//        holder.sName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // send selected contact in callback
//                listener.onSchoolSelected(stockList.get(position));
//            }
//        });
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

        public TextView stockSymbol, companyName,
                realtimePrice, marketCapValue, changeValue, primaryStockExchange;

        public StockViewHolder(View view) {
            super(view);
            stockSymbol = view.findViewById(R.id.stock_symbol);
            companyName = view.findViewById(R.id.company_name);
            realtimePrice = view.findViewById(R.id.realtime_price);
            marketCapValue = view.findViewById(R.id.marketcap_value);
            changeValue = view.findViewById(R.id.change_value);
            primaryStockExchange = view.findViewById(R.id.stock_market);
        }
    }
}
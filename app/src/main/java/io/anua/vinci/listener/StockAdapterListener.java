package io.anua.vinci.listener;


import io.anua.vinci.model.Quote;

public interface StockAdapterListener {

    /**************************
     * Methods to be implemented
     *************************/

    /* Listens to Stock adapter
     * When row is clicked performs action
     *
     * @attribute onSchoolSelected
     * @method onSchoolSelected
     * @param {@link Quote}
     */
    void onStockSelected(Quote result);
}
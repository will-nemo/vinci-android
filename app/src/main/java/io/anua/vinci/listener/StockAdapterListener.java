package io.anua.vinci.listener;

import io.anua.vinci.model.IEXResponse;

public interface StockAdapterListener {

    /**************************
     * Methods to be implemented
     *************************/

    /* Listens to Stock adapter
     * When row is clicked performs action
     *
     * @attribute onSchoolSelected
     * @method onSchoolSelected
     * @param {@link IEXResponse}
     */
    void onStockSelected(IEXResponse iexObject);
}
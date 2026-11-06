package com.example.dessertclicker.ui

import com.example.dessertclicker.data.Datasource
import org.junit.Assert.assertEquals
import org.junit.Test

class DessertViewModelTest {
    @Test fun initialization_hasZeroCountersAndFirstDessert() {
        val state = DessertViewModel().uiState.value
        assertEquals(0, state.revenue)
        assertEquals(0, state.dessertsSold)
        assertEquals(Datasource.dessertList.first(), state.currentDessert)
    }

    @Test fun selling_chargesOldPriceAndChangesDessertAtEveryThreshold() {
        val model = DessertViewModel()
        var expectedRevenue = 0
        val desserts = Datasource.dessertList
        for (sold in 1..desserts.last().startProductionAmount + 1) {
            expectedRevenue += desserts.last { sold - 1 >= it.startProductionAmount }.price
            model.sellDessert()
            val state = model.uiState.value
            assertEquals(sold, state.dessertsSold)
            assertEquals(expectedRevenue, state.revenue)
            assertEquals(desserts.last { sold >= it.startProductionAmount }, state.currentDessert)
        }
    }
}

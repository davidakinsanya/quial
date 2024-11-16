package com.quial.app.paywall

import com.quial.app.utils.UiStateHolder
import com.quial.app.utils.uiStateHolderScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PaywallConfigStateHolder(private val state: PaywallConfigState): UiStateHolder() {
    private val _paywallMap: MutableStateFlow<Map<String, String>> = MutableStateFlow(mapOf())
    val paywallMap = _paywallMap
        .onStart { loadData() }
        .stateIn(
            uiStateHolderScope,
            SharingStarted.WhileSubscribed(5000L),
            mapOf()
        )

    private fun loadData() = uiStateHolderScope.launch {
        _paywallMap.value = state.readConfig().map
    }
}
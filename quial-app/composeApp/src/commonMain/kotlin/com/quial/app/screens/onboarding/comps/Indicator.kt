package com.quial.app.screens.onboarding.comps

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.quial.app.data.datastore.DataStoreStateHolder
import com.quial.app.screens.onboarding.OnboardingUiStateHolder
import org.jetbrains.compose.resources.Font
import quial_app.composeapp.generated.resources.DMSans_Bold
import quial_app.composeapp.generated.resources.Res

@Composable
fun OnboardingNavigationButtons(state: PagerState,
                                navigate: () -> Unit,
                                uiStateHolder: OnboardingUiStateHolder,
                                dataStateHolder: DataStoreStateHolder) {
    Row {
        if (state.currentPage == state.pageCount - 1)
            Button(onClick = {
                dataStateHolder.saveOnboardingResponse(uiStateHolder.getOnboardingResponse())
                navigate.invoke() },
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.outlinedButtonColors(Color.White)
                   ) {
                Text(text = "Continue",
                     color = Color.Black,
                     fontFamily = FontFamily(Font(Res.font.DMSans_Bold)))
            }
    }
}
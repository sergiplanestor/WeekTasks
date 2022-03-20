package com.splanes.weektasks.ui.feature.dashboard

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.splanes.toolkit.compose.base_arch.feature.presentation.activity.BaseComponentActivity
import com.splanes.toolkit.compose.ui.components.feature.navhost.component.AnimatedNavHost
import com.splanes.toolkit.compose.ui.components.feature.navhost.graph.NavGraphDestination
import com.splanes.toolkit.compose.ui.components.feature.navhost.graph.plusAssign
import com.splanes.toolkit.compose.ui.components.feature.scaffold.model.ScaffoldColors
import com.splanes.toolkit.compose.ui.components.feature.statusbar.model.StatusBarColors
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Colors
import com.splanes.weektasks.ui.common.utils.route.GraphRoute
import com.splanes.weektasks.ui.feature.dashboard.component.DashboardComponent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : BaseComponentActivity<DashboardActivityViewModel>() {

    override val initialStatusBarColor: StatusBarColors
        @Composable
        get() = StatusBarColors(Colors.primary)

    override val activityViewModel: DashboardActivityViewModel
        @Composable
        get() = hiltViewModel<DashboardActivityViewModel>().apply {
            ready(
                initialStatusBarColor,
                ScaffoldColors(container = Colors.primaryContainer, content = Colors.surface)
            )
        }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
    @Composable
    override fun ActivityContentComponent(activityViewModel: DashboardActivityViewModel) {

        val navController = rememberAnimatedNavController()
        val uiController = rememberSystemUiController()

        Navigator(navController, uiController)
    }

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
    @Composable
    private fun Navigator(
        navController: NavHostController,
        uiController: SystemUiController
    ) {
        AnimatedNavHost(
            navController = navController,
            startDestinationRoute = GraphRoute.Dashboard.toString()
        ) {
            this += NavGraphDestination(route = GraphRoute.Dashboard.toString()) {
                DashboardComponent(uiController = uiController)
            }
        }
    }
}
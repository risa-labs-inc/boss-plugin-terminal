package ai.rever.boss.plugin.dynamic.terminal

import ai.rever.boss.plugin.api.LocalWindowIdProvider
import ai.rever.boss.plugin.api.LocalWindowProjectStateProvider
import ai.rever.boss.plugin.api.PanelComponentWithUI
import ai.rever.boss.plugin.api.PanelEventProvider
import ai.rever.boss.plugin.api.PanelInfo
import ai.rever.boss.plugin.api.PluginContext
import ai.rever.boss.plugin.api.SettingsProvider
import ai.rever.boss.plugin.api.TerminalTabPluginAPI
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle.Callbacks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * Terminal panel component (Dynamic Plugin)
 *
 * Uses TerminalTabPluginAPI from the terminal-tab plugin for full terminal functionality.
 * Gets the API via getPluginAPI() rather than using TerminalContentProvider from the host.
 */
class TerminalComponent(
    ctx: ComponentContext,
    override val panelInfo: PanelInfo,
    private val pluginContext: PluginContext,
    private val panelEventProvider: PanelEventProvider?,
    private val settingsProvider: SettingsProvider?
) : PanelComponentWithUI, ComponentContext by ctx {

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    init {
        lifecycle.subscribe(
            callbacks = object : Callbacks {
                override fun onDestroy() {
                    coroutineScope.cancel()
                }
            }
        )
    }

    /**
     * Called when user clicks Reset in the panel's more menu.
     */
    override fun onBeforeReset() {
        val terminalApi = pluginContext.getPluginAPI(TerminalTabPluginAPI::class.java)
        terminalApi?.resetAllTerminals()
    }

    @Composable
    override fun Content() {
        val terminalApi = pluginContext.getPluginAPI(TerminalTabPluginAPI::class.java)

        if (terminalApi != null && panelEventProvider != null && settingsProvider != null) {
            val windowIdProvider = LocalWindowIdProvider.current
            val windowProjectStateProvider = LocalWindowProjectStateProvider.current
            val windowId = windowIdProvider?.getWindowId()
            val projectPath = windowProjectStateProvider?.getSelectedProjectPath() ?: ""

            terminalApi.TabbedTerminalContent(
                workingDirectory = projectPath.ifEmpty { null },
                onExit = {
                    windowId?.let { wid ->
                        coroutineScope.launch {
                            panelEventProvider.closePanel(panelInfo.id, wid)
                        }
                    }
                },
                onShowSettings = {
                    windowId?.let { settingsProvider.openSettings(it, "TERMINAL") }
                }
            )
        } else {
            // Fallback stub content when API not available
            TerminalContent()
        }
    }
}

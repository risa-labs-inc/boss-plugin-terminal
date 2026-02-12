package ai.rever.boss.plugin.dynamic.terminal

import ai.rever.boss.plugin.api.DynamicPlugin
import ai.rever.boss.plugin.api.PluginContext
import ai.rever.boss.plugin.api.TerminalTabPluginAPI

/**
 * Terminal dynamic plugin - Loaded from external JAR.
 *
 * Integrated terminal emulator using TerminalTabPluginAPI from the terminal-tab plugin.
 * This plugin consumes the API registered by the terminal-tab plugin via getPluginAPI().
 */
class TerminalDynamicPlugin : DynamicPlugin {
    override val pluginId: String = "ai.rever.boss.plugin.dynamic.terminal"
    override val displayName: String = "Terminal (Dynamic)"
    override val version: String = "1.0.4"
    override val description: String = "Integrated terminal emulator"
    override val author: String = "Risa Labs"
    override val url: String = "https://github.com/risa-labs-inc/boss-plugin-terminal"

    override fun register(context: PluginContext) {
        val panelEventProvider = context.panelEventProvider
        val settingsProvider = context.settingsProvider

        if (panelEventProvider == null || settingsProvider == null) {
            // Providers not available - register stub
            context.panelRegistry.registerPanel(TerminalInfo) { ctx, panelInfo ->
                TerminalComponent(ctx, panelInfo, context, null, null)
            }
            return
        }

        context.panelRegistry.registerPanel(TerminalInfo) { ctx, panelInfo ->
            TerminalComponent(
                ctx = ctx,
                panelInfo = panelInfo,
                pluginContext = context,
                panelEventProvider = panelEventProvider,
                settingsProvider = settingsProvider
            )
        }
    }
}

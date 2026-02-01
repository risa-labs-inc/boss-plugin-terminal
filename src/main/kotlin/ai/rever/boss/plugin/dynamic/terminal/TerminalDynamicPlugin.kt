package ai.rever.boss.plugin.dynamic.terminal

import ai.rever.boss.plugin.api.DynamicPlugin
import ai.rever.boss.plugin.api.PluginContext

/**
 * Terminal dynamic plugin - Loaded from external JAR.
 *
 * Integrated terminal emulator
 */
class TerminalDynamicPlugin : DynamicPlugin {
    override val pluginId: String = "ai.rever.boss.plugin.dynamic.terminal"
    override val displayName: String = "Terminal (Dynamic)"
    override val version: String = "1.0.1"
    override val description: String = "Integrated terminal emulator"
    override val author: String = "Risa Labs"
    override val url: String = "https://github.com/risa-labs-inc/boss-plugin-terminal"

    override fun register(context: PluginContext) {
        context.panelRegistry.registerPanel(TerminalInfo) { ctx, panelInfo ->
            TerminalComponent(ctx, panelInfo)
        }
    }
}

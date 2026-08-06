# BOSS Terminal (panel)

A terminal in the left sidebar panel slot.

This plugin contains no terminal implementation. It is a **panel adapter**: it looks up
`TerminalTabPluginAPI` and renders the [Terminal
Tab](https://github.com/risa-labs-inc/boss-plugin-terminal-tab) plugin's tabbed terminal inside
a panel, so you get the same terminal in the sidebar that you get in a tab.

**Terminal Tab must be installed.** Without it this panel shows a placeholder reading "Terminal
Tab Plugin Required", and points you at the Plugin Manager. That placeholder is the fallback
path, not the normal state.

## What it does

When Terminal Tab is present:

- **Renders its full tabbed terminal** in the panel.
- **Starts in the selected project's directory.**
- **Closes the panel** when the shell exits.
- **Opens the host's TERMINAL settings section** from the terminal's settings action.
- **Resets all terminals** from the panel's Reset menu action.

## Requirements

- BOSS >= 8.16.30, boss-plugin-api >= 1.0.20
- The **`terminal-tab` plugin**, which supplies `TerminalTabPluginAPI` and bundles the
  `bossterm-compose` emulator.
- Host `panelEventProvider` and `settingsProvider`.
- No external binaries of its own.

This plugin contributes no MCP tools. The terminal's own MCP surface belongs to `terminal-tab`.

## Build

```bash
./gradlew buildPluginJar
cp build/libs/boss-plugin-terminal-*.jar ~/.boss/plugins/
```

See [AGENTS.md](AGENTS.md) for architecture and conventions.

## License

Licensed under the [Apache License, Version 2.0](LICENSE).

Copyright 2025-2026 Risa Labs Inc.

# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

Project rules (layering, test naming, safety, files not to touch) live in `AGENTS.md` — read it first; this file only adds what isn't there.

## Commands

Windows host; use the Maven wrapper (`mvnw.cmd` in PowerShell, `./mvnw` in Git Bash).

```bash
mvnw.cmd clean compile                      # fastest useful check
mvnw.cmd spring-boot:run                    # app on http://localhost:7040
mvnw.cmd -Dtest=HealthCheckControllerIT test   # run one test class
scripts/generate-items.sh "<PZ>/media/scripts/generated/items"   # regenerate item catalog
```

**`mvnw clean test` runs zero tests.** Test classes are named `*IT`, and there is no failsafe plugin, so surefire's default includes skip them. Use `-Dtest=...` or add failsafe/surefire config.

**Starting the Spring context is not side-effect free** (this includes `@SpringBootTest`): it connects to the real SQLite files and enables scheduled jobs that send RCON commands to the live server (see Scheduling below). There is no `src/test/resources` / test profile yet — create one that overrides datasources and disables scheduling before writing context-loading tests.

## Architecture

Spring Boot 2.7 / Java 17 / Lombok app that administers a Project Zomboid dedicated server running on the same Windows machine.

### Talking to the game server: RCON via subprocess
All server control goes through `support/process/CommandUtils.executeRconCommand`, which shells out to `rcon/rcon.exe -a <address> -p <password> "<command>"` with `Runtime.exec`. Command templates are in `RconCommandType`; parameters are wrapped as `\\\"%s\\\"` (escaped quotes) so names with spaces survive `Runtime.exec` tokenization — copy that pattern for new commands, and validate any user input that goes into a command (no `"` / `\`; see `GiveItemService.validate`). Many services read the result as a string from the returned `InputStream`; `EventService.callEvent(event, rcon)` is the shared helper for parameterless commands.

Server start/restart is different: `RestartService` launches `whitelist.server-start-path` (a `.bat`) via `cmd /c start`, and gates start vs restart on flags in `ServerStatusService`, where "server is up" means the `players` RCON command returned non-empty output.

### Two SQLite databases
- **game** (`config/database/GameDatabaseConfiguration`, `@Primary`): the PZ server's own DB, read-only use, repositories in `domain/repository/game` (e.g. `whitelist` table).
- **extension** (`ExtensionDatabaseConfiguration`): this app's own DB, repositories in `domain/repository/extension`, uses `extensionTransactionManager`.

The repository *package* decides which DB a repository hits; both share the entity package `domain/model`. `hbm2ddl.auto=none` — schemas are created manually from `src/main/resources/*.sql`. Custom `SQLiteDialect` in `support/database`.

### Scheduling
`ScheduleConfig` enables `@Scheduled` + `@Async` on the `asyncTaskExecutor` pool. Active jobs: chopper/gunshot events (`EventService`), autosave (`SaveServerStateService`), server-state polling (`RestartService.queryServerStatus`). The whitelist-kick and access-level-ban jobs (`CheckAllowedPlayerService`, `AccessLevelCheckService`) have their `@Scheduled` commented out. Intervals come from `whitelist.*` properties. An `@Async void` method can't return output to a controller — expose a separate synchronous method (as `SaveServerStateService.save()` vs `autoSave()`).

### Web/UI
- `static/index.html`: dashboard at `/` (plain HTML + `fetch`), tabs Server / Events / Items; every button calls an existing REST endpoint and prints the response to the Output panel.
- `templates/players-whitelist.html`: the only Thymeleaf page, served by `AllowedPlayerController` at `/players-whitelist`.
- Most mutating endpoints are `GET` (restart, terminate, events, whitelist delete). There is no authentication yet (planned).

### Item catalog
`ItemCatalogService` loads `src/main/resources/items/items.txt` (one `Module.ItemName` per line, generated from game scripts) once at startup; `GiveItemService` only accepts items present there and runs `additem`. An enum is not viable (5k+ constants exceed the class-initializer size limit). The list currently contains vanilla `Base` items only, not workshop-mod items.

## Gotchas

- `src/main/resources/application.properties` holds local DB paths and the real RCON password as an uncommitted change. Never stage it with `git add -A`; GitHub has an empty password.
- With Lombok `@RequiredArgsConstructor`, don't initialize fields from injected dependencies (`rcon = properties.getRcon()` as a field) — field initializers run before the constructor; call the getter inside methods.
- Everything is Windows-specific (backslash paths in properties, `cmd /c`, `rcon.exe`).

# Discord activity setup

The game window title and icon are supplied by AVALON Client. Discord Rich Presence uses a separately registered Discord application.

The live catalog is configured with Application ID `1566335017280471060`, whose public name is **AVALON**. Large and small artwork use the original JPEG hosted in this repository. A local Discord IPC test confirmed that Discord accepted the AVALON application and resolved the external image. The steps below are for changing this configuration later.

1. Open the [Developer Portal](https://discord.com/developers/applications) and create or select an application named `AVALON`.
2. Set the application icon to the image supplied by the owner, available in `branding/src/main/resources/assets/avalonbranding/window-icon.jpg`.
3. Copy its **Application ID** from General Information. This is a public identifier; no bot token or client secret is needed.
4. Upload the same image to Rich Presence Art Assets under the key `avalon`.
5. Add a top-level `discord` object to `distribution.json` and `meta/distrometa.json`'s `meta` object:

```json
{
  "discord": {
    "clientId": "YOUR_APPLICATION_ID",
    "smallImageKey": "avalon",
    "smallImageText": "AVALON"
  }
}
```

Add the following to the selected profile in `distribution.json` and its `servermeta.json`'s `meta` object:

```json
{
  "discord": {
    "shortId": "AVALON",
    "largeImageKey": "avalon",
    "largeImageText": "AVALON"
  }
}
```

Publish the catalog with the real ID. Restart the launcher and launch the game with the Discord desktop client running. The launcher maintains Rich Presence while the game runs and clears it on game exit.

Discord's detected/registered games list is separate from Rich Presence. A manually registered entry may retain its old name until it is renamed or re-added in Discord settings.

## 한국어

Discord에 표시되는 게임 이름과 이미지는 Discord Developer Portal의 애플리케이션 설정을 사용합니다. 이름을 `AVALON`으로 지정하고, 전달받은 이미지를 앱 아이콘과 Rich Presence Art Assets에 등록하세요. General Information의 **Application ID**를 배포 설정에 연결하면 됩니다. 봇 토큰이나 Client Secret은 필요하지 않습니다.

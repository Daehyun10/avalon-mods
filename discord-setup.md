# Discord 활동 표시 설정

AVALON Launcher는 Discord Rich Presence로 게임 이름과 이미지를 표시합니다. 게임 실행 중 Discord 데스크톱 앱이 켜져 있어야 합니다.

현재 애플리케이션 이름은 **AVALON**, Application ID는 `1566335017280471060`입니다. 활동 이미지는 저장소에 있는 AVALON 이미지를 사용합니다.

## 애플리케이션 변경

1. [Discord Developer Portal](https://discord.com/developers/applications)에서 애플리케이션을 선택하거나 생성합니다.
2. 이름과 아이콘을 설정합니다. 아이콘 파일은 `branding/src/main/resources/assets/avalonbranding/window-icon.jpg`에 있습니다.
3. General Information의 **Application ID**를 복사합니다.
4. Rich Presence Art Assets에 이미지를 등록하고 키를 `avalon`으로 지정합니다.
5. 배포 설정의 ID와 이미지 키를 수정합니다.

`distribution.json`의 최상위와 `meta/distrometa.json`의 `meta`에 설정합니다.

```json
{
  "discord": {
    "clientId": "YOUR_APPLICATION_ID",
    "smallImageKey": "avalon",
    "smallImageText": "AVALON"
  }
}
```

`distribution.json`의 해당 프로필과 `servermeta.json`의 `meta`에 설정합니다.

```json
{
  "discord": {
    "shortId": "AVALON",
    "largeImageKey": "avalon",
    "largeImageText": "AVALON"
  }
}
```

설정을 배포한 뒤 런처를 다시 시작합니다. 게임을 종료하면 활동 표시도 종료됩니다. Application ID는 공개 식별자이며, 봇 토큰이나 Client Secret은 필요하지 않습니다.

Discord의 등록된 게임 목록은 Rich Presence와 별개입니다. 수동으로 등록한 게임 이름이 남아 있다면 Discord 설정에서 이름을 바꾸거나 다시 등록하세요.

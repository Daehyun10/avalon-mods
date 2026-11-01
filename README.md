# AVALON Mods

Client profiles and downloads for [AVALON Launcher](https://github.com/Daehyun10/Avalon-Launcher).

The launcher reads `distribution.json` at startup and before launching the game. Each entry in `servers` is a selectable profile with its own Minecraft version, mod loader, mods, Java requirements, and game directory.

Current profile: **AVALON — Minecraft 1.18.2 / Forge 40.2.0**. Enhance System is enabled by default and can be turned off in Settings > Mods. Its JAR is stored in `forgemods/optionalon`. The server address is `localhost:25565`; set a real server address in the profile before distributing it to other players.

Enhance System adds an **Enhance Mod Weapons** creative inventory tab, with a rapier icon. It contains the 13 custom weapons followed by 33 vanilla weapon/tool bases supported by the enhancement system. Use the creative inventory's page arrows to find mod tabs. Items start with their normal enhancement level; internal effect models are hidden.

The required **AVALON Client** mod sets the game window title to `AVALON` and applies the image provided by the owner as the window icon. It is client-only, so the server does not need to install it. Its source and the original JPEG are in `branding/`. This remains active when Enhance System is disabled.

Build it from `branding/` with Java 17 and `./gradlew.bat build`. When changing it, copy the reobfuscated JAR from `branding/build/libs/` to `servers/AVALON-Development-1.18.2/forgemods/required/`, then update its size and MD5 in `distribution.json` before publishing.

Discord Rich Presence is connected to the owner's **AVALON** application (`1566335017280471060`). The activity uses the owner's image from this repository as external artwork. Keep the Discord desktop client running when launching the game. See [Discord setup](discord-setup.md).

The enhancement and ability-selection windows fit the current GUI scale and viewport. Inventory items and button hitboxes use the same scale; resizing keeps the selected equipment and pending enhancement state.

## Update the current mod

Run from this repository after changing the source project:

```powershell
./scripts/update-mod.ps1 -ProjectPath 'C:/path/to/forge-1.18.2-40.2.0-mdk'
git add distribution.json servers
git commit -m "Update AVALON mod"
git push origin main
```

The script builds the project and updates the JAR, file size, and MD5 together. Players receive the updated file on their next game launch. Local source edits alone do not publish updates.

## Add mods and Minecraft versions

Use [Nebula](https://github.com/dscalzi/Nebula) to generate complete Forge or Fabric profiles. Set `ROOT` to this checkout and `BASE_URL` to `https://raw.githubusercontent.com/Daehyun10/avalon-mods/main/`.

For example, generate another profile with `generate server AVALON-Other 1.20.1 --forge 47.2.0`, place compatible mod JARs in that profile's `forgemods/required` or `forgemods/optionalon` directory, and run `generate distro`. Follow Nebula's installer instructions when preparing a new loader version. Commit the generated distribution and referenced files together. This example is not a preinstalled profile.

Required mods are always enabled. Optional mods can be toggled in the launcher's Mods settings. Use separate profile IDs for different modpacks and Minecraft versions so their configuration and saves have separate directories. A mod built for 1.18.2 must be ported and rebuilt before adding it to a 1.20.1 profile. Changing `minecraftVersion` alone does not make a mod compatible. Launcher updates are published separately through the launcher repository's version tags.

## 한국어

이 저장소는 런처가 읽는 프로필 목록과 모드 다운로드 파일을 관리합니다. 메인 화면의 프로필 이름을 눌러 등록된 모드팩과 Minecraft 버전을 선택합니다. 현재 등록된 버전은 1.18.2이며, 다른 버전은 해당 버전의 Forge 또는 Fabric과 호환 모드를 준비해 프로필로 추가하면 됩니다.

크리에이티브 보관함의 페이지 화살표를 넘기면 레이피어 아이콘의 **강화 모드 무기** 탭을 찾을 수 있습니다. 추가 무기 13종과 강화 스킬을 사용하는 기본 무기·도구 33종을 모았습니다. 아이템은 기본 강화 단계로 꺼내며, 연출 전용 모델은 표시하지 않습니다.

강화 창과 능력 선택 창은 GUI 배율과 화면 크기에 맞춰 자동으로 조절됩니다. 아이템 아이콘·버튼·클릭 영역을 같은 비율로 맞추며, 창 크기를 바꿔도 선택 장비와 강화 대기 상태를 유지합니다.

모드를 수정한 뒤 위 스크립트를 실행하고 JAR과 `distribution.json`을 함께 커밋·푸시하면 다음 게임 실행 시 갱신됩니다. 선택 모드는 설정의 Mods 항목에서 켜고 끌 수 있습니다. 서버 접속 주소는 아직 `localhost:25565`이므로 실제 서버 주소로 변경해야 합니다.

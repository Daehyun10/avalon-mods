# AVALON Mods

[AVALON Launcher](https://github.com/Daehyun10/Avalon-Launcher)에서 사용하는 모드와 실행 프로필을 관리합니다.

## 지원 환경

- Minecraft 1.18.2 / Forge 40.2.0
- Java 17
- 프로필: AVALON

## 포함 모드

**Enhance System**은 무기 강화와 전투 스킬을 추가합니다. 기본으로 활성화되며 런처의 설정 → Mods에서 켜고 끌 수 있습니다. 서버와 클라이언트에는 같은 버전의 JAR를 설치해야 합니다.

크리에이티브 보관함의 **강화 모드 무기** 탭에서 추가 무기 13종과 강화 가능한 기본 무기·도구 33종을 찾을 수 있습니다. 탭 아이콘은 레이피어이며, 다른 모드 탭은 페이지 화살표로 이동합니다.

강화 창과 능력 선택 창은 GUI 배율과 화면 크기에 맞춰 조절됩니다. 강화석은 보라색 수정 도트 이미지를 사용하며, 석궁에는 청록·금빛 탄착과 제압 구역 효과가 적용됩니다.

**AVALON Client**는 게임 창 제목과 아이콘을 설정하는 필수 클라이언트 모드입니다. 서버에는 설치하지 않습니다. Enhance System을 꺼도 유지됩니다.

Discord 활동 표시를 사용하려면 Discord 데스크톱 앱을 실행해 두세요. 자세한 설정은 [Discord 설정](discord-setup.md)을 참고하세요.

## 모드 배포

소스 프로젝트를 수정한 뒤 저장소 루트에서 실행합니다.

```powershell
./scripts/update-mod.ps1 -ProjectPath 'C:/path/to/forge-1.18.2-40.2.0-mdk'

git add distribution.json servers
git commit -m "Update AVALON mod"
git push origin main
```

스크립트는 JAR를 빌드하고 `distribution.json`의 파일 크기와 MD5를 갱신합니다. 런처는 다음 게임 실행 시 배포 파일을 확인합니다.

AVALON Client는 `branding/`에서 Java 17로 `./gradlew.bat build`를 실행합니다. 빌드한 JAR를 해당 프로필의 `forgemods/required/`에 넣고 `distribution.json`의 파일 경로·크기·MD5를 갱신합니다.

## 실행 프로필

`distribution.json`의 `servers` 항목이 런처의 프로필 목록입니다. 각 프로필은 Minecraft 버전, 모드 로더, 모드 목록과 게임 폴더를 따로 사용합니다.

- 필수 모드: `forgemods/required/`
- 선택 모드: `forgemods/optionalon/`
- 현재 서버 주소: `localhost:25565`

외부에 배포할 때는 실제 서버 주소를 설정하세요. 다른 Minecraft 버전을 추가하려면 해당 버전의 Forge 또는 Fabric과 호환 모드를 준비해야 합니다.

프로필 생성에는 [Nebula](https://github.com/dscalzi/Nebula)를 사용할 수 있습니다. 저장소 경로를 `ROOT`, 배포 주소를 `BASE_URL`로 설정합니다.

```text
BASE_URL=https://raw.githubusercontent.com/Daehyun10/avalon-mods/main/
```

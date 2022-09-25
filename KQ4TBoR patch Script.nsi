!include MUI2.nsh
Unicode true
RequestExecutionLevel user
!define MUI_ICON "icon.ico"
!define MUI_UNICON "uninstall.ico"

!define UNINSTALLER_NAME "KQ4TBoR_uninstaller.exe"
!define MUI_COMPONENTSPAGE_NODESC

Function .onVerifyInstDir
    IfFileExists "$INSTDIR\resource.map" +2 0
        abort
FunctionEnd

Name "KQ4:TBoR"

BrandingText "King's Quest IV: The Breasts of Rosella"
!define MUI_WELCOMEPAGE_TITLE "KQIV: The Breasts of Rosella"
!define MUI_WELCOMEPAGE_TEXT "CONTENT WARNING: This mod contains adult situations, nudity, graphic violence, suicide, drug references and more. It is intended as parody. Players should expect the raunch level to be not much worse than an Al Lowe game (though, the writing certainly will be). It does not attempt to deliberately offend, but may do so reguardless.${U+000A}${U+000A}This mod preserves the original KQ4 game solutions, but adds many new ones. Much of the content is initially hidden, so explore and try things differently than you normally would in KQIV."
!define MUI_WELCOMEFINISHPAGE_BITMAP "welcome.bmp"
!define MUI_UNWELCOMEFINISHPAGE_BITMAP "welcome.bmp"

!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_COMPONENTS #enable options
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_PAGE_FINISH
!insertmacro MUI_UNPAGE_WELCOME
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES
!insertmacro MUI_UNPAGE_FINISH
!insertmacro MUI_LANGUAGE "English"
OutFile "KQ4-TBoR-Patcher.exe"

DirText "NOTICE: This patch requires a retail copy of King's Quest IV v1.006.004 and you must locate your King's Quest 4 game folder before continuing (e.g. C:\GOG Games\Kings Quest 4). The GOG and Steam versions are both compatible, but if needed verify your version in-game under the ABOUT menu option.${U+000A}${U+000A}The mod includes an uninstaller, but make a backup your orginal game files before installing. Regrettably, due to HEAP crashes these patch files are NOT compatible with DOSBox." "Select your retail KQ4 game folder:"


Section "-Update file"
    ; Set output path to the installation directory
    SetOutPath $INSTDIR
    File /r "installerData\"
    SetOutPath $INSTDIR\PATCHES
    File uninstall.ico
    File icon.ico

    WriteUninstaller $INSTDIR\${UNINSTALLER_NAME}

    WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\KQIVTBOR" \
                     "DisplayName" "KQIV - The Breasts of Rosella"
    WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\KQIVTBOR" \
                     "UninstallString" "$INSTDIR\${UNINSTALLER_NAME}"
   
SectionEnd

Section /o "Add Start Menu Items" start_id
    # Shortcuts
    SetOutPath $INSTDIR
    CreateShortCut "$INSTDIR\PATCHES\KQIV - TBoR.lnk" "$INSTDIR\PATCHES\scummvm\scummvm.exe" "-c .\PATCHES\kq4tbor.ini kq4sci" "$INSTDIR\PATCHES\icon.ico"
    CreateShortCut "$INSTDIR\PATCHES\UNINSTALL - KQ4-TBoR.lnk" "$INSTDIR\KQ4TBoR_uninstaller.exe" "" "$INSTDIR\PATCHES\uninstall.ico"
  
    CreateDirectory "$SMPROGRAMS\KQIV - The Breasts of Rosella"
    CopyFiles "$INSTDIR\PATCHES\KQIV - TBoR.lnk" "$SMPROGRAMS\KQIV - The Breasts of Rosella\"
    CopyFiles "$INSTDIR\PATCHES\UNINSTALL - KQ4-TBoR.lnk" "$SMPROGRAMS\KQIV - The Breasts of Rosella\"
SectionEnd

Section /o "Add Desktop Shortcut" desktop_s_id
    CopyFiles "$INSTDIR\PATCHES\KQIV - TBoR.lnk" "$DESKTOP\"
    Delete "$INSTDIR\PATCHES\KQIV - TBoR.lnk"
SectionEnd


Section "Uninstall"
    SetOutPath "$TEMP"
    RMDir /r "$SMPROGRAMS\KQIV - The Breasts of Rosella"
    Delete "$DESKTOP\KQIV - TBoR.lnk"
    DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\KQIVTBOR"
    RMDir /r /REBOOTOK "$INSTDIR\PATCHES\"
    Delete "$INSTDIR\${UNINSTALLER_NAME}"
SectionEnd
 
Function .onInit
    SectionSetFlags ${start_id} 1
    SectionSetFlags ${desktop_s_id} 0
FunctionEnd


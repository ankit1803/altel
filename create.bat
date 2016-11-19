set JAVA_HOME=C:\Program Files (x86)\Java\jdk1.7.0_40\
set ANT_HOME=C:\Users\admin\Downloads\apache-ant-1.9.7-bin\apache-ant-1.9.7

set PATH==%JAVA_HOME%\bin;%ANT_HOME%\bin;c:\mingw\bin;c:\MinGW\msys\1.0\bin;c:\Program Files (x86)\WiX Toolset v3.10

set BZ2_HOME=D:\altel\src\native\windows\setup
set LZMA_HOME=C:\MinGW\xz
set MINGW_HOME=C:\MinGW
start /B /LOW /WAIT ant build-installation-wix
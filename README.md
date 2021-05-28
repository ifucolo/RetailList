# RetailList

![work flow](https://github.com/ifucolo/RetailList/actions/workflows/runOnGitHub.yml/badge.svg)


### Android Studio Version to run the project
###### Android Studio Arctic Fox | 2020.3.1 Beta 2

### Main Apis used:
###### Compose, ViewModel, Coroutines, Flow, Coil, Spek, Mockito, Navigation component.

### Tips:
###### As this project uses localhost to be able to use local host on android you should run two commands when you connect your emulator or device(those commands uses adb tools where you can find on Android Library/sdk/platform-tools):
###### ./adb reverse tcp:3001 tcp:3001
###### ./adb reverse tcp:3002 tcp:3002

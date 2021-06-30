# WEATHER APPLICATION

## Features

- Getting current weather, list of forecast weathers
- Scaling text 
- Speech to text

## Technicals

- Applied MVVM architecture, LiveData
- AndroidInstrument Test, UnitTest
- Room Database

## Explanation
### Software Development principles
5 principles of Software development that I'm using are S.O.L.I.D

### Design pattern

In this app, I'm applied 3 pattern are:

- Repository Pattern
- Singleton Pattern
- Factory Pattern

### Practices

Using the newest SDK, MAD, Material Design. 
Applied Dependency Injection
Applied MVVM (Google Clean Architecture for android) 
![image info](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

## Folder Structure

- Adapter: contains all adapters of recyclerviews 
- Model: contains all objects need to use while appilication running (Ex: WeatherResponse, WeatherItem, etc...)
- Network: contains networks handlers (retrofit, endpoint api, request body, etc...)
- Repository: contains repositories to get data from service or room database
- Ui: contains screens of application 
- Util: contains tools for wrapping, pre-processing, and classifying data.
- Viewmodel: contains viewmodel, receiving events, getting data from repositories and return data to Ui

## Libraries, module, and frameworks


## How to run

There're 2 ways you can install and run the app

- Clone this GIT repository, open Android Studio, then click Run to intsall.
- You can get app.apk file in this repository then install via ADB or copy to phone via USB.

## Checklist

- [x] Programming language: Kotlin
- [x] Application's architecture: MVVM
- [x] LiveData
- [x] UI looks like attachment
- [ ] Unit test
- [x] Exception Handling
- Secure App from:
    - [ ] Decompile APK
    - [ ] Rooted device
    - [ ] Data transmission via network
    - [ ] Encryption for sensitive information
- Accessibility for Disability Supports:
    - [ ] Talkback
    - [x] Scaling Text
- [x] Diagrams





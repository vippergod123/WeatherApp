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

- Using the newest SDK, MAD, Material Design. 
- Applied Dependency Injection
- Applied MVVM (Google Clean Architecture for android) 
![image info](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)
- Applied clean architecture 
![image info](https://github.com/android10/Sample-Data/blob/master/Android-CleanArchitecture/clean_architecture.png?raw=true)
## Folder Structure: 

Have 3 main module: App, Domain, Data, represent for solving specific tasks, have 1 main folder structure

- DI: contains all adapters of recyclerviews 
- Model (entity): contains all objects need to be used 
    -  Object receivce from service (in Data module)
    -  Object be handled after received from service or save, or get from ROOM database (entity in Domain module)
- Repository: contains repositories to get data from service or room database
- Util: contains tools for wrapping, pre-processing, and classifying data.


And each module has some specific folder of their own:
- App:
    - Adapter: contains all adapters of recyclerviews 
    - Viewmodel: contains viewmodel, receiving events, getting data from repositories and return data to Ui
    - Ui: contains screens of application 
- Domain:
    - usecase: contains behaviour app should be doing (GetWeatherForecast, GetCurrentWeather, v.v...)
    - exception: contains self-declaring exceptions 
- Data:
    - source: contains Endpoint of API, or ROOM database 
    

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
- [x] Unit test
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





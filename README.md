# Practical-7: JSON API & SQLite Database

## AIM

Develop an Android application that retrieves **Person data in JSON format from an Internet API** and stores/displays the data using an **SQLite database**.

## Features

* Fetch Person data from JSON API
* Parse JSON data
* Display data using `RecyclerView`
* Store retrieved data in SQLite
* Display Person details
* Pass `Person` object between Activities
* Show location/map data using latitude and longitude

## Person Details

* ID
* First Name
* Last Name
* Phone Number
* Email
* Address
* Latitude
* Longitude

## Concepts

`JSON`, `RecyclerView`, `SQLite`, `HttpURLConnection`, `CoroutineScope`, `Serializable`, `Intent`, `Adapter`, and Internet Permission.

## Permission

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## Main Components

```text
MainActivity
    ↓
HttpRequest
    ↓
JSON API
    ↓
JSON Parsing
    ↓
Person Objects
    ↓
SQLite Database
    ↓
RecyclerView
```

## Result

Successfully retrieved Person data from an online JSON API, stored the data in **SQLite**, and displayed it using **RecyclerView**.

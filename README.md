#  Ivory Ledger — Android SQLite Student Management App

## Overview

**Ivory Ledger** is a simple Android application developed as part of:

> **LAB 15: SQLite and Android — Simple Student Management System**  
Course: Mobile Programming — Android with Java

The application demonstrates how to build a complete **CRUD system (Create, Read, Delete)** using:
- SQLite local database
- Clean service-layer architecture
- Android UI with animations and interactions

---

##  Objectives

This project aims to:

- Understand SQLite database integration in Android
- Implement a local persistent storage system
- Apply CRUD operations (Insert, Search, Delete)
- Separate concerns using Model / Service / UI layers
- Connect database logic with Android UI events
- Validate operations using Logcat

---

##  Architecture

The application follows a simple layered architecture:

UI Layer (MainActivity)
↓
Service Layer (ScholarVaultService)
↓
Database Helper (IvoryDatabaseGateway)
↓
SQLite Database

---

###  Application Demo (UI Interaction)

The video below shows the **Ivory Ledger application workflow**, including:

- Enrolling a new scholar  
- Searching for a scholar by ID  
- Removing a scholar from the database  
- Real-time UI feedback with animations  

 **Demo Video:**

https://github.com/user-attachments/assets/ac4c0550-714d-4533-ae05-a86ca4018aa5

---

###  Logcat Output (Database Verification)

The screenshot below shows the **Android Logcat output**:

<img width="1773" height="355" alt="Screenshot 2026-04-14 181839" src="https://github.com/user-attachments/assets/dad10571-55b9-49d6-82d6-36945afd947a" />


---


##  Project Structure

###  Model Layer

#### EnrolledScholar.java

Represents a scholar entity stored in the database.

Fields:
- scholarId (int)
- familyName (String)
- givenName (String)

---

###  Database Layer

#### IvoryDatabaseGateway.java

This class extends SQLiteOpenHelper and is responsible for:

- Creating the database
- Creating the table `scholars`
- Handling database upgrades

Database Schema:

CREATE TABLE scholars (
    scholarId INTEGER PRIMARY KEY AUTOINCREMENT,
    familyName TEXT,
    givenName TEXT
);

---

###  Service Layer

#### ScholarVaultService.java

Handles all CRUD operations:

- enrollScholar() → Insert a scholar
- fetchScholarById() → Search scholar by ID
- fetchAllScholars() → Retrieve all scholars
- amendScholar() → Update scholar
- removeScholar() → Delete scholar

This layer isolates database logic from the UI.

---

###  UI Layer

#### MainActivity.java

Responsible for:

- Handling user input
- Button click events
- Connecting UI with database service
- Running animations (buttons + result display)

Features:
- Enroll Scholar
- Search Scholar by ID
- Remove Scholar
- Smooth UI animations

---

##  UI Design

Built using XML with:

- EditText → input fields
- Button → actions
- TextView → result display
- ScrollView → responsive layout

Main Actions:
- ENROLL SCHOLAR
- SEARCH
- REMOVE

---

##  Application Workflow

###  Enroll Scholar

1. Enter family name and given name
2. Click ENROLL SCHOLAR
3. Data is saved into SQLite database

---

###  Search Scholar

1. Enter Scholar ID
2. Click SEARCH
3. Result is displayed on screen

---

###  Remove Scholar

1. Enter Scholar ID
2. Click REMOVE
3. Record is deleted from database

---

## Testing (Logcat)

All inserted records are printed in Logcat:

Log.d("Scholar", familyName + " " + givenName);

---

##  Technologies Used

- Java
- Android SDK
- SQLite
- XML (UI Design)
- AndroidX AppCompat

---

##  Features Summary

✔ SQLite database integration  
✔ Scholar model implementation  
✔ CRUD operations (Create, Read, Delete)  
✔ Service-based architecture  
✔ Clean UI design  
✔ Basic animations  
✔ Logcat debugging support  

##  Conclusion

This project demonstrates a complete Android SQLite application with:

- Clean architecture (Model → Service → UI)
- Functional CRUD operations
- Local database persistence
- Interactive user interface

It is a solid foundation for more advanced Android development projects.

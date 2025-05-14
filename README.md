# 💳 Banking Management System using Java + Oracle JDBC

This is a mini project for the subject **"Introduction to Database"**, developed using **Java**, **Oracle SQL**, and **JDBC**. It is a console-based application that mimics basic banking operations like managing customers, accounts, loans, deposits, and withdrawals.

---

## 📂 Folder Structure

BankingManagementSystem/
├── src/
│ └── BankingApp.java
├── sql/
│ └── banking_db_setup.sql
├── lib/
│ └── ojdbc8.jar
├── README.md
└── .gitignore

---

## 🛠 Features Implemented

1. View all Customer Records
2. Add a Customer
3. Delete a Customer
4. Update Customer Information (name, phone, city)
5. Show Account Details (with branch info)
6. Show Loan Details (with branch info)
7. Deposit Money to Account
8. Withdraw Money from Account
9. Exit the Program

---

## 🧾 SQL Database Setup

All SQL queries are inside the file:
/sql/banking_db_setup.sql

It includes:
- Table creation: `Customer`, `Account`, `Loan`, `Branch`
- Insert sample data

You can run this directly in **Oracle SQL Developer**.

---

## 💡 Technologies Used

- Java 8+ or above
- Oracle Database (11g XE or newer)
- JDBC Driver (ojdbc8.jar)
- Eclipse IDE (or any other Java IDE)

---

## 🖥 How to Run the Project

### ✅ 1. Setup Oracle SQL
- Install Oracle XE Database
- Open **SQL Developer** and run the queries in `banking_db_setup.sql`
- Make sure to remember your Oracle DB username and password

---

### ✅ 2. Setup Java Project in Eclipse
1. Create a new Java Project (e.g., `BankingManagementSystem`)
2. Add `ojdbc8.jar`:
   - Right-click project → **Build Path → Configure Build Path → Libraries tab**
   - Click **Add External JARs** and add `/lib/ojdbc8.jar`
3. Place the code from `BankingApp.java` into your `src/` folder

---

### ✅ 3. Edit Database Credentials
In `BankingApp.java`, update:
```java
String username = "your_oracle_username";
String password = "your_oracle_password";
```
---

### ✅ 3. Run the Program
  - Right-click BankingApp.java → Run As → Java Application
  - A menu-driven console interface will appear
  - Use numbers 1–9 to interact with the system

---

### 🚀 Sample Output:
![image](https://github.com/user-attachments/assets/b4636b49-7799-4a00-ab16-9efe1258211a)

### 🔒 Note:
  - Make sure Oracle DB is running before starting the program
  - Handle all database exceptions gracefully
  - Do not use hardcoded credentials in production

---

### 📄 License
This project is made for academic purposes under the course CSE 3151 – Introduction to Database. Feel free to fork and modify for learning or personal use.

---

### 🤝 Acknowledgements
Thanks to the faculty and the lab manual guide that helped structure this learning project.

---

### 👨🏽‍💼 MADE BY PRAKASH BEHURIA
-INSTA - deadcoil.dumb
-LINKEDIN - https://www.linkedin.com/in/prakash-behuria-167946306?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app

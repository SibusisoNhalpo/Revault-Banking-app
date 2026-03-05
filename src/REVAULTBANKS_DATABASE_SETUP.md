# RevaultBanks Database Setup

## Overview

RevaultBanks uses a MariaDB/MySQL database called `revaultbanks`. The database schema is provided in `src/revaultbanks.sql`. The database starts empty—all user data is created through the application.

## Prerequisites

- **MariaDB 10.4.32** or **MySQL 5.7+**
- Command-line access or a GUI client (phpMyAdmin, MySQL Workbench, etc.)

## Database Schema

The `revaultbanks` database contains three tables:

**users** — Stores user account information
- `id` (INT, Primary Key, Auto-increment)
- `name` (VARCHAR 100)
- `surname` (VARCHAR 100)
- `password` (VARCHAR 100) — **Must be hashed, never stored in plain text**
- `phone` (VARCHAR 100)
- `email` (VARCHAR 100)
- `account_number` (VARCHAR 20) — Generated account number
- `balance` (VARCHAR 20) — User's wallet balance

**beneficiaries** — Stores beneficiary accounts for transfers
- `id` (INT, Primary Key, Auto-increment)
- `user_email` (VARCHAR 100) — Email of the user who added this beneficiary
- `name` (VARCHAR 100) — Beneficiary name
- `account_number` (VARCHAR 50)
- `branch_code` (VARCHAR 50)
- `bank_name` (VARCHAR 100)

**transactions** — Stores all transaction history
- `id` (INT, Primary Key, Auto-increment)
- `email` (VARCHAR 255) — User's email
- `date` (TIMESTAMP) — Transaction timestamp
- `description` (VARCHAR 255) — Transaction description
- `type` (VARCHAR 50) — Transaction type (DEPOSIT, WITHDRAW, PAYBILL, TRANSFER, etc.)
- `amount` (DOUBLE) — Transaction amount
- `balance` (DOUBLE) — Balance after transaction

## Initial Data

The database starts completely empty. All users, beneficiaries, and transactions are created through the application. There are no default accounts or test data.

## Setup Instructions

### Option 1: Command Line (Recommended)

1. Open your terminal/command prompt
2. Log in to MariaDB/MySQL:
   ```bash
   mysql -u root -p
   ```
   (Enter your root password when prompted)

3. Create the database and import the schema:
   ```bash
   mysql -u root -p < src/revaultbanks.sql
   ```
   Or if you're already in the MySQL console:
   ```sql
   CREATE DATABASE revaultbanks;
   USE revaultbanks;
   SOURCE src/revaultbanks.sql;
   ```

4. Verify the setup:
   ```sql
   USE revaultbanks;
   SHOW TABLES;
   ```

You should see three tables: `users`, `beneficiaries`, and `transactions`

### Option 2: GUI Client (phpMyAdmin, MySQL Workbench, etc.)

1. Open your database GUI tool
2. Create a new database named `revaultbanks`
3. Right-click the database and select "Import" or "Execute SQL Script"
4. Select the `src/revaultbanks.sql` file and execute

## Database Connection Configuration

Configure your Java application to connect to the database using these details:

- **Host:** `localhost` (or your server IP)
- **Port:** `3306` (default)
- **Database:** `revaultbanks`
- **Username:** `root` (or your configured user)
- **Password:** Your MySQL/MariaDB root password

Update your application's database configuration file (e.g., `application.properties`, `application.yml`, or `pom.xml`) with these credentials.

Example JDBC URL:
```
jdbc:mysql://localhost:3306/revaultbanks
```

## Security Notes

⚠️ **Critical Security Requirements:**

- **Password Hashing:** Passwords must be hashed using bcrypt, Argon2, or similar algorithms. Never store passwords in plain text.
- **Environment Variables:** Store database credentials in environment variables or a secure configuration file, not hardcoded.
- **HTTPS Only:** The application must use HTTPS for all transactions to protect sensitive financial data.
- **Input Validation:** Validate and sanitize all user input to prevent SQL injection.
- **Access Control:** Implement proper role-based access control for sensitive operations.
- **Account Numbers:** Generate unique account numbers for each user. Do not expose internal IDs.

## Resetting the Database

To reset the database to its initial empty state:

```bash
mysql -u root -p -e "DROP DATABASE revaultbanks;"
mysql -u root -p < src/revaultbanks.sql
```

Or in the MySQL console:
```sql
DROP DATABASE revaultbanks;
SOURCE src/revaultbanks.sql;
```

## Troubleshooting

**"Access denied for user 'root'@'localhost'"**
- Verify your MySQL/MariaDB service is running
- Check your username and password are correct

**"Database already exists"**
- The database exists from a previous setup. Either drop it first or use the import command to overwrite

**"Source file not found"**
- Ensure the path to `revaultbanks.sql` is correct relative to where you're running the command
- Use an absolute path if needed: `SOURCE /full/path/to/src/revaultbanks.sql;`

## Transaction Types

The application supports the following transaction types:

- **DEPOSIT** — Money added to wallet
- **WITHDRAW** — Money withdrawn from wallet
- **PAYBILL** — Bill payment (electricity, DSTV, etc.)
- **TRANSFER** — Transfer to another user's account
- **BENEFICIARY_TRANSFER** — Transfer to saved beneficiary account

## Next Steps

After importing the database:
1. Update your Java application's connection properties
2. Test the database connection with a simple query
3. Implement user registration with proper password hashing
4. Set up transaction logging for audit trails
5. Test all transaction types (deposits, withdrawals, bill payments)
6. Implement proper error handling for database operations

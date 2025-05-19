# 💰 Dormitory Expense Manager - Web Application ✨

✨ Dormitory Expense Manager is a web application designed to simplify expense tracking and financial management for students living in dormitories or shared accommodations. The app provides an intuitive interface for managing room communities, tracking personal and shared expenses, and generating financial statistics.

---

## Features

### 👥 Room Management
- **Create Room Communities**: Set up multiple room groups with unique IDs
- **Member Management**: Add, remove, and manage room members
- **Admin Controls**: Transfer room ownership and manage permissions

### 💸 Expense Management
#### 📝 Transaction Tracking
- **Small Transactions**: Record daily purchases and personal expenses
- **Fixed Costs**: Manage recurring expenses like rent, utilities, etc.
- **Payment Requests**: Create and track payment deadlines for room members

#### 📊 Financial Statistics
- **Personal Spending**: Track individual spending patterns
- **Room Averages**: Compare personal expenses with room averages
- **Monthly Reports**: View spending trends across different months
- **Balance Calculation**: Automatically calculate who owes what to maintain equal contributions

#### 🔔 Notifications
- Payment deadline reminders
- Pending join requests alerts
- Balance updates

### 🔐 User Management
- User registration and login
- Profile management with avatar upload
- Security question setup for account recovery
- Password management

---

## 🔧 Technologies Used

### Frontend
- **📱 jQuery**: For interactive UI components
- **🎨 HTML/CSS**: For structure and styling

### Backend
- **☕ Java Spring Boot**: For building a robust RESTful API
- **📊 MySQL**: Relational database for data persistence
- **🔄 Maven**: For dependency management and build automation

---

## 🌍 System Architecture

The application follows a classic Spring MVC architecture:
1. **📱 Client Layer**: Browser-based interface communicating with REST endpoints
2. **🛠️ Controller Layer**: Handles HTTP requests and routes to appropriate services
3. **💼 Service Layer**: Contains business logic and transaction management
4. **📁 Repository Layer**: Data access layer interacting with the database
5. **📊 Database**: MySQL for persistent data storage

---

## 📊 Entity Relationship Diagram

Key entities in the system include:
- User: Stores user account information
- Room: Represents a dormitory room or living space
- Member of Room: Maps users to rooms they belong to
- Small Transaction: Tracks daily expenses
- Fee with Deadline: Manages recurring costs with due dates
- Join Room Request: Handles room membership requests

---

## ⚙️ Installation and Setup

### Prerequisites
- Java Development Kit (JDK)
- MySQL Database
- Maven
- Git

### Setup Instructions
1. Clone the repository:
   ```bash
   git clone [https://github.com/phongnickchinh/CNPM-20231]
   cd CNPM-20231
   ```

2. Configure MySQL database:
   ```bash
   # Create a database named 'quanlynhatro'
   mysql -u root -p -e "CREATE DATABASE quanlynhatro CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
   ```

3. Configure application properties:
   - Update `src/main/resources/application.properties` with your database credentials

4. Build the project:
   ```bash
   mvn clean install
   ```

5. Run the application:
   ```bash
   mvn spring-boot:run
   ```

6. Access the web application:
   - Open your browser and navigate to `http://localhost:8080`

---

## 🔄 Usage
- Register a new account or login with existing credentials
- Create a room or join an existing room using a room ID
- Start tracking your expenses by adding transactions
- Set up fixed costs with deadlines for shared expenses
- View statistics to understand spending patterns
- Use room management features to organize members and permissions

---

## 👥 Contributors
- [List project team members here]

---

## 📄 License
[Include license information if applicable]

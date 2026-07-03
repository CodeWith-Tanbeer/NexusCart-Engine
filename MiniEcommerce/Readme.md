# NexusCart-Engine: Production-Grade Multi-Tier Decoupled Transactional System

A high-performance, architecture-driven asynchronous e-commerce transaction processing engine engineered using Core Java (JavaSE-21), native JDBC protocols, and a relational MySQL persistence tier. This system features a completely decoupled architecture, cleanly splitting a styled HTML5/CSS3/JavaScript frontend interface from an underlying business validation layer and data repository.

---

## 1. Architectural Philosophy & Directory Topography

The software is engineered entirely around the **Single Responsibility Principle (SRP)** and **Separation of Concerns (SoC)**. Instead of creating a monolithic codebase where structural queries, UI presentation inputs, and validation loops are tightly bound, this engine isolates each functional concern into dedicated systemic layers.

### Project Repository Blueprint
The workspace is cleanly divided into decoupled client and server directories:

```text
NexusCart-Project/  (Main Repository Root)
├── EcommerceFrontend/     (Presentation Tier - Running on Client Web Browser)
│   ├── index.html         (Semantic Structural Layout Markup)
│   ├── style.css          (Aesthetic Layer Styles & Theme Grids)
│   └── script.js          (Asynchronous Fetch Client Engine Network Driver)
│
├── MiniEcommerce/         (Core API Server Tier - Running inside Java Environment)
│   ├── JRE System Library [JavaSE-21]
│   ├── Referenced Libraries
│   │   └── mysql-connector-j-9.7.0.jar (Low-level TCP network driver binary)
│   └── src
│        └── com
│             └── ecommerce
│                  ├── Main.java         (Java Server Container Sockets / HTTP Engine)
│                  ├── OrderService.java (Core Business Logic Isolation Firewall)
│                  ├── ProductDAO.java   (Data Access Object - Repository Query Layer)
│                  └── DBUtility.java    (Centralized Environment Variable Gateway)
│
└── README.md              (Comprehensive System Documentation)
```

---

## 2. Technical Diagrams & Structural Design

### Unified Modeling Language (UML) Dependency Map
This class blueprint maps class fields, parameters, and internal data transmission channels downstream across the server structure:

```text
+────────────────────────────────────────────────────────┐
│                        Main                            │
+────────────────────────────────────────────────────────┤
│ + main(args: String[]) : void                          │
│ + static class CheckoutHandler implements HttpHandler  │
+────────────────────────────────────────────────────────┘
                           │
                           ▼ (Extracts Stream Payload & Instantiates)
+────────────────────────────────────────────────────────┐
│                    OrderService                        │
+────────────────────────────────────────────────────────┤
│ - dao: ProductDAO                                      │
+────────────────────────────────────────────────────────┤
│ + processOrder(userId: int, prodId: int, qty: int)     │
│   : String                                             │
+────────────────────────────────────────────────────────┘
                           │
                           ▼ (Evaluates Logical System Constraints)
+────────────────────────────────────────────────────────┐
│                     ProductDAO                         │
+────────────────────────────────────────────────────────┤
│ + getStock(productId: int) : int                       │
│ + getPrice(productId: int) : int                       │
│ + getUserBalance(userId: int) : int                    │
│ + executeTransaction(uId: int, pId: int, q: int,       │
│                      cost: int) : void                 │
+────────────────────────────────────────────────────────┘
                           │
                           ▼ (Requests Sockets & Manages Rollbacks)
+────────────────────────────────────────────────────────┐
│                     DBUtility                          │
+────────────────────────────────────────────────────────┤
│ - URL, USER, PASSWORD : String                         │
+────────────────────────────────────────────────────────┤
│ + getConnection() : Connection                         │
+────────────────────────────────────────────────────────┘
```

### Entity-Relationship Diagram (ERD) Schema
The backend persistence layer maps relational data fields inside the active database schema:

```text
  DATABASE LOGICAL SCHEMA: mini_ecommerce
  
  TABLE: products (Warehouse Inventory catalog)
  +───────────────────┬───────────────────┬──────────────────────────────────┐
  │ Column Name       │ Data Type         │ Key / Constraint Details         │
  +───────────────────┼───────────────────┼──────────────────────────────────+
  │ id                │ INT               │ PRIMARY KEY (Unique Identifiers) │
  │ name              │ VARCHAR(50)       │ Non-Null Product Description     │
  │ price             │ INT               │ Core Base Cost Metric (INR)      │
  │ stock             │ INT               │ Real-Time Count tracking Volume  │
  +───────────────────┴───────────────────┴──────────────────────────────────┘

  TABLE: users (Customer Financial Profiles)
  +───────────────────┬───────────────────┬──────────────────────────────────┐
  │ Column Name       │ Data Type         │ Key / Constraint Details         │
  +───────────────────┼───────────────────┼──────────────────────────────────+
  │ id                │ INT               │ PRIMARY KEY (Unique Customer ID) │
  │ name              │ VARCHAR(50)       │ Legal Identity Record String     │
  │ balance           │ INT               │ Isolated Liquid Wallet Funds     │
  +───────────────────┴───────────────────┴──────────────────────────────────┘
```

---


## 🔄 End-to-End Data & Project Flow

When you click the action button on your web page, data flows through the application layers and database partitions in this exact sequence:

```text
  [ FRONTEND TIER ]                 [ CORE BACKEND REPOSITORY ENGINE ]              [ RELATIONAL DATABASE ]
┌────────────────────┐             ┌──────────────────────────────────┐            ┌───────────────────────┐
│ Form Inputs Entered│             │ Main.java (Port 8080 Socket)     │            │ MySQL Server          │
│         │          │             │         │ (Decodes Parameters)   │            │ (Port 3306)           │
│         ▼          │             │         ▼                        │            │                       │
│ script.js (Fetch)  │ ──────────► │ OrderService.java (Validation)   │            │                       │
│                    │  HTTP POST  │         ├─► Checks Inventory ────┼──────────► │ Queries stock row     │
│                    │             │         ├─► Checks Liquidity ────┼──────────► │ Queries balance row   │
│                    │             │         ▼                        │            │                       │
│ Updates Web Screen │ ◄────────── │ ProductDAO.java                  │            │ Locks & Updates Rows  │
│ (Dynamic Text DOM) │  Text Echo  │ (AutoCommit = False)             │ ─────────► │ (Commit or Rollback)  │
└────────────────────┘             └──────────────────────────────────┘            └───────────────────────┘
```

### 1. The Presentation Phase (Frontend Web Page)
* You fill out the checkout fields (**User ID**, **Product ID**, and **Quantity**) on the web screen and click **Execute System Transaction**.
* JavaScript (`script.js`) captures those numbers instantly, joins them into a comma-separated text string (`"101,1,1"`), and shoots a network `POST` request to your running Java program on port `8080`.

### 2. The Verification Phase (Java Engine Middleware)
* `Main.java` catches the incoming network packet and injects approval codes (**CORS headers**) so your web browser is legally allowed to talk to your backend.
* It hands the numbers over to `OrderService.java`. The service layer reaches out to your database to cross-verify the rules:
  1. **Stock Check**: *Is the requested quantity available in the warehouse table?*
  2. **Wallet Check**: *Does the user record exist, and do they have enough money in their balance to pay for the total price?*

### 3. The Persistence Phase (MySQL Database Storage)
* If all rules pass, the data shifts to `ProductDAO.java`. The DAO disables default auto-saving by executing `con.setAutoCommit(false);`. This locks the required rows so other users cannot conflict with them.
* It fires a dual-update statement to the **MySQL Server** on port `3306`:
  * Deducts items from the `products` stock column.
  * Deducts the total cost from the `users` balance column.
* **Commit/Rollback Safety**: If both updates execute flawlessly, `con.commit();` saves the changes permanently to your hard disk files. If a network line drops mid-way, `con.rollback();` runs instantly, wiping the temporary changes clean to prevent mismatched data.

### 4. The Response Phase (UI Feedback)
* The Java server echoes back a text statement (e.g., `"Order Successful! Database states adjusted securely."`), and JavaScript dynamically displays it directly on your screen without needing to reload the webpage.
# Restaurant Menu Management System (RMMS)

A Spring Boot REST API that lets restaurant managers authenticate with Google, manage their menus and menu items, and share each menu publicly via a unique link — no login required for customers.

---

## What We Built

We built a clean, layered backend that handles the full lifecycle of a restaurant's digital menu:

- Managers sign in with their Google account — we automatically create their profile on first login
- Each manager gets their own isolated set of menus, fully separated from other users
- Every menu gets a public shareable URL that anyone can view without logging in
- All write operations are ownership-protected — managers can only edit what belongs to them

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| Security | Spring Security + Google OAuth 2.0 |
| Persistence | Spring Data JPA / Hibernate |
| Database | H2 (development) → Oracle 19c (production) |
| Build Tool | Maven |

---

## Project Structure

```
src/main/java/Backend/RestaurantManagement/
├── config/        # Spring Security & OAuth2 configuration
├── controller/    # REST controllers — Menu, MenuItem, Public
├── service/       # Business logic — ownership checks, CRUD
├── repository/    # Spring Data JPA interfaces
├── model/         # JPA entities — User, Menu, MenuItem
├── dto/           # Request and response DTOs
└── exception/     # Custom exceptions & global error handler
```

---

## Database Schema

We designed three tables to support the full feature set:

**APP_USER** — stores manager accounts (email is the primary key)

**MENU** — each menu belongs to one manager via email foreign key

**MENU_ITEMS** — each item belongs to one menu and is deleted automatically when the parent menu is removed

---

## API Endpoints

### Authentication

Google OAuth2 is handled automatically by Spring Security.

| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/oauth2/authorization/google` | Public | Redirect to Google login |

### Menus (Protected)

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/menus` | Get all menus owned by the logged-in manager |
| POST | `/api/menus` | Create a new menu |
| GET | `/api/menus/{id}` | Get a single menu with all its items |
| PUT | `/api/menus/{id}` | Update a menu's title or category |
| DELETE | `/api/menus/{id}` | Delete a menu and all its items |

### Menu Items (Protected)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/menus/{menuId}/items` | Add an item to a menu |
| PUT | `/api/menus/{menuId}/items/{itemId}` | Update a menu item |
| DELETE | `/api/menus/{menuId}/items/{itemId}` | Delete a single item |

### Public Menu

| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/menu/{id}` | Public | View any menu — no login required |

---

## Error Handling

We return consistent JSON error responses across the entire API:

```json
{
  "status": 403,
  "message": "You cannot edit this menu."
}
```

| Status | Trigger |
|---|---|
| 400 | Invalid or missing request fields |
| 401 | Request made without being logged in |
| 403 | Attempting to modify another manager's menu |
| 404 | Menu or item does not exist |
| 500 | Unexpected server error |

---

## Getting Started

### Prerequisites

- Java 17
- Maven (or use the included `mvnw` wrapper)
- Google OAuth2 credentials (created in Google Cloud Console)

### Configuration

Open `src/main/resources/application.properties` and fill in your Google credentials:

```properties
spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_CLIENT_SECRET
```

### Run

```bash
./mvnw spring-boot:run
```

The API starts at `http://localhost:8080`.

The H2 console for development inspection is at `http://localhost:8080/h2-console`.

---

## Security Model

We enforce one strict rule: **you can only modify menus you own.**

Every protected request is validated against the authenticated user's Google email. If the email on the menu doesn't match the logged-in user, we return `403 Forbidden`. Public read access on `GET /menu/{id}` is open to everyone with no authentication required.

---

## Oracle Migration

We use H2 for development. When moving to Oracle 19c, two changes are needed:

1. In `pom.xml` — swap `com.h2database:h2` for `com.oracle.database.jdbc:ojdbc11`
2. In `application.properties` — replace the H2 datasource block with Oracle connection details

Both files contain `TODO (Oracle migration)` comments marking the exact lines to update.

---

## Team

Built by Team Ibrahim — Tamauz / Codeline

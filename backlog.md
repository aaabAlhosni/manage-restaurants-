# RMMS Backlog

---

## Version 0.1 — Initial Backend Release

- Set up Spring Boot project with full layered architecture: Model, Repository, Service, Controller, DTO, and Exception packages
- Integrated Google OAuth2 authentication — new managers are provisioned automatically on first login using their Google email as a unique identifier
- Implemented full CRUD API for menus and menu items with ownership enforcement — managers can only modify menus linked to their own email
- Added a public `GET /menu/{id}` endpoint that exposes any saved menu as a shareable JSON response with no authentication required
- Configured global exception handling returning consistent JSON error responses with appropriate HTTP status codes across all endpoints

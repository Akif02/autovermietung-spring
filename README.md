# 🚗 Autovermietung - REST API

Eine robuste Spring Boot Anwendung zur Verwaltung einer Autovermietung. Diese API ermöglicht es, Fahrzeuge zu verwalten, Benutzer zu registrieren und Reservierungen mit automatischer Preisberechnung durchzuführen.

## 🚀 Features

*   **Fahrzeug-Management:** Erstellen, Suchen (Filter nach Kategorie, Kraftstoff, Sitze, Marke), Aktualisieren und Löschen von Autos.
*   **Benutzer-Management:** Registrierung und Verwaltung von Kunden.
*   **Sicheres Login:** Passwörter werden mit **BCrypt** gehasht gespeichert. Authentifizierung via **HTTP Basic Auth**.
*   **Reservierungssystem:**
    *   Automatische Berechnung des Gesamtpreises basierend auf Miettagen.
    *   Validierung der Verfügbarkeit (nur `AVAILABLE` Autos können gebucht werden).
    *   Automatisches Update des Fahrzeugstatus auf `RENTED` bei Buchung.
    *   Stornierungsfunktion mit automatischer Freigabe des Fahrzeugs.
*   **Globale Fehlerbehandlung:** Strukturierte JSON-Fehlermeldungen für Validierungsfehler und Ausnahmen.
*   **Data Initializer:** Automatisches Anlegen eines Admin-Benutzers beim Systemstart.

## 🛠 Technologien

*   **Java 21** & **Spring Boot 3.2.3**
*   **Spring Data JPA** (Datenbankzugriff)
*   **Spring Security** (Authentifizierung & Autorisierung)
*   **MySQL** (Datenbank)
*   **Hibernate Validator** (Eingabevalidierung)
*   **Maven** (Dependency Management)

## 📋 API-Endpunkte

### Fahrzeuge (`/auto`)
| Methode | Endpunkt | Beschreibung | Zugriff |
| :--- | :--- | :--- | :--- |
| GET | `/auto/all` | Alle Autos abrufen | Öffentlich |
| GET | `/auto/{id}` | Einzelnes Auto abrufen | Öffentlich |
| GET | `/auto/filter` | Autos filtern (brand, fuel, etc.) | Öffentlich |
| POST | `/auto` | Neues Auto anlegen | **ADMIN** |
| PATCH | `/auto/{id}/status` | Status ändern (ACTIVE, RENTED, etc.) | **ADMIN** |
| DELETE | `/auto/{id}` | Auto löschen | **ADMIN** |

### Reservierungen (`/reservation`)
| Methode | Endpunkt | Beschreibung | Zugriff |
| :--- | :--- | :--- | :--- |
| POST | `/reservation` | Auto reservieren (berechnet Preis) | Eingeloggter User |
| GET | `/reservation/{id}` | Details einer Reservierung | Eingeloggter User |
| GET | `/reservation/user/{id}`| Alle Buchungen eines Users | Eingeloggter User |
| PATCH | `/reservation/cancel/{id}`| Reservierung stornieren | Eingeloggter User |
| GET | `/reservation` | Alle Reservierungen sehen | **ADMIN** |
| DELETE | `/reservation/{id}` | Reservierung löschen | **ADMIN** |

### Benutzer (`/api/users`)
| Methode | Endpunkt | Beschreibung | Zugriff |
| :--- | :--- | :--- | :--- |
| POST | `/api/users` | Neuen User registrieren | Öffentlich |
| GET | `/api/users/{id}` | Userdetails abrufen | Eingeloggter User |

## ⚙️ Installation & Start

1.  **Datenbank:** Erstelle eine MySQL-Datenbank namens `autovermietung`.
2.  **Konfiguration:** Passe die `src/main/resources/application.properties` an (Username/Passwort deiner DB).
3.  **Start:** Führe `mvn spring-boot:run` aus oder starte die `AutovermietungApplication` in deiner IDE.

## 🔑 Test-Zugangsdaten (Data Initializer)

Beim ersten Start wird automatisch ein Administrator angelegt:
*   **E-Mail:** `admin@test.de`
*   **Passwort:** `admin123`
*   **Rolle:** `ADMIN`

## 🧪 Testing mit Postman

Nutze für geschützte Endpunkte den Tab **Authorization** -> **Basic Auth** und gib die oben genannten Test-Daten ein.
Datumsformat für Reservierungen: `dd.MM.yyyy HH:mm` (z.B. `01.06.2024 10:00`).

---

## 👤 Autor

*   **  Mehmet Akif Erdem** - *Initial Projekt*

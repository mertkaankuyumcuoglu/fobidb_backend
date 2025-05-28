# FobiDB Backend API-Dokumentation

Diese Dokumentation beschreibt die verfügbaren Endpunkte und Datenmodelle der FobiDB Backend API.

## Basis-URL

```
http://localhost:8080
```

## Endpunkte

### Teacher

#### GET /teacher

Gibt eine Liste aller Lehrer zurück.

**Antwort (200 OK):**

```json
[
  {
    "id": 1,
    "lastName": "Mustermann",
    "name": "Max",
    "shortName": "MM",
    "email": "max.mustermann@example.com",
    "trainingTime": 120,
    "imageUrl": "https://example.com/image.jpg",
    "course": [
      ...
    ],
    "department": [
      ...
    ]
  }
]
```

#### POST /teacher

Erstellt einen neuen Lehrer.

**Request Body:**

```json
{
  "lastName": "Mustermann",
  "name": "Max",
  "shortName": "MM",
  "email": "max.mustermann@example.com",
  "trainingTime": 120,
  "imageUrl": "https://example.com/image.jpg"
}
```

**Antwort (200 OK):**

```json
{
  "errorMessage": null
}
```

#### PUT /teacher

Aktualisiert einen vorhandenen Lehrer.

**Request Body:**

```json
{
  "id": 1,
  "lastName": "Mustermann",
  "name": "Max",
  "shortName": "MM",
  "email": "max.mustermann@example.com",
  "trainingTime": 120,
  "imageUrl": "https://example.com/image.jpg"
}
```

**Antwort (200 OK):**

```json
{
  "id": 1,
  "lastName": "Mustermann",
  "name": "Max",
  "shortName": "MM",
  "email": "max.mustermann@example.com",
  "trainingTime": 120,
  "imageUrl": "https://example.com/image.jpg",
  "course": [
    ...
  ],
  "department": [
    ...
  ]
}
```

#### DELETE /teacher

Löscht einen Lehrer anhand seiner ID.

**Request Body:**

```json
1
```

**Antwort (200 OK):**

```json
{
  "errorMessage": null
}
```

#### GET /teacher/email

Sucht einen Lehrer anhand seiner E-Mail-Adresse.

**Parameter:**

- `email`: Die E-Mail-Adresse des Lehrers (erforderlich)

**Antwort (200 OK):**

```json
{
  "id": 1,
  "lastName": "Mustermann",
  "name": "Max",
  "shortName": "MM",
  "email": "max.mustermann@example.com",
  "trainingTime": 120,
  "imageUrl": "https://example.com/image.jpg",
  "course": [
    ...
  ],
  "department": [
    ...
  ]
}
```

### Course

#### GET /course

Gibt eine Liste aller Kurse zurück.

**Antwort (200 OK):**

```json
[
  {
    "title": "Java-Programmierung",
    "description": "Grundlagen der Java-Programmierung",
    "startDate": "2025-06-01",
    "duration": 90,
    "contact": {
      ...
    },
    "rating": 4,
    "comments": [
      "Sehr gut!",
      "Hilfreich"
    ],
    "errorMessage": null
  }
]
```

#### POST /course

Erstellt einen neuen Kurs.

**Request Body:**

```json
{
  "title": "Java-Programmierung",
  "description": "Grundlagen der Java-Programmierung",
  "startDate": "2025-06-01T10:00:00",
  "endDate": "2025-06-01T11:30:00",
  "contact": {
    "id": 1
  },
  "department": {
    "Id": 1
  }
}
```

**Antwort (200 OK):**

```
null
```

#### POST /course/rating

Bewertet einen vorhandenen Kurs.

**Request Body:**

```json
{
  "Id": 1,
  "rating": 5,
  "comment": "Ausgezeichneter Kurs!"
}
```

**Antwort (200 OK):**

```json
{
  "title": "Java-Programmierung",
  "description": "Grundlagen der Java-Programmierung",
  "startDate": "2025-06-01",
  "duration": 90,
  "contact": {
    ...
  },
  "rating": 5,
  "comments": [
    "Ausgezeichneter Kurs!"
  ],
  "errorMessage": null
}
```

## Datenmodelle

### Teacher

```json
{
  "id": 1,
  "lastName": "Mustermann",
  "name": "Max",
  "shortName": "MM",
  "email": "max.mustermann@example.com",
  "trainingTime": 120,
  "imageUrl": "https://example.com/image.jpg",
  "course": [],
  "department": []
}
```

### TeacherResponse

```json
{
  "errorMessage": "Fehlermeldung falls vorhanden"
}
```

### Course

```json
{
  "Id": 1,
  "title": "Java-Programmierung",
  "description": "Grundlagen der Java-Programmierung",
  "startDate": "2025-06-01T10:00:00",
  "endDate": "2025-06-01T11:30:00",
  "rating": [
    4,
    5,
    3
  ],
  "ratingAvg": 4,
  "ratingCount": 3,
  "comments": [
    "Sehr gut!",
    "Hilfreich",
    "Gute Erklärungen"
  ],
  "contact": {
    ...
  },
  "department": {
    ...
  }
}
```

### CourseRequest

```json
{
  "Id": 1,
  "rating": 5,
  "comment": "Ausgezeichneter Kurs!"
}
```

### CourseResponse

```json
{
  "title": "Java-Programmierung",
  "description": "Grundlagen der Java-Programmierung",
  "startDate": "2025-06-01",
  "duration": 90,
  "contact": {
    ...
  },
  "rating": 5,
  "comments": [
    "Ausgezeichneter Kurs!"
  ],
  "errorMessage": null
}
```

### Department

```json
{
  "Id": 1,
  "name": "Informatik"
}
```

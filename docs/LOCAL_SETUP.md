### `users`

```sql
CREATE TABLE users (
    user_id     SERIAL PRIMARY KEY,
    first_name  VARCHAR(50)  NOT NULL,
    last_name   VARCHAR(50)  NOT NULL,
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(60)  NOT NULL,
    is_online   BOOLEAN      NOT NULL DEFAULT FALSE,
    is_active   BOOLEAN      NOT NULL DEFAULT TRUE,
    avatar_img  INTEGER
);
```

| Spalte       | Hinweis                                                                                    |
|--------------|--------------------------------------------------------------------------------------------|
| `user_id`    | Wird von Postgres automatisch vergeben (`SERIAL`)                                          |
| `email`      | `UNIQUE`, da sie für den Login genutzt wird                                                |
| `password`   | Enthält den **BCrypt-Hash**, nie das Klartext-Passwort. Ein Hash ist immer 60 Zeichen lang |
| `avatar_img` | Nummer des gewählten Avatar-Bilds                                                          |

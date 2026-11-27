# 📱 CheckIn Pro — Application Mobile (Android / Kotlin + SQLite)

**CheckIn Pro** est une application mobile Android autonome développée en **Kotlin** avec une base de données locale **SQLite** (`CheckInProBD` via `BDLocalService`), dédiée à la gestion des utilisateurs et au suivi des pointages des travailleurs de terrain et nomades sans dépendance à un serveur Back-End.

---

## ✨ Fonctionnalités & État Actuel

### Gestion des utilisateurs (`BDLocalService`)
* **Compte par défaut** : Création automatique d'un compte administrateur système (`admin@checkinpro.com` / `123456`, rôle `ADMIN`) lors de l'initialisation de la base.
* **Authentification locale** : Vérification des identifiants (`email` et `password`) via `checkLogin(email, password)`.
* **CRUD complet sur `users`** :
  * Ajout d'un utilisateur (`addUser`)
  * Consultation de tous les utilisateurs (`getAllUsers`) ou par identifiant (`getUserById`)
  * Mise à jour des informations (`updateUser`)
  * Suppression d'un utilisateur (`deleteUser`)

## 🛠️ Stack Technique

* **Langage** : Kotlin (`jvmTarget = '1.8'`, Java 8)
* **Plateforme Android** : `minSdk 28` (Android 9.0), `compileSdk 34` / `targetSdk 34`
* **IDE** : Android Studio
* **Base de données locale** : SQLite (`SQLiteOpenHelper` — base `CheckInProBD`, version `2`)
---

## 🗄️ Schéma de Base de Données (`CheckInProBD`)

Table `users` (version `2`) :

```sql
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    fullName TEXT,
    email TEXT UNIQUE,
    password TEXT,
    role TEXT
)
```

---

## 🚀 Installation et Configuration

### 1. Prérequis
* **Android Studio** (supportant `compileSdk 34`)
* **JDK 8** ou supérieur
* Un émulateur Android ou un smartphone Android (`minSdk 28` / Android 9.0+)

### 2. Cloner le dépôt
```bash
git clone <url-du-depot>
cd checkinpro
```

### 3. Configuration Gradle (`build.gradle`)
Le projet utilise `com.android.application`, `kotlin-android`, `minSdk 28` et `targetSdk 34` avec les dépendances AndroidX et Material. 

### 4. Compilation et exécution
1. Ouvrez le projet dans **Android Studio**.
2. Laissez **Gradle** synchroniser les dépendances.
3. Sélectionnez un émulateur ou un appareil physique et lancez l'application.

---

## 👥 Équipe de Développement — Groupe Majesteye

Projet réalisé dans le cadre de l'année universitaire **2026/2027** à l'**Institut Supérieur des Études Technologiques de Béja (ISET Béja)** — *Département des Technologies Informatiques*.

* **Product Owner** : Mme Balkis Bettoumi
* **Scrum Master** : Sdiri Douaa
* **Équipe de développement** :
  * Aloui Elaa
  * Yaakoubi Syrine
  * Bhiri Yassine
  * Chaweli Mohamed Amine
  * Rezgui Firas

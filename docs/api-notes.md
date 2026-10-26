# Note Technique : Couche Web REST, Spring MVC et SpringDoc OpenAPI (AutoLoc)

Cette note technique documente la conception, l'implémentation et la validation de l'API REST de l'application **AutoLoc** (Atelier 5). Elle détaille l'inventaire complet des endpoints, la gestion de la sérialisation JSON avec Jackson, la complémentarité entre Postman et Swagger, l'analyse de qualité de code (SonarQube) ainsi que les réponses aux questions de compréhension.

---

## Section A — Endpoints de l'API REST

Tous les contrôleurs sont préfixés par le context-path `/api` défini de manière centralisée dans `application.properties` via `server.servlet.context-path=/api`. Les routes désignent des collections de ressources au pluriel.

| Contrôleur | Verbe HTTP & Route | Service appelé | Code succès | Codes d'erreur possibles |
| :--- | :--- | :--- | :---: | :---: |
| **`ContratController`** | `GET /api/contrats` | `contratService.findAll()` | `200 OK` | — |
| **`ContratController`** | `GET /api/contrats/{id}` | `contratService.findById(id)` | `200 OK` | `404 Not Found` |
| **`ContratController`** | `POST /api/contrats` | `contratService.create(contrat)` | `201 Created` | `400 Bad Request` |
| **`ContratController`** | `PUT /api/contrats/{id}` | `contratService.update(id, contrat)` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| **`ContratController`** | `DELETE /api/contrats/{id}` | `contratService.deleteById(id)` | `204 No Content` | `404 Not Found` |
| **`PaiementController`** | `GET /api/paiements` | `paiementService.findAll()` | `200 OK` | — |
| **`PaiementController`** | `GET /api/paiements/{id}` | `paiementService.findById(id)` | `200 OK` | `404 Not Found` |
| **`PaiementController`** | `POST /api/paiements` | *(Non exposé — lecture seule)* | — | `405 Method Not Allowed` |
| **`AgenceController`** | `GET /api/agences` | `agenceService.findAll()` | `200 OK` | — |
| **`AgenceController`** | `GET /api/agences/{id}` | `agenceService.findById(id)` | `200 OK` | `404 Not Found` |
| **`AgenceController`** | `POST /api/agences` | `agenceService.create(agence)` | `201 Created` | `400 Bad Request` |
| **`AgenceController`** | `PUT /api/agences/{id}` | `agenceService.update(id, agence)` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| **`AgenceController`** | `DELETE /api/agences/{id}` | `agenceService.deleteById(id)` | `204 No Content` | `404 Not Found` |
| **`EmployeController`** | `GET /api/employes` | `employeService.findAll()` | `200 OK` | — |
| **`EmployeController`** | `GET /api/employes/{id}` | `employeService.findById(id)` | `200 OK` | `404 Not Found` |
| **`EmployeController`** | `POST /api/employes` | `employeService.create(employe)` | `201 Created` | `400 Bad Request` |
| **`EmployeController`** | `PUT /api/employes/{id}` | `employeService.update(id, employe)` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| **`EmployeController`** | `DELETE /api/employes/{id}` | `employeService.deleteById(id)` | `204 No Content` | `404 Not Found` |
| **`VehiculeController`** | `GET /api/vehicules` | `vehiculeService.findAll()` | `200 OK` | — |
| **`VehiculeController`** | `GET /api/vehicules/{id}` | `vehiculeService.findById(id)` | `200 OK` | `404 Not Found` |
| **`VehiculeController`** | `POST /api/vehicules` | `vehiculeService.create(vehicule)` | `201 Created` | `400 Bad Request` |
| **`VehiculeController`** | `PUT /api/vehicules/{id}` | `vehiculeService.update(id, vehicule)` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| **`VehiculeController`** | `DELETE /api/vehicules/{id}` | `vehiculeService.deleteById(id)` | `204 No Content` | `404 Not Found` |
| **`EquipementController`** | `GET /api/equipements` | `equipementService.findAll()` | `200 OK` | — |
| **`EquipementController`** | `GET /api/equipements/{id}` | `equipementService.findById(id)` | `200 OK` | `404 Not Found` |
| **`EquipementController`** | `POST /api/equipements` | `equipementService.create(equipement)` | `201 Created` | `400 Bad Request` |
| **`EquipementController`** | `PUT /api/equipements/{id}` | `equipementService.update(id, equipement)` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| **`EquipementController`** | `DELETE /api/equipements/{id}` | `equipementService.deleteById(id)` | `204 No Content` | `404 Not Found` |
| **`ClientController`** | `GET /api/clients` | `clientService.findAll()` | `200 OK` | — |
| **`ClientController`** | `GET /api/clients/{id}` | `clientService.findById(id)` | `200 OK` | `404 Not Found` |
| **`ClientController`** | `POST /api/clients` | `clientService.create(client)` | `201 Created` | `400 Bad Request` |
| **`ClientController`** | `PUT /api/clients/{id}` | `clientService.update(id, client)` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| **`ClientController`** | `DELETE /api/clients/{id}` | `clientService.deleteById(id)` | `204 No Content` | `404 Not Found` |
| **`ReservationController`** | `GET /api/reservations` | `reservationService.findAll()` | `200 OK` | — |
| **`ReservationController`** | `GET /api/reservations/{id}` | `reservationService.findById(id)` | `200 OK` | `404 Not Found` |
| **`ReservationController`** | `POST /api/reservations` | `reservationService.create(reservation)` | `201 Created` | `400 Bad Request` |
| **`ReservationController`** | `PUT /api/reservations/{id}` | `reservationService.update(id, reservation)` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| **`ReservationController`** | `DELETE /api/reservations/{id}` | `reservationService.deleteById(id)` | `204 No Content` | `404 Not Found` |
| **`MaintenanceController`** | `GET /api/maintenances` | `maintenanceService.findAll()` | `200 OK` | — |
| **`MaintenanceController`** | `GET /api/maintenances/{id}` | `maintenanceService.findById(id)` | `200 OK` | `404 Not Found` |
| **`MaintenanceController`** | `POST /api/maintenances` | `maintenanceService.create(maintenance)` | `201 Created` | `400 Bad Request` |
| **`MaintenanceController`** | `PUT /api/maintenances/{id}` | `maintenanceService.update(id, maintenance)` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| **`MaintenanceController`** | `DELETE /api/maintenances/{id}` | `maintenanceService.deleteById(id)` | `204 No Content` | `404 Not Found` |

---

## Section B — Annotations Jackson et Sérialisation Bidirectionnelle

### 1. Synthèse des choix d'annotation par association

| Association | Entité & Attribut annoté | Annotation retenue | Justification technique |
| :--- | :--- | :--- | :--- |
| **`Contrat ↔ Paiement`** | `Contrat.paiements`<br>`Paiement.contrat` | `@JsonManagedReference("contrat-paiements")`<br>`@JsonBackReference("contrat-paiements")` | **Vraie composition métier** (`cascade = CascadeType.ALL`). Les paiements sont créés directement imbriqués dans le JSON du contrat (`POST /api/contrats`). La paire managed/back permet à Jackson de reconstruire le lien parent-enfant sans provoquer de récursion infinie (`StackOverflowError`). |
| **`Reservation ↔ Contrat`** | `Reservation.contrat` | `@JsonIgnore` | Association OneToOne bidirectionnelle. Le contrat référence sa réservation (`Contrat.reservation`). Placer `@JsonIgnore` sur le côté inverse (`Reservation.contrat`) évite la boucle infinie tout en préservant la visibilité de la réservation lors de la consultation d'un contrat. |
| **`Client ↔ Reservation`** | `Client.reservations` | `@JsonIgnore` | Une réservation est créée en désignant son client par identifiant (`{ "client": {"idClient": 1} }`). L'attribut propriétaire `Reservation.client` ne reçoit aucune annotation pour rester désérialisable lors du POST. L'attribut inverse `Client.reservations` est ignoré pour éviter la boucle et l'over-fetching. |
| **`Agence ↔ Vehicule`** | `Agence.vehicules` | `@JsonIgnore` | L'enfant (`Vehicule`) référence son agence (`Vehicule.agence`). Lors de la consultation d'une agence, la liste complète des véhicules n'a pas à être sérialisée (évite le chargement inopportun de la collection LAZY et la boucle). |
| **`Agence ↔ Employe`** | `Agence.employes` | `@JsonIgnore` | Similaire aux véhicules : un employé référence son agence (`Employe.agence`), mais la liste inverse sur `Agence` est masquée pour préserver les performances et la clarté du JSON. |
| **`Vehicule ↔ Maintenance`** | `Vehicule.maintenances` | `@JsonIgnore` | La maintenance désigne son véhicule (`Maintenance.vehicule`). La collection inverse sur `Vehicule` est ignorée pour couper la récursion. |
| **`Vehicule ↔ Equipement`** | `Equipement.vehicules` | `@JsonIgnore` | Association ManyToMany. Le véhicule possède ses équipements (`Vehicule.equipements`), la collection réciproque sur `Equipement` est masquée. |
| **Proxies Hibernate LAZY** | Classes d'entités | `@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})` | Évite l'exception Jackson `No serializer found for class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor` lors de la sérialisation d'objets proxifiés par le chargement paresseux Hibernate. |

### 2. Différence fondamentale entre `@JsonBackReference` et `@JsonIgnore`

- **`@JsonBackReference`** :
  - Fonctionne exclusivement en **binôme** avec `@JsonManagedReference`.
  - Le champ annoté est **omis lors de la sérialisation** (il n'apparaît pas dans le JSON produit).
  - En revanche, lors de la **désérialisation**, Jackson reconstitue automatiquement la référence bidirectionnelle vers l'instance parente (`@JsonManagedReference`).
  - *Usage idéal :* Modèles de composition parent-enfant avec création imbriquée (ex. `Contrat ↔ Paiement`).

- **`@JsonIgnore`** :
  - Désactive totalement le champ : la propriété est **ignorée à la fois en sérialisation et en désérialisation**.
  - Si un payload JSON contient cette propriété, elle ne sera jamais injectée dans l'objet Java.
  - *Conséquence importante :* Si l'on plaçait `@JsonBackReference` sur `Reservation.client`, Jackson ignorerait le champ client lors du POST d'une réservation (`{ "client": {"idClient": 1} }`), produisant une réservation sans client. L'approche correcte consiste à ne rien mettre sur `Reservation.client` et à placer `@JsonIgnore` sur la collection inverse `Client.reservations`.

---

## Section C — Postman et Swagger : Rôles et Complémentarité

| Outil | Rôle principal | Caractéristiques clés | Exemple concret rencontré dans l'atelier |
| :--- | :--- | :--- | :--- |
| **Postman** | **Client de test dynamique et automatisable** | • Exécution de requêtes HTTP réelles (headers, body, params).<br>• Scripts de test post-réponse (`pm.test()`, assertions).<br>• Gestion de variables d'environnement (`{{baseUrl}}`, `{{contratId}}`).<br>• Validation des scénarios d'erreurs (400, 404, 405). | **Enchaînement CRUD & Cascade :** Création d'un contrat via `POST /api/contrats`, capture automatique de son identifiant généré dans `contratId` via le script `pm.environment.set("contratId", pm.response.json().idContrat);`, puis utilisation immédiate de cette variable dans le `PUT` de mise à jour du montant (480 → 520) et le `DELETE`. |
| **Swagger UI (SpringDoc)** | **Spécification vivante et documentation interactive** | • Découverte du contrat d'API en temps réel (`/api/v3/api-docs`).<br>• Interface web visuelle claire (`/api/swagger-ui.html`).<br>• Synchronisation automatique avec le code Java (pas de dérive).<br>• Description précise des schémas JSON, paramètres et codes HTTP. | **Contrat d'API et exploration immédiate :** Visualisation des 9 contrôleurs regroupés par tags (`Contrats`, `Paiements`, etc.), description détaillée des statuts (200, 201, 204, 400, 404), et possibilité pour un développeur front-end d'essayer un endpoint via "Try it out" sans installer de logiciel client. |

**Complémentarité :**
Swagger sert de **spécification contractuelle** et de catalogue d'échange entre équipes, tandis que Postman sert de **suite de tests d'intégration et de validation opérationnelle**, capable de rejouer des scénarios complexes et de vérifier la cohérence des règles métier (cascade, intégrité référentielle, gestion des exceptions).

---

## Section D — Anomalies SonarQube for IDE Détectées et Corrigées

### 1. Règle `java:S6813` — *Field injection should not be used*
- **Description de l'anomalie :** L'utilisation de `@Autowired` sur des champs privés dans un contrôleur ou un service masque les dépendances de la classe, interdit l'utilisation du mot-clé `final` (perte de l'immuabilité) et complique les tests unitaires isolés sans initialiser le contexte Spring.
- **Correction appliquée :** Utilisation systématique de champs `private final I...Service ...Service;` combinés à l'annotation Lombok `@RequiredArgsConstructor`. Le constructeur avec arguments est généré automatiquement par Lombok, assurant une injection par constructeur propre et conforme aux standards de l'Atelier 4.

### 2. Typo d'annotation et conventions OpenAPI — *Unknown annotation `@tag` vs `@Tag`*
- **Description de l'anomalie :** Présence d'une annotation en casse incorrecte `@tag` dans le squelette initial de `AgenceController`, provoquant une erreur de compilation Maven (`cannot find symbol: class tag`).
- **Correction appliquée :** Remplacement par l'annotation standard `io.swagger.v3.oas.annotations.tags.Tag` (`@Tag(name = "Agences", description = "Gestion des agences")`) et enrichissement des méthodes avec `@Operation`, `@ApiResponse`, et `@Parameter`.

### 3. Couplage fort API / Persistance — *Exposition directe des entités JPA dans la couche Web*
- **Description de l'anomalie :** Les contrôleurs retournent et reçoivent directement les entités du package `domain`. Cela expose les structures de tables internes, risque d'exposer des données sensibles et provoque des erreurs de sérialisation Jackson lors de relations bidirectionnelles ou de proxies `LAZY`.
- **Correction appliquée :** Protection de la couche Web par l'application des annotations Jackson ciblées (`@JsonIgnoreProperties`, `@JsonIgnore`, `@JsonManagedReference`, `@JsonBackReference`) et configuration de `spring.jackson.serialization.fail-on-empty-beans=false`, en attendant l'introduction complète des DTO (Data Transfer Objects) au Chapitre 10.

---

## Section E — Questions de Compréhension

### 1. Quel composant reçoit toutes les requêtes, et comment retrouve-t-il la méthode du contrôleur à appeler ?
Toutes les requêtes HTTP adressées à l'application sont d'abord réceptionnées par la **`DispatcherServlet`** (qui agit comme *Front Controller* central de Spring MVC).
Pour identifier la méthode à invoquer, la `DispatcherServlet` consulte le composant **`HandlerMapping`** (plus précisément son implémentation par défaut `RequestMappingHandlerMapping`). Ce composant inspecte l'URL de la requête, le verbe HTTP (GET, POST, etc.), les en-têtes et les types de contenu afin de faire correspondre la requête à la méthode du contrôleur annotée par `@RequestMapping` / `@GetMapping` / `@PostMapping`.

### 2. Pourquoi `ContratController` dépend-il de `IContratService` et non de `ContratRepository` ni de `ContratServiceImpl` ?
- **Non-dépendance envers `ContratRepository` :** Respecte l'étanchéité des couches d'architecture. Le contrôleur s'occupe de la communication HTTP (sérialisation, codes de statut), alors que la logique métier (calculs, validation de montant positif, gestion de la cascade) réside dans la couche Service. Court-circuiter le service pour aller au repository violerait la séparation des responsabilités.
- **Non-dépendance envers `ContratServiceImpl` :** Respecte le principe d'Inversion des Dépendances (*Dependency Inversion Principle*, DIP des principes SOLID). Dépendre d'une abstraction (`IContratService`) assure un couplage faible, permet de substituer l'implémentation (par exemple par un mock lors des tests unitaires), et autorise Spring à injecter un proxy dynamique (gestion transactionnelle `@Transactional`, sécurité, logging) sans impacter le contrôleur.

### 3. Pourquoi un POST renvoie-t-il 201 et un DELETE 204 ? Pourquoi une ressource absente donne-t-elle 404 et non 500 ?
- **`POST` → 201 Created :** Le verbe POST a pour sémantique de créer une nouvelle ressource sur le serveur. Le standard HTTP impose le code `201 Created` pour attester que la création s'est déroulée avec succès et renvoyer la ressource créée avec son nouvel identifiant généré.
- **`DELETE` → 204 No Content :** Une suppression réussie fait disparaître la ressource ciblée. Le serveur n'a plus aucune donnée utile à renvoyer dans le corps de la réponse ; le statut `204 No Content` indique un succès sans corps de réponse.
- **Ressource absente → 404 Not Found (et non 500) :** Un code 500 (*Internal Server Error*) signale un bogue interne ou une défaillance inattendue du serveur. En revanche, lorsqu'un client demande une ressource inexistante (ex. ID `9999`), il s'agit d'une erreur d'aiguillage côté client. Le serveur traite correctement la demande en constatant que l'élément n'existe pas, ce qui correspond exactement au code HTTP `404 Not Found`.

### 4. Pourquoi `PaiementController` n'expose-t-il que des GET ? Quelle réponse reçoit un POST `/api/paiements` ?
- **Lecture seule :** Dans le modèle métier AutoLoc, le `Contrat` est l'agrégat racine qui gère le cycle de vie de ses `Paiement` en composition stricte (`cascade = CascadeType.ALL, orphanRemoval = true`). Les écritures (création, modification, suppression) se font obligatoirement via le contrat porteur. Le contrôleur `PaiementController` ne doit donc permettre que la consultation (`findAll`, `findById`).
- **Réponse à un POST `/api/paiements` :** Comme aucune méthode annotée avec `@PostMapping` n'est définie sur la route `/paiements`, le serveur Spring MVC renvoie automatiquement un statut HTTP **`405 Method Not Allowed`** (Méthode non autorisée).

### 5. Quels risques présente l'exposition directe des entités JPA ? Pourquoi `Contrat.paiements` utilise-t-il `@JsonManagedReference` alors que `Agence.vehicules` utilise `@JsonIgnore` ?
- **Risques de l'exposition directe des entités JPA :**
  1. *Couplage fort :* Toute modification du modèle relationnel en base impacte directement l'API publique consommée par les clients.
  2. *Fuite d'informations internes :* Risque d'exposer des attributs techniques ou confidentiels.
  3. *Vulnérabilité de sur-affectation (Mass Assignment) :* Un client malveillant pourrait modifier des champs protégés s'ils ne sont pas filtrés.
  4. *Boucles infinies et proxies LAZY :* Déclenchement d'exceptions `StackOverflowError` ou `ByteBuddyInterceptor` lors de la sérialisation Jackson.
- **Différence entre `Contrat.paiements` et `Agence.vehicules` :**
  - **`Contrat.paiements`** : Il s'agit d'une **composition** où les paiements font partie intégrante du document contrat. Un client créant un contrat peut fournir simultanément ses paiements. La paire `@JsonManagedReference` (côté Contrat) et `@JsonBackReference` (côté Paiement) permet d'embarquer la liste des paiements dans le JSON du contrat tout en évitant la boucle de sérialisation retour.
  - **`Agence.vehicules`** : Il s'agit d'une **agrégation** de grande taille. Une agence gère de nombreux véhicules créés indépendamment d'elle (chacun désignant son agence par son identifiant). Renvoyer l'ensemble des véhicules à chaque consultation d'une agence entraînerait un surcoût réseau et mémoire considérable (*over-fetching*) et forcerait le chargement de collections LAZY volumineuses. L'utilisation de `@JsonIgnore` sur la collection inverse `vehicules` évite ce transfert inutile et supprime la récursion.

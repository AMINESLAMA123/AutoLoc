# Note Technique : Couche Service, IoC et Injection de Dépendances (AutoLoc)

Cette note technique documente les choix d'architecture, les modes d'injection de dépendances, le diagnostic des erreurs de conteneur Spring et l'analyse de qualité de code pour la couche Service de l'application **AutoLoc**.

---

### Section A — Modes d'injection comparés

| Critère | Injection par constructeur | Injection par attribut (`@Autowired`) |
| :--- | :--- | :--- |
| **Champ `final` (immutabilité) possible ?** | **Oui** (garantit que la dépendance ne sera jamais réassignée) | **Non** (les champs `final` ne peuvent pas être injectés par réflexion tardive) |
| **Dépendance visible dans la signature de la classe ?** | **Oui** (explicite dans le constructeur ou `@RequiredArgsConstructor`) | **Non** (dépendance cachée à l'intérieur de la classe) |
| **Classe utilisable avec `new` hors conteneur (tests unitaires) sans dépendance `null` ?** | **Oui** (le compilateur impose de fournir toutes les dépendances requises) | **Non** (risque de `NullPointerException` si non injectée par réflexion/framework) |
| **Signalé par SonarQube for IDE ?** | **Non** (conforme aux règles de Clean Code) | **Oui** (règle `java:S6813` : *"Field injection should not be used"*) |

---

### Section B — Inventaire des Services Métier

| Service | Dépendances injectées | Mode d'injection | Justification |
| :--- | :--- | :--- | :--- |
| **`ContratServiceImpl`** | `IContratRepository` | Constructeur (`@RequiredArgsConstructor`) | Opérations CRUD sur les contrats de location, validation du montant total et maintien des règles de cascade. |
| **`PaiementServiceImpl`** | `IPaiementRepository` | Constructeur (`@RequiredArgsConstructor`) | Consultation en lecture seule (`findById`, `findAll`) des paiements ; les écritures sont déléguées au Contrat. |
| **`AgenceServiceImpl`** | `IAgenceRepository` | Constructeur (`@RequiredArgsConstructor`) | Gestion des agences et validation de la présence des attributs obligatoires (nom d'agence). |
| **`EmployeServiceImpl`** | `IEmployeRepository` | Constructeur (`@RequiredArgsConstructor`) | Gestion du personnel et validation des attributs obligatoires (nom, rôle). |
| **`VehiculeServiceImpl`** | `IVehiculeRepository` | Constructeur (`@RequiredArgsConstructor`) | Gestion du parc automobile et validation des règles de gestion (tarif journalier non négatif, immatriculation). |
| **`EquipementServiceImpl`** | `IEquipementRepository` | Constructeur (`@RequiredArgsConstructor`) | Gestion du catalogue des options/équipements et validation de libellé non vide. |
| **`ClientServiceImpl`** | `IClientRepository` | Constructeur (`@RequiredArgsConstructor`) | Gestion des clients et contrôle des données d'identification obligatoires (nom, e-mail). |
| **`ReservationServiceImpl`** | `IReservationRepository` | Constructeur (`@RequiredArgsConstructor`) | Gestion des réservations et contrôle de la cohérence temporelle (date début antérieure à date fin). |
| **`MaintenanceServiceImpl`** | `IMaintenanceRepository` | Constructeur (`@RequiredArgsConstructor`) | Suivi des opérations d'entretien et validation de la cohérence des dates d'intervention. |

---

### Section C — Diagnostic des Messages d'Erreur du Conteneur

#### Message 1 : Bean introuvable
```text
Parameter 0 of constructor in tn.esprit.autoloc.web.controller.ContratController required a bean of type 'tn.esprit.autoloc.service.IContratService' that could not be found.
```
- **Deux causes les plus probables côté `ContratServiceImpl` :**
  1. *Omission de l'annotation `@Service` (ou `@Component`)* : Spring ne détecte pas la classe d'implémentation comme bean géré lors du scan de composants.
  2. *Problème de package / scan* : La classe `ContratServiceImpl` est placée en dehors de l'arborescence scannée par la classe principale `@SpringBootApplication` (ex. faute de frappe dans le nom de package).
- **Correction :** Vérifier que `ContratServiceImpl` est annotée avec `@Service` et qu'elle réside dans le sous-package `service` du package racine scanné.

#### Message 2 : Conflit d'ambiguïté (NoUniqueBeanDefinitionException)
```text
Parameter 0 of constructor in tn.esprit.autoloc.web.controller.NotificationController required a single bean, but 2 were found:
 - emailNotificateur
 - smsNotificateur
```
- **Cause :** Deux beans implémentent la même interface et Spring ne sait pas lequel injecter dans le constructeur unique.
- **Deux propositions de correction :**
  1. Placer `@Primary` sur la classe d'implémentation par défaut (ex. `@Primary @Service public class EmailNotificateur implements INotificateur`).
  2. Placer `@Qualifier("emailNotificateur")` directement sur le paramètre du constructeur dans `NotificationController` pour désigner explicitement le bean attendu.

#### Message 3 : Dépendance circulaire (BeanCurrentlyInCreationException)
```text
The dependencies of some of the beans in the application context form a cycle:
   clientServiceImpl
      ↓
   reservationServiceImpl
      ↓
   clientServiceImpl
```
- **Pourquoi l'injection par constructeur rend ce cycle impossible à contourner :**
  Lors d'une injection par constructeur, Spring doit instancier tous les arguments avant de créer le bean cible. Si `ClientServiceImpl` requiert `ReservationServiceImpl` pour être instancié et réciproquement, aucun des deux constructeurs ne peut être exécuté en premier, produisant un blocage strict à l'instanciation.
- **Piste de conception pour supprimer le cycle :**
  Extraire la logique commune ou l'orchestration dans un troisième service (ex. `ClientReservationOrchestrator` ou `ReservationWorkflowService`), ou inverser le sens de communication à l'aide d'événements applicatifs Spring (`ApplicationEventPublisher` / `@EventListener`).

---

### Section D — Anomalies SonarQube for IDE Détectées et Corrigées

1. **Règle `java:S6813` — *Field injection should not be used***
   - *Explication :* L'injection par champ masque les dépendances de la classe, empêche l'utilisation de variables immuables `final` et rend les tests unitaires isolés difficiles sans framework de réflexion.
   - *Correction :* Utiliser l'injection par constructeur via `@RequiredArgsConstructor` de Lombok combiné à des champs `private final`.

2. **Règle `java:S112` — *Generic exceptions should never be thrown***
   - *Explication :* Lancer des exceptions génériques (`RuntimeException`, `Exception`) complique le ciblage et la gestion centralisée des erreurs dans la couche Web (`@RestControllerAdvice`).
   - *Correction :* Création et utilisation systématique d'une exception métier dédiée `ResourceNotFoundException`.

3. **Convention Clean Code & JPA — *Interdiction de `@Data` sur les entités et services sans état***
   - *Explication :* `@Data` surcharge `equals()`, `hashCode()` et `toString()`, provoquant des boucles infinies sur les relations bidirectionnelles et forçant le déclenchement de requêtes `LAZY`. De plus, les services doivent rester strictement sans état (*stateless*) pour être thread-safe.
   - *Correction :* Remplacement ciblé par `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor` sur les entités, et utilisation exclusive de dépendances `final` injectées sans attributs d'état mutables dans les services.

---

### Section E — Questions de Compréhension

1. **Où est le `new` de `ContratServiceImpl` et qui l'exécute ?**
   Il n'y a aucun `new ContratServiceImpl()` dans le code applicatif. C'est le conteneur IoC de Spring (`ApplicationContext` / `BeanFactory`) qui instancie le bean par réflexion lors du démarrage de l'application pendant la phase de scan (`@ComponentScan`).

2. **Pourquoi un contrôleur dépendra-t-il de `IContratService` et non de `ContratServiceImpl` ?**
   Pour respecter le principe d'Inversion des Dépendances (DIP des principes SOLID). Cela garantit un couplage faible, permet de substituer facilement l'implémentation (par exemple par un mock lors des tests unitaires), et autorise Spring à appliquer des proxys dynamiques (transactions, sécurité, cache) sans impacter le contrôleur.

3. **Pourquoi un service singleton doit-il rester sans état ? Donner un exemple de bug.**
   Dans Spring, les services sont par défaut des singletons partagés par tous les threads de l'application (traitant plusieurs requêtes HTTP simultanées).
   *Exemple de bug :* Si un service stocke un champ d'état mutable `private Long currentUserId;`, un thread traitant la requête de l'utilisateur A modifiera cette valeur pendant qu'un thread B l'utilise, entraînant une fuite de données ou l'exécution d'opérations au nom d'un autre utilisateur.

4. **Pourquoi `update` charge-t-il l'entité existante avant d'appeler `save` au lieu de lui passer directement l'objet reçu ?**
   Appeler directement `save(contratRecu)` écraserait aveuglément tous les champs non renseignés par `null` et romprait les associations existantes. De plus, sur une relation avec `orphanRemoval = true` (comme les paiements rattachés au contrat), passer une collection vide ou non initialisée supprimerait physiquement tous les enregistrements enfants en base de données. Charger l'entité permet de mettre à jour de façon contrôlée uniquement les attributs modifiables.

5. **Différence entre `@Component` et `@Bean` ? Entre `@Primary` et `@Qualifier` ? Pourquoi `IPaiementService` n'expose-t-il ni création, ni modification, ni suppression ?**
   - **`@Component` vs `@Bean` :** `@Component` est une annotation de niveau classe détectée automatiquement par scan ; `@Bean` est une annotation de niveau méthode dans une classe `@Configuration` permettant d'instancier et configurer manuellement un objet (notamment issu de bibliothèques tierces).
   - **`@Primary` vs `@Qualifier` :** `@Primary` désigne l'implémentation par défaut à choisir en cas d'ambiguïté ; `@Qualifier` permet au point d'injection d'exiger explicitement un bean spécifique par son nom.
   - **`IPaiementService` :** Un paiement n'existe que dans le cadre de son contrat (`Composition`). Toute opération de création, modification ou suppression de paiement doit obligatoirement être orchestrée par le `Contrat` afin de maintenir la cohérence comptable et contractuelle (`CascadeType.ALL + orphanRemoval = true`).

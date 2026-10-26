# Note Technique : Stratégie de Fetch et Cascade (AutoLoc)

Cette note technique documente les choix d'associations JPA, les stratégies de chargement (`FetchType`) et les règles de propagation (`CascadeType` / `orphanRemoval`) appliquées aux entités du domaine de l'application **AutoLoc**.

---

### Tableau récapitulatif des associations

| Association | Fetch | Cascade | Justification |
| :--- | :--- | :--- | :--- |
| **Contrat $\rightarrow$ Paiement** | `LAZY` | `ALL + orphanRemoval` | Un paiement n'existe que rattaché à son contrat (composition forte) ; supprimer un contrat doit supprimer ses paiements et retirer un paiement de la liste doit le supprimer en base. |
| **Agence $\rightarrow$ Vehicule** | `LAZY` | `Aucune` | Un véhicule survit à son agence (il peut être réaffecté ou conservé dans le parc). Le chargement différé évite de charger toute la flotte de véhicules lors de la simple consultation d'une agence. |
| **Agence $\rightarrow$ Employe** | `LAZY` | `Aucune` | Les employés ont leur propre cycle de vie indépendant de l'agence. Le chargement à la demande (`LAZY`) évite d'alourdir les requêtes sur l'agence. |
| **Vehicule $\leftrightarrow$ Equipement** | `LAZY` | `Aucune` | Les équipements sont des entités partagées et réutilisables entre plusieurs véhicules. Supprimer ou modifier un véhicule ne doit pas impacter le catalogue d'équipements. Utilisation d'un `Set` pour optimiser la table de jointure. |
| **Client $\rightarrow$ Reservation** | `LAZY` | `PERSIST` | Permet de persister de nouvelles réservations créées avec le client. La suppression en cascade est évitée pour conserver l'historique des réservations. `LAZY` évite de charger tout l'historique lors de la lecture du client. |
| **Reservation $\rightarrow$ Vehicule** | `LAZY` | `Aucune` | La réservation référence un véhicule mais ne possède aucun droit de destruction sur celui-ci. `LAZY` évite les jointures inutiles lors des consultations de réservations. |
| **Reservation $\leftrightarrow$ Contrat** | `LAZY` | `ALL` (côté Reservation) | Le contrat est intrinsèquement lié à la réservation. La création, modification ou suppression d'une réservation se propage sur son contrat. `LAZY` assure des requêtes légères par défaut. |
| **Vehicule $\rightarrow$ Maintenance** | `LAZY` | `PERSIST` | Permet de persister de nouvelles fiches de maintenance créées avec le véhicule. `LAZY` permet de ne charger l'historique de maintenance que sur demande explicite. |

---

### Principes directeurs et bonnes pratiques retenus

1. **Stratégie de Fetch `LAZY` généralisée :**
   - Toutes les associations (`@ManyToOne`, `@OneToMany`, `@ManyToMany`, `@OneToOne`) utilisent `fetch = FetchType.LAZY` afin d'éviter le problème de requêtes N+1 et de ne charger en mémoire que les données nécessaires.
   - Les jointures nécessaires sont réalisées au besoin via des requêtes JPQL/HQL ciblées (`JOIN FETCH`) ou des `EntityGraph`.

2. **Côté propriétaire et `mappedBy` :**
   - Le côté propriétaire détient toujours la clé étrangère (ou la table de jointure).
   - Le côté inverse déclare `mappedBy` pointant exactement sur le nom de l'attribut dans l'entité propriétaire, garantissant l'absence de colonnes ou tables de jointure superflues.

3. **Différence entre `CascadeType.REMOVE` et `orphanRemoval = true` :**
   - `cascade = CascadeType.REMOVE` : la suppression de l'entité parente déclenche la suppression des entités enfants en base de données.
   - `orphanRemoval = true` : le retrait d'une entité enfant de la collection de l'entité parente en mémoire déclenche sa suppression physique en base de données.
   - Sur l'association **Contrat $\rightarrow$ Paiement**, les deux sont combinés (`CascadeType.ALL` inclut `REMOVE`, et `orphanRemoval = true` gère les suppressions d'éléments de la collection).

4. **Lombok et entités JPA :**
   - `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor` sont utilisés de manière ciblée.
   - `@Data` est proscrit pour éviter les boucles infinies et `StackOverflowError` dans `equals()`, `hashCode()` et `toString()` causés par les relations bidirectionnelles.

#  TP3 : Serveur Multi-threads - sockets en mode connecté TCP

**Titre : Serveur multithread — Sockets en mode connecté TCP**

**Étudiante :** Eya Makni  
**Établissement :** Faculté des Sciences de Sfax  
**Enseignante :** Amal ABID  
**Projet :** TP3-DAR2026

---

# Introduction générale

Dans ce TP, nous allons étudier la communication entre plusieurs applications à travers les sockets TCP en Java. L’objectif principal est de développer des serveurs capables de gérer plusieurs clients simultanément, contrairement aux applications précédentes qui traitaient les connexions de manière séquentielle ou ne géraient qu’un seul client à la fois.

Le TP est organisé en deux activités :

- **Activité 1 :** Développer un serveur TCP multithread permettant d’accepter plusieurs connexions clientes simultanément et d’attribuer un numéro d’ordre à chaque client.
- **Activité 2 :** Améliorer le service de calculatrice développé dans le TP2 en utilisant un serveur multithread, une communication par objets Java sérialisés et un compteur global partagé entre les threads.

## Objectifs du TP

- Comprendre le fonctionnement des sockets TCP.
- Mettre en œuvre les classes `ServerSocket` et `Socket`.
- Utiliser les threads pour gérer plusieurs clients en parallèle.
- Échanger des données entre un client et un serveur.
- Utiliser la sérialisation Java pour transmettre des objets.
- Gérer l’accès concurrent à une ressource partagée.

---

# Activité 1 :Acceptation de plusieurs clients

## 1. Objectif

L’objectif de cette activité est de transformer une application client/serveur TCP classique en une application capable de gérer plusieurs clients simultanément.

Le serveur doit accepter les connexions entrantes, attribuer un numéro d’ordre à chaque client et lui transmettre ce numéro. Chaque client doit être traité indépendamment des autres.

## 2. Principe de fonctionnement

Le serveur utilise la classe `ServerSocket` pour écouter les connexions sur le port 1234.

Lorsqu’un client se connecte, le serveur :

1. Accepte la connexion avec la méthode `accept()`.
2. Récupère l’adresse IP du client.
3. Attribue un numéro d’ordre au client.
4. Affiche les informations de connexion dans la console.
5. Crée un thread dédié au client.
6. Envoie le numéro du client à travers la socket.
7. Continue à accepter les nouvelles connexions sans attendre la fin du traitement du client précédent.

Le client utilise une socket TCP pour se connecter au serveur. Il reçoit son numéro d’identification et peut poursuivre la communication jusqu’à sa déconnexion.

## 3. Réalisation technique

### 3.1. Partie serveur

Le serveur utilise principalement les éléments suivants :

- `ServerSocket` : écoute les connexions entrantes.
- `Socket` : représente la connexion avec un client.
- `accept()` : accepte une nouvelle connexion.
- `Thread` : permet de traiter chaque client indépendamment.
- `PrintWriter` et `BufferedReader` : permettent d’échanger des données textuelles.
- `InetAddress` : permet d’obtenir des informations sur l’adresse IP.

La méthode `accept()` est placée dans une boucle afin que le serveur puisse accepter plusieurs clients successifs.

Pour chaque connexion, un nouveau thread est créé. Le traitement de chaque client ne bloque donc pas l’acceptation des autres connexions.

### 3.2. Partie client

Le client se connecte au serveur à l’adresse IP et au port configurés dans le programme.

Dans le cas du test local, l’adresse utilisée est `localhost` et le port est `1234`.

Le client reçoit son numéro d’ordre depuis le serveur. Les échanges textuels sont effectués à l’aide de flux d’entrée et de sortie.


## 4. Tests et résultats

### Test 1 : Connexion de plusieurs clients

Plusieurs instances du client sont lancées pendant que le serveur reste actif.

Le serveur accepte les différentes connexions et attribue un numéro distinct à chaque client. Le traitement est assuré par des threads indépendants.

![img_2.png](img_2.png)

### Test 2 : Communication textuelle

Le client et le serveur échangent des messages à travers les flux textuels. La saisie de `quit` permet de terminer la communication du client.

![img_3.png](img_3.png)

### Test 3 : Test avec Telnet et test LAN

Telnet peut être utilisé pour vérifier qu’un port TCP est accessible et, si le protocole est textuel, pour effectuer des échanges simples.

Pour le test LAN, il faut démarrer le serveur sur une machine et lancer le client depuis une autre machine du même réseau. L’adresse IP du serveur doit être configurée dans le client et le pare-feu doit autoriser les connexions entrantes sur le port 1234.

Ces tests doivent être validés sur les machines utilisées avant d’affirmer que les essais Telnet et LAN ont réussi.

## 6. Bilan de l’activité 1

Cette activité a permis de comprendre comment améliorer un serveur TCP classique en utilisant les threads. La création d’un thread pour chaque client rend possible le traitement indépendant de plusieurs connexions. L’attribution d’un numéro d’ordre facilite également l’identification des clients connectés.

---

# Activité 2 : Amélioration de l’application calculatrice

## 1. Objectif

Dans cette activité, nous reprenons le service de calculatrice développé dans l’activité 2-2 du TP2.

Le but est de permettre au serveur d’accepter plusieurs clients simultanément et de traiter les opérations arithmétiques envoyées par chacun d’eux. Contrairement à une communication fondée uniquement sur des chaînes de caractères, cette activité utilise des objets Java sérialisés.

Un compteur global partagé permet également de connaître le nombre total d’opérations traitées par le serveur.

## 2. Principe de fonctionnement

Le client saisit deux opérandes et un opérateur arithmétique. Il crée ensuite un objet représentant l’opération et l’envoie au serveur.

Le serveur accepte la connexion et crée un thread dédié au client. Ce thread reçoit l’objet, effectue le calcul demandé, met à jour le compteur global et renvoie un objet contenant le résultat ou un message d’erreur.

Le client reçoit la réponse et affiche le résultat.

Les principales étapes sont les suivantes :

1. Le client établit une connexion TCP avec le serveur.
2. Le client crée un objet `Operation`.
3. L’objet est envoyé au serveur par sérialisation.
4. Le serveur désérialise l’objet reçu.
5. Le thread calcule le résultat.
6. Le compteur global est incrémenté de manière sûre.
7. Le serveur renvoie l’objet réponse.
8. Le client affiche le résultat ou le message d’erreur.
9. La connexion est fermée.

## 3. Structure du projet

Le projet contient les classes suivantes :

| Classe | Rôle |
|---|---|
| `Server2.java` | Écoute les connexions entrantes et crée un thread pour chaque client. |
| `Client2.java` | Saisit les données, envoie l’opération et affiche la réponse. |
| `ClientProcess2.java` | Traite la communication et le calcul pour un client donné. |
| `Operation.java` | Représente l’opération arithmétique et la réponse transmise entre le client et le serveur. |

Dans cette implémentation, la classe `Operation` est placée dans le package `Serverpackage` et elle est importée par le client. Le serveur et le client utilisent donc la même définition de la classe.

## 4. Communication par objets Java

### 4.1. Classe `Operation`

La classe `Operation` implémente l’interface `Serializable`, ce qui permet de convertir son état en une suite d’octets transmissible à travers un flux.

Elle contient les informations nécessaires au calcul :

- `operande1` : premier nombre.
- `operateur` : opération à effectuer.
- `operande2` : deuxième nombre.
- `resultat` : résultat calculé.
- `message` : message de réponse ou d’erreur.

Les getters et les setters permettent d’accéder aux attributs et de les modifier.

L’interface `Serializable` permet de transmettre l’objet à l’aide de `ObjectOutputStream` et de le reconstruire à l’aide de `ObjectInputStream`.

### 4.2. Envoi et réception des objets

Le client utilise `ObjectOutputStream` pour envoyer l’objet avec la méthode `writeObject()`.
![img_4.png](img_4.png)
![img_5.png](img_5.png)

Le serveur utilise `ObjectInputStream` pour récupérer l’objet avec la méthode `readObject()`.
![img_6.png](img_6.png)
![img_7.png](img_7.png)

Après le calcul, le serveur utilise `writeObject()` pour renvoyer l’objet réponse. Le client récupère cette réponse avec `readObject()`.

Il est important que les deux côtés créent leurs flux d’objets dans un ordre compatible. Dans l’implémentation réalisée, le flux de sortie est créé en premier puis vidé avec `flush()`, avant la création du flux d’entrée. Cela évite notamment un blocage lié à l’établissement des flux d’objets.

## 5. Calcul des opérations

Le serveur prend en charge les quatre opérations arithmétiques suivantes :

- Addition : `a + b`
- Soustraction : `a - b`
- Multiplication : `a * b`
- Division : `a / b`

Le serveur examine l’opérateur reçu et applique le calcul correspondant.

Pour la division, il vérifie que le deuxième opérande n’est pas nul afin d’éviter une division par zéro. Si l’opérateur n’est pas reconnu, le serveur renvoie un message d’erreur au lieu d’effectuer un calcul invalide.

Exemple : coté Client

![img_9.png](img_9.png)


## 6. Serveur multithread

Le serveur utilise `ServerSocket` sur le port 1234 et reste actif dans une boucle d’acceptation des connexions.

À chaque nouvelle connexion, il incrémente le numéro du client, affiche son adresse et crée un nouvel objet `ClientProcess`.

La classe `ClientProcess` étend `Thread`. Son exécution est lancée avec la méthode `start()`, qui permet au traitement du client de s’effectuer dans un thread séparé.

Ainsi, plusieurs clients peuvent envoyer des opérations au serveur sans devoir attendre que les autres aient terminé leurs calculs.

Chaque thread possède sa propre socket et ses propres flux de communication. Les communications des clients sont donc indépendantes.

## 7. Gestion des accès concurrents

### 7.1. Problème de concurrence

Tous les threads utilisent un compteur global qui représente le nombre total d’opérations traitées.

Si plusieurs threads modifient simultanément un même compteur sans protection, des mises à jour peuvent être perdues. Ce problème est lié à l’accès concurrent à une ressource partagée.

### 7.2. Solution utilisée

Dans notre implémentation, le compteur est déclaré comme un `AtomicInteger`.

Cette classe fournit des opérations atomiques, notamment `incrementAndGet()`, qui incrémente le compteur et retourne sa nouvelle valeur en une seule opération atomique.

Exemple :

![img_14.png](img_14.png)

Lorsqu’une opération est calculée avec succès, le serveur effectue :

![img_15.png](img_15.png)
![img_16.png](img_16.png)

Cette solution protège l’incrémentation du compteur partagé contre les mises à jour perdues lorsque plusieurs threads effectuent des calculs simultanément.

Le compteur doit être incrémenté une seule fois pour chaque opération considérée comme traitée avec succès. Dans notre implémentation, les erreurs de calcul, comme la division par zéro, ne sont pas comptabilisées comme des opérations réussies.


## 8. Tests et résultats

### Test 1 : Une opération arithmétique

Le client envoie une opération au serveur. Le serveur reçoit l’objet, effectue le calcul et renvoie le résultat.

Exemple :

![img_10.png](img_10.png)

Ces exemples permettent de vérifier le calcul et le retour de la réponse au client.

### Test 2 : Plusieurs clients simultanés

Plusieurs instances du client Java sont lancées pendant que le serveur reste actif.

Les résultats observés dans la console du serveur sont les suivants :

![img_11.png](img_11.png)

Ce test confirme que le serveur accepte trois connexions, attribue un numéro à chaque client et traite leurs opérations dans des threads distincts.

### Test 3 : Vérification du compteur global

Au cours du test précédent, le compteur passe de 1 à 2 après les deux opérations réussies.

Le nombre total d’opérations affiché correspond au nombre d’opérations traitées avec succès.

**Résultat :** le compteur est partagé entre les threads et les incrémentations observées sont cohérentes.

### Test 4 : Vérification des erreurs

Le serveur doit également gérer les cas invalides :

- Division par zéro.
- Opérateur non reconnu.
- Erreur de communication ou déconnexion du client.

Le serveur doit renvoyer un message approprié lorsqu’une erreur de calcul est détectée. Le traitement d’un client ne doit pas empêcher le serveur de continuer à accepter d’autres connexions.

Exemple : Division par zéro
coté Client :
![img_12.png](img_12.png)
coté serveur : 
![img_13.png](img_13.png)
### Test 5 : Test sur réseau local

Pour vérifier le fonctionnement entre plusieurs machines, il faut démarrer le serveur sur une machine et exécuter le client sur une autre machine connectée au même réseau.


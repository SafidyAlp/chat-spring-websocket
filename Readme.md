1️⃣ Côté Java / Spring Boot

Ton projet utilise Spring Boot avec WebSocket et STOMP pour le chat en temps réel. Les outils principaux sont :

a) Spring Boot
- Framework Java pour créer des applications web rapidement.
- Fournit des dépendances automatiques, des serveurs intégrés (Tomcat), et des configurations simplifiées.
- Dans ton projet, Spring Boot sert de serveur backend pour gérer :
  - Le stockage des messages (en mémoire ici, pas en base).
  - La diffusion des messages à tous les clients connectés via WebSocket.

b) Spring WebSocket + STOMP
- WebSocket : Protocole qui permet une communication bidirectionnelle en temps réel entre le client (navigateur) et le serveur.
- STOMP (Simple Text Oriented Messaging Protocol) :
  - C’est un protocole sur WebSocket qui simplifie l’envoi et la réception de messages.
  - Exemple : /app/chat.send (envoi), /topic/messages (diffusion à tous).

Classes utilisées :
- WebSocketConfig.java
  - Configure le serveur WebSocket.
  - Définit l’endpoint : /ws → les clients JS se connectent à cet endpoint.
  - Définit le broker simple /topic pour diffuser les messages.
  - Définit le préfixe /app pour envoyer les messages côté serveur.

- ChatController.java
  - Reçoit les messages envoyés par les clients (@MessageMapping("/chat.send")).
  - Diffuse le message à tous les abonnés (@SendTo("/topic/messages")).
  - Ajoute l’heure d’envoi (LocalDateTime.now()).

- Message.java
  - Classe modèle pour représenter un message.
  - Contient : sender (pseudo), content (texte), timestamp (date/heure).

c) Autres outils Java
- LocalDateTime → pour gérer l’heure et la date.
- @Controller, @GetMapping, @MessageMapping, @SendTo → annotations Spring pour créer un endpoint web et gérer les messages STOMP.
- Spring Boot Web Starter → fournit Tomcat intégré et support REST/WebSocket.

---

2️⃣ Côté JavaScript

Ton HTML utilise JS pour se connecter au serveur WebSocket et gérer l’affichage des messages.

a) SockJS
- Librairie JS qui simule un WebSocket même si le navigateur ne le supporte pas.
- Elle gère la reconnexion et la compatibilité.
- Exemple : const socket = new SockJS('/ws');

b) STOMP JS
- Librairie JS qui implémente le protocole STOMP sur le WebSocket.
- Permet :
  - stompClient.connect() → se connecter au serveur.
  - stompClient.subscribe() → s’abonner à un topic.
  - stompClient.send() → envoyer un message au serveur.

c) LocalStorage
- Stocke des données sur le navigateur.
- Ici, utilisé pour mémoriser le pseudo de l’utilisateur :

  let pseudo = localStorage.getItem("pseudo");
  if (!pseudo) {
      pseudo = prompt("Entrez votre pseudo") || "Utilisateur";
      localStorage.setItem("pseudo", pseudo);
  }

- La prochaine fois que l’utilisateur revient, il n’a pas besoin de ressaisir son pseudo.

d) DOM et événements
- document.getElementById("messageInput") → récupérer l’input pour taper le message.
- sendBtn.addEventListener("click", sendMessage) → bouton envoyer.
- messageInput.addEventListener("keypress", e => { if(e.key === 'Enter') sendMessage(); }) → envoyer le message en appuyant sur Enter.
- appendChild → ajouter les messages dans la vue.

e) Manipulation du DOM pour le chat
- Messages envoyés → à droite (sent), messages reçus → à gauche (received).
- Chaque message contient :
  - Pseudo de l’envoyeur.
  - Contenu du message.
  - Heure (timestamp) formatée avec toLocaleTimeString().

✅ Résumé du flux complet

1. L’utilisateur ouvre la page → JS demande le pseudo (ou prend celui du localStorage).
2. JS se connecte au serveur Spring Boot via WebSocket/STOMP.
3. L’utilisateur tape un message et clique sur Envoyer ou Enter.
4. JS envoie le message au serveur sur /app/chat.send.
5. Spring Boot reçoit le message → l’envoie à tous les clients abonnés sur /topic/messages.
6. Chaque client reçoit le message → JS affiche le message dans le chat avec pseudo, contenu et heure.

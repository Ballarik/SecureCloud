package com.likepenguins.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        String email = request.get("email");

        // 1. Validazione base dei dati ricevuti
        if (username == null || password == null || email == null) {
            return ResponseEntity.badRequest().body("Errore: Campi mancanti (username, password, email richiesti).");
        }

        // 2. Controlla se lo username esiste già nel database SQLite
        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body("Errore: Questo username è già registrato!");
        }

        // 3. Controlla se l'email esiste già
        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("Errore: Questa email è già associata a un account!");
        }

        // 4. Crea il nuovo utente e salvalo
        // NOTA: Per ora salviamo la password così com'è. Successivamente la cifreremo con un algoritmo sicuro.
        User newUser = new User(username, password, email);
        userRepository.save(newUser);

        return ResponseEntity.ok("Utente registrato con successo nel database di Like Penguins! 🐧");
    }
}
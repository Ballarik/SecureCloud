package com.likepenguins.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // 1. REGISTRAZIONE
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User newUser) {
        // Usiamo l'Optional correttamente per controllare se esiste già lo username
        Optional<User> existingUser = userRepository.findByUsername(newUser.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: Questo username esiste già!");
        }
        
        userRepository.save(newUser);
        return ResponseEntity.ok("Registrazione completata! Attendi che l'amministratore ti assegni una cartella.");
    }

    // 2. LOGIN
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());

        // Controlla se l'utente NON esiste
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Errore: Credenziali errate.");
        }

        User user = userOpt.get();

        // Controlla se la password in chiaro coincide
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Errore: Credenziali errate.");
        }

        return ResponseEntity.ok("Login effettuato con successo!");
    }

    // 3. CARTELLE AUTORIZZATE
    @GetMapping("/folders")
    public ResponseEntity<?> getMyFolders(@RequestParam String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato.");
        }

        User user = userOpt.get();
        // Restituisce la lista reale delle cartelle autorizzate per l'utente (può essere vuota all'inizio)
        return ResponseEntity.ok(user.getCartelleAutorizzate());
    }
}
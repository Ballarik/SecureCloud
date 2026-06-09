package com.likepenguins.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // 1. REGISTRAZIONE: Permette a un utente di crearsi un account (inizialmente senza cartelle)
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User newUser) {
        // Controlla se lo username è già preso
        if (userRepository.findByUsername(newUser.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: Questo username esiste già!");
        }
        
        // Salva l'utente nel database (Le cartelle saranno vuote di default)
        userRepository.save(newUser);
        return ResponseEntity.ok("Registrazione completata! Attendi che l'amministratore ti assegni una cartella.");
    }

    // 2. LOGIN: Controlla le credenziali dell'utente
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());

        // Controlla se l'utente esiste e se la password coincide
        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Errore: Credenziali errate.");
        }

        return ResponseEntity.ok("Login effettuato con successo!");
    }

    // 3. CARTELLE AUTORIZZATE: Restituisce solo la lista delle cartelle assegnate a quell'utente
    @GetMapping("/folders")
    public ResponseEntity<?> getMyFolders(@RequestParam String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato.");
        }

        // Se l'utente non ha cartelle assegnate, restituisce una lista vuota
        List<Folder> mieCartelle = user.getCartelleAutorizzate();
        return ResponseEntity.ok(mieCartelle);
    }
}
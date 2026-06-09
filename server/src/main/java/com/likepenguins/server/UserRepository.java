package com.likepenguins.server;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    // Questo ci servirà per verificare se lo username è già preso durante la registrazione
    Optional<User> findByUsername(String username);
    
    // Questo ci servirà se un domani vorremo fare il login tramite Email anziché Username
    Optional<User> findByEmail(String email);
}

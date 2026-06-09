package com.likepenguins.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Questo metodo magico dice a Spring Boot di creare automaticamente 
    // la query per cercare un utente nel database tramite il suo username!
    User findByUsername(String username);
}
package com.likepenguins.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    
    // Questo metodo serve a cercare una cartella nel database 
    // partendo dal suo percorso reale sul disco fisso
    Folder findByPercorsoReale(String percorsoReale);
}
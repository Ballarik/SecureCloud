package com.likepenguins.server;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "utenti")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // Relazione: Un utente può essere autorizzato per molte cartelle
    @ManyToMany
    @JoinTable(
        name = "permessi_utenti_cartelle",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "folder_id")
    )
    private List<Folder> cartelleAutorizzate;

    // --- GETTER E SETTER ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Folder> getCartelleAutorizzate() { return cartelleAutorizzate; }
    public void setCartelleAutorizzate(List<Folder> cartelleAutorizzate) { this.cartelleAutorizzate = cartelleAutorizzate; }
}
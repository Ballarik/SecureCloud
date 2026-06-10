package com.likepenguins.server;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // Questa verrà salvata cifrata (hash)

    @Column(unique = true, nullable = false)
    private String email;

    // Relazione Molti-a-Molti con le Cartelle
    @ManyToMany
    @JoinTable(
        name = "user_cartelle", // Nome della tabella di giunzione nel DB SQLite
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "cartella_id")
    )
    private List<Folder> cartelleAutorizzate = new ArrayList<>();

    // Costruttore vuoto richiesto da JPA/Hibernate
    public User() {}

    // Costruttore completo
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getter e Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Getter e Setter per le cartelle autorizzate
    public List<Folder> getCartelleAutorizzate() { return cartelleAutorizzate; }
    public void setCartelleAutorizzate(List<Folder> cartelleAutorizzate) { this.cartelleAutorizzate = cartelleAutorizzate; }
}
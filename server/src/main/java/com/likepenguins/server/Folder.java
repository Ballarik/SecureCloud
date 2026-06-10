package com.likepenguins.server;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "cartelle")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeVisualizzato; // Il nome che vede l'utente nel programma

    @Column(nullable = false, unique = true)
    private String percorsoReale; // La posizione vera della cartella sul PC server

    // Relazione inversa per sapere quali utenti possono vedere questa cartella
    @ManyToMany(mappedBy = "cartelleAutorizzate")
    private List<User> utentiAutorizzati;

    // --- GETTER E SETTER ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeVisualizzato() { return nomeVisualizzato; }
    public void setNomeVisualizzato(String nomeVisualizzato) { this.nomeVisualizzato = nomeVisualizzato; }

    public String getPercorsoReale() { return percorsoReale; }
    public void setPercorsoReale(String percorsoReale) { this.percorsoReale = percorsoReale; }

    public List<User> getUtentiAutorizzati() { return utentiAutorizzati; }
    public void setUtentiAutorizzati(List<User> utentiAutorizzati) { this.utentiAutorizzati = utentiAutorizzati; }
}
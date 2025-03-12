package com.northcoders.exhibition_curator_android.model;

import java.util.Set;

public class Collection {
    private Long id;
    private String name;
    private Set<Artwork> artworks;

    public Collection(Long id, String name, Set<Artwork> artworks) {
        this.id = id;
        this.name = name;
        this.artworks = artworks;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Set<Artwork> getArtworks() { return artworks; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setArtworks(Set<Artwork> artworks) { this.artworks = artworks; }
}

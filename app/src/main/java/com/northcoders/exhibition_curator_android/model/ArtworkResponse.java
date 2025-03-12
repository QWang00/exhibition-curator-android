package com.northcoders.exhibition_curator_android.model;


import java.util.List;

public class ArtworkResponse {
    private List<Artwork> artworks;
    private Integer nextPage;
    private Integer prevPage;
    private Integer currentPage;

    public List<Artwork> getArtworks() { return artworks; }
    public Integer getNextPage() { return nextPage; }
    public Integer getPrevPage() { return prevPage; }
    public Integer getCurrentPage() { return currentPage; }
}
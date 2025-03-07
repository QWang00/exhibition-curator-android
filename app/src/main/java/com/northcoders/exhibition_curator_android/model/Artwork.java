package com.northcoders.exhibition_curator_android.model;

public class Artwork {
    private Long id;
    private String sourceArtworkId;
    private String imageUrl;
    private String museumName;
    private String title;
    private String yearMade;
    private String artist;
    private String culture;
    private String artistActiveYear;
    private String description;
    private String tombstone;
    private String preview;

    public Artwork(){

    }

    // Constructor
    public Artwork(Long id, String sourceArtworkId, String imageUrl, String museumName,
                   String title, String yearMade, String artist, String culture,
                   String artistActiveYear, String description, String tombstone, String preview) {
        this.id = id;
        this.sourceArtworkId = sourceArtworkId;
        this.imageUrl = imageUrl;
        this.museumName = museumName;
        this.title = title;
        this.yearMade = yearMade;
        this.artist = artist;
        this.culture = culture;
        this.artistActiveYear = artistActiveYear;
        this.description = description;
        this.tombstone = tombstone;
        this.preview = preview;
    }

    // Getters
    public Long getId() { return id; }
    public String getSourceArtworkId() { return sourceArtworkId; }
    public String getImageUrl() { return imageUrl; }
    public String getMuseumName() { return museumName; }
    public String getTitle() { return title; }
    public String getYearMade() { return yearMade; }
    public String getArtist() { return artist; }
    public String getCulture() { return culture; }
    public String getArtistActiveYear() { return artistActiveYear; }
    public String getDescription() { return description; }
    public String getTombstone() { return tombstone; }
    public String getPreview() { return preview; }
}

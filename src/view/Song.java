//Aditya Rangavajhala, Aryan Dornala
package view;

public class Song {
    private String songid;
    private String title;
    private String artist;
    private String year;
    private String album;

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getId() {
        return title + " " + artist;
    }

    public String getYear() {
        return year;
    }

    public String getAlbum() {
        return album;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setId(String title, String artist) {
        this.songid = this.title + " " + this.artist;
    }

    @Override
    public String toString() {

        return this.title + " by " + this.artist;

    }

}

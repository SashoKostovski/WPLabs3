package mk.ukim.finki.wp.lab.services;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import java.util.List;
import java.util.Optional;

public interface SongService {
    List<Song> listSongs();
    Artist addArtistToSong(Artist artist, Song song);
    Song findByTrackId(String trackId);
    List<Song> findAll();
    Optional<Song> findById(Long id);
    void update(Long id, String title, String trackId, String genre, int releaseYear, Album album);
    void delete(Long id);
    List<Song> searchSongsByTitle(String title);
    Song save(String title, String trackId, String genre, int releaseYear, Album album);
    List <Song> findSongsByAlbumId(Long albumId);
}
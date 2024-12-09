package mk.ukim.finki.wp.lab.services.Impl;

import mk.ukim.finki.wp.lab.repository.impl.inMemoryArtistRepository;
import mk.ukim.finki.wp.lab.repository.impl.inMemorySongRepository;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.repository.jpa.ArtistRepository;
import mk.ukim.finki.wp.lab.repository.jpa.SongRepository;
import mk.ukim.finki.wp.lab.services.SongService;
import org.springframework.stereotype.Service;
import mk.ukim.finki.wp.lab.model.Album;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;


    public SongServiceImpl(SongRepository songRepository, ArtistRepository artistRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public List<Song> listSongs() {
        return songRepository.findAll();
    }

    @Override
    public Artist addArtistToSong(Artist artist, Song song) {

        song.getPerformers().add(artist);
        return artist;
    }

    @Override
    public Song findByTrackId(String trackId) {
        return songRepository.findByTrackId(trackId);
    }

    @Override
    public List<Song> findAll() {
        return songRepository.findAll();
    }

    @Override
    public Optional<Song> findById(Long id) {
        return songRepository.findById(id);
    }

    @Override
    public List<Song> searchSongsByTitle(String title) {
        return songRepository.findAll().stream()
                .filter(song -> song.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Song save(String title, String trackId, String genre, int releaseYear, Album album) {
        Song song = new Song(trackId, title, genre, releaseYear, album);
        songRepository.save(song);
        return song;
    }

    @Override
    public void update(Long id, String title, String trackId, String genre, int releaseYear, Album album) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));
        song.setTitle(title);
        song.setTrackId(trackId);
        song.setGenre(genre);
        song.setReleaseYear(releaseYear);
        song.setAlbum(album);
        songRepository.save(song);
    }

    @Override
    public void delete(Long id) {
        songRepository.deleteById(id);
    }


    public List<Song> findSongsByAlbumId(Long albumId) {
        return songRepository.findAllByAlbum_Id(albumId);
    }
}
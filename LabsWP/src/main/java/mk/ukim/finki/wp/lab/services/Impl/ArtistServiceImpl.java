package mk.ukim.finki.wp.lab.services.Impl;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.repository.impl.inMemoryArtistRepository;
import mk.ukim.finki.wp.lab.repository.impl.inMemorySongRepository;
import mk.ukim.finki.wp.lab.repository.jpa.ArtistRepository;
import mk.ukim.finki.wp.lab.repository.jpa.SongRepository;
import mk.ukim.finki.wp.lab.services.ArtistService;
import org.springframework.stereotype.Service;
import mk.ukim.finki.wp.lab.model.Song;


import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final SongRepository songRepository; // Added dependency for SongRepository

    public ArtistServiceImpl(ArtistRepository artistRepository, SongRepository songRepository) {
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
    }

    @Override
    public List<Artist> listArtists() {
        return artistRepository.findAll();
    }

    @Override
    public Artist findById(Long id) {
        return artistRepository.findById(id).orElse(null);
    }

    @Override
    public void addArtistToSong(Long artistId, Long trackId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        Song song = songRepository.findById(trackId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        song.addPerformer(artist);
        songRepository.save(song);
    }



}
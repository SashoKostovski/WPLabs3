package mk.ukim.finki.wp.lab.services;

import mk.ukim.finki.wp.lab.model.Artist;
import java.util.List;


public interface ArtistService {
    List<Artist> listArtists();
    Artist findById(Long id);
    void addArtistToSong(Long artistId, Long trackId);
}

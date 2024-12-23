package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Artist;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class inMemoryArtistRepository {

    public static List<Artist> findAll() {
        return DataHolder.artistList;
    }

    public Optional<Artist> findById(Long id) {
        return DataHolder.artistList.stream()
                .filter(artist -> artist.getId().equals(id))
                .findFirst();
    }
}

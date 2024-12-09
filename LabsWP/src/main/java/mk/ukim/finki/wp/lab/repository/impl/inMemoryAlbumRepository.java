package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Album;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class inMemoryAlbumRepository {
    public List<Album> findAll() {
        return DataHolder.albumList;
    }

    public Optional<Album> findById(Long id) {
        return DataHolder.albumList.stream()
                .filter(album -> album.getId().equals(id))
                .findFirst();
    }

    public Album save(Album album) {
        DataHolder.albumList.add(album);
        return album;
    }

    public void delete(Long id) {
        DataHolder.albumList.removeIf(album -> album.getId().equals(id));
    }
}

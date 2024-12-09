package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;
@Repository
public class inMemorySongRepository {

    public List<Song> findAll() {
        return DataHolder.songList;
    }

    public Song findByTrackId(String trackId) {
        return DataHolder.songList.stream()
                .filter(song -> song.getTrackId().equals(trackId))
                .findFirst()
                .orElse(null);
    }

    public void deleteById(Long id) {
        DataHolder.songList.removeIf(song -> song.getId() != null && song.getId().equals(id));
    }

    public Optional<Song> findById(Long id) {
        return DataHolder.songList.stream().filter(song -> song.getId().equals(id)).findFirst();

    }

    public void save(Song song) {

        Optional<Song> existingSong = findById(song.getId());
        existingSong.ifPresent(DataHolder.songList::remove);
        DataHolder.songList.add(song);
    }

    public Artist addArtistToSong(Song song, Artist artist) {
        for (Song s : DataHolder.songList) {
            if (s.getTrackId().equals(song.getTrackId())) {
                s.addPerformer(artist);
                return artist;
            }
        }
        return null;
    }
}

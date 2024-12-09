package mk.ukim.finki.wp.lab.web.controller;
import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.services.AlbumService;
import mk.ukim.finki.wp.lab.services.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;
    private final AlbumService albumService;

    public SongController(SongService songService, AlbumService albumService) {
        this.songService = songService;
        this.albumService = albumService;
    }

    @GetMapping
    public String getSongsPage(@RequestParam(required = false) String error, Model model, HttpServletRequest req) {
        List<Song> songs = songService.findAll();
        List<Album> albums = albumService.findAll();

        System.out.println("Songs: " + songs); // Debugging: Ensure songs are retrieved
        System.out.println("Albums: " + albums); // Debugging: Ensure albums are retrieved

        model.addAttribute("songList", songs);
        model.addAttribute("albums", albums);
        model.addAttribute("error", error);
        return "listSongs";
    }

    @GetMapping("/add-form")
    public String addSongPage(Model model) {
        List<Album> albumList = albumService.findAll();
        model.addAttribute("albums", albumList);
        return "add-song";
    }

    @PostMapping("/add")
    public String saveSong(@RequestParam String title,
                           @RequestParam String trackId,
                           @RequestParam String genre,
                           @RequestParam int releaseYear,
                           @RequestParam Long albumId) {
        Album album = albumService.findById(albumId).orElse(null);
        if (album == null) {
            return "redirect:/songs?error=Invalid album";
        }
        songService.save(title, trackId, genre, releaseYear, album); // Save the new song
        return "redirect:/songs";
    }

    @GetMapping("/edit-form/{songId}")
    public String editSong(@PathVariable Long songId, Model model) {
        Song song = songService.findById(songId).orElse(null);
        if (song == null) {
            return "redirect:/songs?error=Song not found";
        }
        model.addAttribute("song", song);
        model.addAttribute("albums", albumService.findAll());
        return "add-song";
    }

    @PostMapping("/edit-form/{songId}")
    public String updateSong(@PathVariable Long songId,
                             @RequestParam String title,
                             @RequestParam String trackId,
                             @RequestParam String genre,
                             @RequestParam int releaseYear,
                             @RequestParam Long albumId) {
        Album album = albumService.findById(albumId).orElse(null);
        if (album == null) {
            return "redirect:/songs?error=Invalid album";
        }
        songService.update(songId, title, trackId, genre, releaseYear, album);
        return "redirect:/songs";
    }

    @PostMapping("/delete/{id}")
    public String deleteSong(@PathVariable Long id) {
        songService.delete(id);
        return "redirect:/songs";
    }
}
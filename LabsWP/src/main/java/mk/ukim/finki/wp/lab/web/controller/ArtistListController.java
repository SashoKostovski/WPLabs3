package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.services.Impl.ArtistServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
public class ArtistListController {

    private final ArtistServiceImpl artistService;

    @Autowired
    public ArtistListController(ArtistServiceImpl artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/artist/artist-list")
    public String showArtistList(Model model) {
        List<Artist> artistList = artistService.listArtists();
        model.addAttribute("artistList", artistList);
        return "listArtists";
    }

    @PostMapping("/artist/artist-list")
    public String processArtistSelection( Model model) {
        List<Artist> artistList = artistService.listArtists();
        model.addAttribute("artistList", artistList);
        return "listArtists";
    }

    @PostMapping("/songs/song-details")
    public String addArtistToSong(
            @RequestParam Long artistId,
            @RequestParam Long trackId
    ) {
        System.out.println("artist added");
        // Logic for associating the artist with the song
        artistService.addArtistToSong(artistId, trackId);

        // Return to the song details page or listSongs.html
        return "redirect:/songs"; // Replace with appropriate redirection or rendering
    }
}

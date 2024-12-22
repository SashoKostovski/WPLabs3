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
    public String showArtistList(@RequestParam(required = false) String trackId, Model model) {
        // Check if trackId is null or empty
        if (trackId == null || trackId.isEmpty()) {
            model.addAttribute("error", "Track ID is required to add an artist!");
            return "redirect:/songs"; // Redirect to the songs list if trackId is missing
        }

        // Fetch the artist list
        List<Artist> artistList = artistService.listArtists();
        if (artistList == null || artistList.isEmpty()) {
            model.addAttribute("error", "No artists found!");
            return "redirect:/songs"; // Handle empty artist list gracefully
        }

        // Pass data to the model
        model.addAttribute("artistList", artistList);
        model.addAttribute("trackId", trackId);

        return "listArtists"; // Render the listArtists.html template
    }

    @PostMapping("/artist/artist-list")
    public String processArtistSelection(@RequestParam(required = false, name = "artistId") Long trackId, Model model) {
        if (trackId == null) {
            model.addAttribute("error", "No track selected!");
            return "listArtists"; // Return to the form with an error message
        }
        System.out.println("Selected track ID: " + trackId);
        return "redirect:/artist/artist-list";
    }

    @PostMapping("/songs/song-details")
    public String addArtistToSong(@RequestParam Long artistId, @RequestParam Long trackId) {
        System.out.println("Artist added: " + artistId + " to track: " + trackId);
        artistService.addArtistToSong(artistId, trackId);
        return "redirect:/listSongs"; // Redirect to the songs list or details
    }



    @GetMapping("/test")
    public String testThymeleaf(Model model) {
        model.addAttribute("message", "Hello Thymeleaf!");
        return "test"; // Matches test.html in templates folder
    }
}

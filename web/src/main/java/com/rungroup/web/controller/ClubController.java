package com.rungroup.web.controller;

import com.rungroup.web.dto.EventDto;
import com.rungroup.web.models.Club;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.security.SecurityUtil;
import com.rungroup.web.service.ClubService;
import com.rungroup.web.service.EventService;
import com.rungroup.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import com.rungroup.web.dto.ClubDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClubController {
    private  ClubService clubService;
    private UserService userService;

    @Autowired
    public ClubController(ClubService clubService, UserService userService) {
        this.clubService = clubService;
        this.userService = userService;
    }

    @GetMapping("/clubs")
    public String listClubs(Model model) {
        List<ClubDto> clubDtos = clubService.findAllClubs();
        model.addAttribute("clubDtos", clubDtos);
        return "clubs-list";
    }

    @GetMapping("/your-clubs")
    public String Home(Model model) {
        String email = SecurityUtil.getSessionUser();
        List<ClubDto> clubDtos = null;
        if(email != null) {
            clubDtos = clubService.findAllClubsByUserEmail(email);
        }
        model.addAttribute("clubDtos", clubDtos);
        return "your-clubs";
    }

    @GetMapping("/clubs/new")
    public String createClubFrom(Model model) {
        Club club = new Club();
        model.addAttribute("club", club);
        return "clubs-create-form";
    }

    @GetMapping("/clubs/{clubID}")
    public String viewClub(@PathVariable("clubID") Long clubID, Model model) {
        UserEntity user = new UserEntity();
        ClubDto clubDto = clubService.findClubById(clubID);
        String email = SecurityUtil.getSessionUser();
        if(email != null) {
            user = userService.findByEmail(email);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("club", clubDto);
        return "clubs-detail";
    }

    @GetMapping("/clubs/search")
    public String searchClub(@RequestParam("query") String title, Model model) {
        List<ClubDto> clubDtos = clubService.searchClubsByTitle(title);
        model.addAttribute("clubDtos", clubDtos);
        return "clubs-list";
    }

    @PostMapping("/clubs/new")
    public String saveClub(@Valid @ModelAttribute("club") ClubDto club,BindingResult result) {
        if (result.hasErrors()) {
            return "clubs-create-form";
        }
        clubService.saveClub(club);
        return "redirect:/your-clubs";
    }

    @GetMapping("/clubs/{clubID}/edit")
    public String editClub(@PathVariable long clubID, Model model) {
        ClubDto clubDto = clubService.findClubById(clubID);
        model.addAttribute("club", clubDto);
        return "clubs-edit-form";
    }

    @PostMapping("/clubs/{clubID}/edit")
    public String updateClub(@PathVariable("clubID") long clubID,@Valid @ModelAttribute("club") ClubDto club,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("club", club);
            return "clubs-edit-form";
        }
        club.setId(clubID);
        clubService.updateClub(club);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubID}/delete")
    public String deleteClub(@PathVariable long clubID) {
        clubService.delete(clubID);
        return "redirect:/clubs";
    }
}

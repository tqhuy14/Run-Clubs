package com.rungroup.web.service;

import com.rungroup.web.dto.ClubDto;
import com.rungroup.web.models.Club;
import org.springframework.stereotype.Service;
import java.util.List;


public interface ClubService {
    List<ClubDto> findAllClubs();

    List<ClubDto> findAllClubsByUserEmail(String email);

    Club saveClub(ClubDto club);
    ClubDto findClubById(Long id);

    void updateClub(ClubDto club);

    void delete(long clubID);

    List<ClubDto> searchClubsByTitle(String title);
}

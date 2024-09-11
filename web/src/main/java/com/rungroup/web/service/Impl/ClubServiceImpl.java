package com.rungroup.web.service.Impl;

import com.rungroup.web.dto.ClubDto;
import com.rungroup.web.models.Club;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.repository.ClubRepository;
import com.rungroup.web.repository.UserRepository;
import com.rungroup.web.security.SecurityUtil;
import com.rungroup.web.service.ClubService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.rungroup.web.mapper.ClubMapper.mapToClub;
import static com.rungroup.web.mapper.ClubMapper.mapToClubDto;

@Service
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private UserRepository userRepository;


    public ClubServiceImpl(ClubRepository clubRepository, UserRepository userRepository) {
        this.userRepository  = userRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    public List<ClubDto> findAllClubs() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream().map((club) -> mapToClubDto(club)).collect(Collectors.toList());
    }

    @Override
    public List<ClubDto> findAllClubsByUserEmail(String email) {
        List<Club> clubs = clubRepository.findAllByCreatedByEmail(email);
        return clubs.stream().map((club) -> mapToClubDto(club)).collect(Collectors.toList());
    }

    @Override
    public Club saveClub(ClubDto clubDto) {
        String email = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByEmail(email);
        Club club = mapToClub(clubDto);
        club.setCreatedBy(user);
        return clubRepository.save(club);
    }

    @Override
    public ClubDto findClubById(Long id) {
        Club club = clubRepository.findById(id).orElse(null);
        return mapToClubDto(club);
    }

    @Override
    public void updateClub(ClubDto clubDto) {
        String email = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByEmail(email);
        Club club = mapToClub(clubDto);
        club.setCreatedBy(user);
        clubRepository.save(club);
    }

    @Override
    public void delete(long clubID) {
        clubRepository.deleteById(clubID);
    }

    @Override
    public List<ClubDto> searchClubsByTitle(String title) {
        List<Club> clubs = clubRepository.searchClubByTitle(title);
        return clubs.stream().map(club -> mapToClubDto(club)).collect(Collectors.toList());
    }
}

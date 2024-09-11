package com.rungroup.web.mapper;

import com.rungroup.web.dto.ClubDto;
import com.rungroup.web.models.Club;

import java.util.stream.Collectors;

import static com.rungroup.web.mapper.EventMapper.mapToEventDto;

public class ClubMapper {
    public static Club mapToClub(ClubDto clubdto) {
        Club club =Club.builder()
                .id(clubdto.getId())
                .title(clubdto.getTitle())
                .photoUrl(clubdto.getPhotoUrl())
                .content(clubdto.getContent())
                .createdBy(clubdto.getCreatedBy())
                .createdOn(clubdto.getCreatedOn())
                .updatedOn(clubdto.getUpdatedOn())
                .build();
        return club;
    }

    public static ClubDto mapToClubDto(Club club) {
        ClubDto clubDto = ClubDto.builder()
                .id(club.getId())
                .title(club.getTitle())
                .photoUrl(club.getPhotoUrl())
                .content(club.getContent())
                .createdBy(club.getCreatedBy())
                .createdOn(club.getCreatedOn())
                .updatedOn(club.getUpdatedOn())
                .events(club.getEvents().stream().map((event) -> mapToEventDto(event)).collect(Collectors.toList()))
                .build();
        return clubDto;
    }
}

package com.rungroup.web.repository;


import com.rungroup.web.models.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {
    Optional<Club> findByTitle(String url);

    @Query("SELECT c FROM Club c WHERE c.createdBy.email = :email")
    List<Club> findAllByCreatedByEmail(String email);

    @Query("SELECT c from Club c where c.title like concat('%', :query, '%') ")
    List<Club> searchClubByTitle(@Param("query") String query);


}

package com.ashish.planservice.repository;

import com.ashish.planservice.model.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrackingRepository extends JpaRepository<Tracking,Long> {
    Optional<Tracking> findByUserId(UUID userId);

    Optional<Tracking> findFirstByUserIdOrderByTrackingDateDesc(UUID userId);


}

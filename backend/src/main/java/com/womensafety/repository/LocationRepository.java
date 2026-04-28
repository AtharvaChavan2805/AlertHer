package com.womensafety.repository;

import com.womensafety.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByUserId(Long userId);
    
    List<Location> findByUserIdOrderByTimestampDesc(Long userId);
    
    @Query("SELECT l FROM Location l WHERE l.user.id = :userId AND l.timestamp >= :startTime ORDER BY l.timestamp DESC")
    List<Location> findRecentLocations(@Param("userId") Long userId, @Param("startTime") LocalDateTime startTime);
    
    @Query("SELECT l FROM Location l WHERE " +
           "ABS(l.latitude - :latitude) <= :distance AND " +
           "ABS(l.longitude - :longitude) <= :distance")
    List<Location> findLocationsNear(@Param("latitude") Double latitude, 
                                      @Param("longitude") Double longitude, 
                                      @Param("distance") Double distance);
}

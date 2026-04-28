package com.womensafety.repository;

import com.womensafety.model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByUserId(Long userId);
    List<Incident> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    @Query("SELECT i FROM Incident i WHERE " +
           "ABS(i.latitude - :latitude) <= :distance AND " +
           "ABS(i.longitude - :longitude) <= :distance")
    List<Incident> findIncidentsNear(@Param("latitude") Double latitude, 
                                      @Param("longitude") Double longitude, 
                                      @Param("distance") Double distance);
}

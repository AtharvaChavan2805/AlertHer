package com.womensafety.repository;

import com.womensafety.model.SOSAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SOSAlertRepository extends JpaRepository<SOSAlert, Long> {
    List<SOSAlert> findByUserId(Long userId);
    List<SOSAlert> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<SOSAlert> findByStatus(String status);
}

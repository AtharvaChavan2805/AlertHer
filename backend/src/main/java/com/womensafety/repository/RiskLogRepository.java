package com.womensafety.repository;

import com.womensafety.model.RiskLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RiskLogRepository extends JpaRepository<RiskLog, Long> {
    List<RiskLog> findByUserId(Long userId);
    List<RiskLog> findByRiskLevel(String riskLevel);
}

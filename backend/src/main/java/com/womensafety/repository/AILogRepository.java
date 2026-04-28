package com.womensafety.repository;

import com.womensafety.model.AILog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AILogRepository extends JpaRepository<AILog, Long> {
    List<AILog> findByUserId(Long userId);
    List<AILog> findByRiskLevel(String riskLevel);
}

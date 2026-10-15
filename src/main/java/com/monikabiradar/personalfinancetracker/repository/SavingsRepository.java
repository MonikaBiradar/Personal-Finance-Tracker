package com.monikabiradar.personalfinancetracker.repository;

import com.monikabiradar.personalfinancetracker.entity.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SavingsRepository extends JpaRepository<Savings, Long> {

    Optional<Savings> findBySavingsIdAndUserUserId(Long savingsId, Long userId);

    List<Savings> findByUserUserIdAndSavingsNameIgnoreCase(Long userId, String savingsName);

    boolean existsByUserUserIdAndSavingsNameAndSavingsIdNot (Long userId, String SavingsName, Long SavingsId);

    List<Savings> findByUserUserId(Long userId);

    @Query("""
        SELECT COALESCE(SUM(s.currentAmount), 0)
        FROM Savings s
        WHERE s.user.userId = :userId
          AND s.savingsStatus = com.monikabiradar.personalfinancetracker.enums.SavingsStatus.ACTIVE
    """)
    BigDecimal sumActiveSavingsByUserId(@Param("userId") Long userId);

}

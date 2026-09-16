package com.monikabiradar.personalfinancetracker.repository;

import com.monikabiradar.personalfinancetracker.entity.Savings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavingsRepository extends JpaRepository<Savings, Long> {

    Optional<Savings> findBySavingsIdAndUserUserId(Long savingsId, Long userId);

    List<Savings> findByUserUserIdAndSavingsNameIgnoreCase(Long userId, String savingsName);

    boolean existsByUserUserIdAndSavingsNameAndSavingsIdNot (Long userId, String SavingsName, Long SavingsId);

}

package com.monikabiradar.personalfinancetracker.repository;

import com.monikabiradar.personalfinancetracker.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    Optional<Income> findByIncomeIdAndUserUserId(Long incomeId, Long userId);

    List<Income> findByUserUserIdAndIncomeNameIgnoreCase(Long userId, String incomeName);

    boolean existsByUserUserIdAndIncomeNameAndIncomeIdNot (Long userId, String incomeName, Long incomeId);
}

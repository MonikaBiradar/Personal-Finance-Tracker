package com.monikabiradar.personalfinancetracker.service;

import com.monikabiradar.personalfinancetracker.dto.SavingsRequest;
import com.monikabiradar.personalfinancetracker.entity.Savings;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.enums.SavingsStatus;
import com.monikabiradar.personalfinancetracker.exception.DuplicateSavingsException;
import com.monikabiradar.personalfinancetracker.exception.SavingsNotFoundException;
import com.monikabiradar.personalfinancetracker.repository.SavingsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SavingsService {

    private final SavingsRepository savingsRepository;

    public SavingsService(SavingsRepository savingsRepository) {
        this.savingsRepository = savingsRepository;
    }

    public Savings addSavings(SavingsRequest request, User user) {
        Savings savings = new Savings();

        savings.setSavingsName(request.getSavingsName());
        savings.setTargetAmount(request.getTargetAmount());
        savings.setSavingsStatus(SavingsStatus.ACTIVE);
        savings.setUser(user);

        return savingsRepository.save(savings);
    }

    public Savings updateTargetSavings(Long savingsId, BigDecimal newTargetAmount, User user) {

        Savings savings = savingsRepository
                .findBySavingsIdAndUserUserId(savingsId, user.getUserId())
                .orElseThrow(()-> new SavingsNotFoundException("Savings not found"));

        savings.setTargetAmount(newTargetAmount);
        return savingsRepository.save(savings);
    }

    public Savings updateCurrentSavings(Long savingsId, BigDecimal newCurrentAmount, User user) {
        Savings savings = savingsRepository
                .findBySavingsIdAndUserUserId(savingsId, user.getUserId())
                .orElseThrow(()-> new SavingsNotFoundException("Savings not found"));

        savings.setCurrentAmount(newCurrentAmount);
        return savingsRepository.save(savings);
    }

    public List<Savings> searchSavings(String savingsName, User user) {
        return savingsRepository.findByUserUserIdAndSavingsNameIgnoreCase(user.getUserId(), savingsName);
    }

    public void renameSavings(Long savingsId, String newSavingsName, User user) {
        Savings savings = savingsRepository
                .findBySavingsIdAndUserUserId(savingsId, user.getUserId())
                .orElseThrow(()-> new SavingsNotFoundException("Savings not found"));

        boolean exists = savingsRepository.existsByUserUserIdAndSavingsNameAndSavingsIdNot(user.getUserId(), newSavingsName, savingsId);

        if(exists) {
            throw new DuplicateSavingsException("Savings already exists");
        }
        savings.setSavingsName(newSavingsName);
        savingsRepository.save(savings);
    }

    public void removeSavings(Long savingsId, User user) {
        Savings savings = savingsRepository
                .findBySavingsIdAndUserUserId(savingsId, user.getUserId())
                .orElseThrow(()-> new SavingsNotFoundException("Savings not found"));

        savings.setSavingsStatus(SavingsStatus.INACTIVE);
        savingsRepository.save(savings);
    }

}

package com.monikabiradar.personalfinancetracker.dto;

import com.monikabiradar.personalfinancetracker.enums.UpdateFrequency;

public class FrequencyUpdateRequest {

    private UpdateFrequency frequency;

    public FrequencyUpdateRequest(UpdateFrequency frequency) {
        this.frequency = frequency;
    }

    public UpdateFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(UpdateFrequency frequency) {
        this.frequency = frequency;
    }
}

package org.aseccxz.devices.config;

import java.util.Set;

public class OscilloscopeConfig {
    private final int numberOfChannels;
    private final Set<InputImpedance> supportedImpedance;
    private final double maxFrequency;

    public OscilloscopeConfig(int numberOfChannels, Set<InputImpedance> supportedImpedance, double maxFrequency) {
        this.numberOfChannels = numberOfChannels;
        this.supportedImpedance = supportedImpedance;
        this.maxFrequency = maxFrequency;
    }

    public int getNumberOfChannels() {
        return numberOfChannels;
    }

    public Set<InputImpedance> getSupportedImpedance() {
        return supportedImpedance;
    }

    public double getMaxFrequency() {
        return maxFrequency;
    }
}
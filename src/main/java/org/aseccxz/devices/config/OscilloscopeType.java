package org.aseccxz.devices.config;

import java.util.Set;

public enum OscilloscopeType {
    MSO5204(
            "MSO5204",
            OscilloscopeSeries.MSO5000,
            new OscilloscopeConfig(4, Set.of(InputImpedance.HIGH), 200000000.0)
    );
    private final String deviceName;
    private final OscilloscopeSeries series;
    private final OscilloscopeConfig config;

    OscilloscopeType(String deviceName, OscilloscopeSeries series, OscilloscopeConfig config) {
        this.deviceName = deviceName;
        this.series = series;
        this.config = config;
    }

    public OscilloscopeSeries getSeries() {
        return series;
    }

    public OscilloscopeConfig getConfig() {
        return config;
    }
    public static OscilloscopeType getConfigByName(String name){
        for (OscilloscopeType type : values()) {
            if (type.deviceName.equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unsupported device: " + name);
    }
}

package org.aseccxz.procedures;

import org.aseccxz.devices.config.OscilloscopeType;

import java.awt.*;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class ProcedurePointsFactory {
    public ProcedurePointsFactory() {}

    public static Map<ProcedureKey, List<Double>> getMapForDevice(OscilloscopeType type) {
        Map<ProcedureKey, List<Double>> map = new EnumMap<>(ProcedureKey.class);
        switch (type.getSeries()) {
            case DS1000Z -> {
                map.put(ProcedureKey.SCALE_MEAS_HIGH, PointTemplates.SCALE_MEAS_DS1000Z_DS7000_MSO7000_MSO8000_HIGH);
                map.put(ProcedureKey.OFFSET_MEAS_SCALES, PointTemplates.OFFSET_MEAS_SCALES_MSO_DSO_DS);
                map.put(ProcedureKey.OFFSET_MEAS_HIGH, PointTemplates.OFFSET_MEAS_POINTS_HIGH_DS1000);
                map.put(ProcedureKey.BW_MEAS, PointTemplates.BW_MEAS_POINTS_MSO_DSO_DS);
            }
            case MSO5000 -> {
                map.put(ProcedureKey.SCALE_MEAS_HIGH, PointTemplates.SCALE_MEAS_MSO5000);
                map.put(ProcedureKey.OFFSET_MEAS_SCALES, PointTemplates.OFFSET_MEAS_SCALES_MSO_DSO_DS);
                map.put(ProcedureKey.OFFSET_MEAS_HIGH, PointTemplates.OFFSET_MEAS_POINTS_HIGH_DS7000_MSO);
                map.put(ProcedureKey.BW_MEAS, PointTemplates.BW_MEAS_POINTS_MSO_DSO_DS);
            }
        }
        return Map.copyOf(map);
    }
}

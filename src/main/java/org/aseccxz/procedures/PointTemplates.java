package org.aseccxz.procedures;

import java.util.List;

public final class PointTemplates {
    private PointTemplates(){}

    public static final List<Double> SCALE_MEAS_DS1000Z_DS7000_MSO7000_MSO8000_HIGH =
            List.of(0.003, 0.006, 0.015, 0.03, 0.06, 0.15, 0.3, 0.6, 1.5, 3.0, 6.0, 15.0, 30.0);
    public static final List<Double> SCALE_MEAS_MSO5000 =
            List.of(0.0015, 0.003, 0.006, 0.015, 0.03, 0.06, 0.15, 0.3, 0.6, 1.5, 3.0, 6.0, 15.0, 30.0);
    public static final List<Double> SCALE_MEAS_DS7000_MSO7000_MSO8000_LOW =
            List.of(0.003, 0.006, 0.015, 0.03, 0.06, 0.15, 0.3, 0.6, 1.5, 3.0);
    public static final List<Double> OFFSET_MEAS_SCALES_MSO_DSO_DS =
            List.of(0.003, 0.006, 0.015, 0.03, 0.06, 0.15, 0.3, 0.6, 1.5, 3.0);
    public static final List<Double> OFFSET_MEAS_POINTS_HIGH_DS1000 =
            List.of(1.0, 2.0, 2.0, 40.0, 50.0, 100.0);
    public static final List<Double> OFFSET_MEAS_POINTS_HIGH_DS7000_MSO =
            List.of(1.0, 10.0, 30.0, 40.0, 50.0, 100.0);
    public static final List<Double> OFFSET_MEAS_POINTS_LOW_DS7000_MSO7000_MSO8000 =
            List.of(1.0, 1.0, 2.0, 4.0, 4.0);
    public static final List<Double> BW_MEAS_POINTS_MSO_DSO_DS =
            List.of(0.01, 0.025, 0.05, 0.1, 0.25, 0.5, 1.0, 2.5, 5.0);


}

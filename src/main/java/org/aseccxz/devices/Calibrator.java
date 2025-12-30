package org.aseccxz.devices;

import xyz.froud.jvisa.JVisaException;
import xyz.froud.jvisa.JVisaInstrument;

public class Calibrator {
    private final JVisaInstrument calibrator;

    public Calibrator(JVisaInstrument calibrator) {
        this.calibrator = calibrator;
    }

    public void write (String command) throws JVisaException {
        calibrator.write(command);
    }
    public String queryString(String command) throws JVisaException {
        return calibrator.queryString(command);
    }
    public void setImp(String imp) throws JVisaException {
        String command = switch (imp.toLowerCase()) {
            case "high" -> "ROUT:SIGNal:IMP 1000000";
            case "low" -> "ROUT:SIGNal:IMP 50";
            default -> throw new IllegalStateException("Unexpected value: " + imp.toLowerCase());
        };
        calibrator.write(command);
    }
    public void measOhm() throws JVisaException {
        calibrator.write("CONF:RES");
    }
    public void setOutput (int output) throws JVisaException {
        if (output == 0) {
            calibrator.write("OUTP:STATe 0");
        }
        if (output == 1) {
            calibrator.write("OUTP:STATe 1");
        }
    }
    public void setVoltage (double value) throws JVisaException {
        calibrator.write(":VOLT " + value);
    }
    public void setFreq (double value) throws JVisaException {
        calibrator.write(":FREQ " + value);
    }
}

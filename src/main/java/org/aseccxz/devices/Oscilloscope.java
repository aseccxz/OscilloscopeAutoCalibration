package org.aseccxz.devices;

import org.aseccxz.devices.config.OscilloscopeConfig;
import org.aseccxz.devices.config.OscilloscopeType;
import xyz.froud.jvisa.JVisaException;
import xyz.froud.jvisa.JVisaInstrument;

public class Oscilloscope {
    private final JVisaInstrument oscilloscope;
    private final OscilloscopeType type;

    public Oscilloscope(JVisaInstrument oscilloscope) throws JVisaException {
        this.oscilloscope = oscilloscope;
        this.type = OscilloscopeType.getConfigByName(oscilloscope.getModelName());
    }

    public OscilloscopeType getType() {
        return type;
    }

    public OscilloscopeConfig getConfig() {
        return type.getConfig();
    }

    public void write (String command) throws JVisaException {
        oscilloscope.write(command);
    }
    public String queryString(String command) throws JVisaException {
        return oscilloscope.queryString(command);
    }
    public void setImp(int channel, String imp) throws JVisaException {
        String command = switch (imp.toLowerCase()) {
            case "high" -> "CHANnel" + channel + ":IMP OMEG";
            case "low" -> "CHANnel" + channel + ":IMP FIFTy";
            default -> throw new IllegalStateException("Unexpected value: " + imp.toLowerCase());
        };
        oscilloscope.write(command);
    }
    public void setVScale(int channel, double value) throws JVisaException {
        oscilloscope.write(":CHANnel" + channel + ":SCALe " + value);
    }
    public void setHScale(double value) throws JVisaException {
        oscilloscope.write(":TIMebase:MAIN:SCALe " + value);
    }
    public void setOffset(int channel, double value) throws JVisaException {
        oscilloscope.write(":CHANnel" + channel + ":OFFSet " + value);
    }
    public void setDisplayChannel(int channel, int state) throws JVisaException {
        if (state == 1) {
            oscilloscope.write(":CHANnel" + channel + ":DISPlay ON");
        }
        if (state == 0) {
            oscilloscope.write(":CHANnel" + channel + ":DISPlay OFF");
        }
    }
    public void setBW(int channel, String value) throws JVisaException {
        oscilloscope.write(":CHANnel" + channel + ":BWLimit " + value);
    }
    public void setCoupling(int channel, String value) throws JVisaException {
        oscilloscope.write(":CHANnel" + channel + ":COUPling " + value);
    }
}

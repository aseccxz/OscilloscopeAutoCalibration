package org.aseccxz;

import org.aseccxz.devices.Calibrator;
import org.aseccxz.devices.Oscilloscope;
import org.aseccxz.devices.config.OscilloscopeType;
import org.aseccxz.procedures.ProcedurePointsFactory;
import xyz.froud.jvisa.JVisaException;
import xyz.froud.jvisa.JVisaInstrument;
import xyz.froud.jvisa.JVisaResourceManager;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final static int NUMBER_OF_CHANNELS = 2;

    public static void main(String[] args) throws JVisaException, InterruptedException {
        JVisaResourceManager rm = new JVisaResourceManager();
        String[] resourcesNames = rm.findResources();
        System.out.println(Arrays.toString(resourcesNames));
        JVisaInstrument occil = rm.openInstrument(resourcesNames[0]);
        JVisaInstrument cal = rm.openInstrument("GPIB0::18::INSTR");
        System.out.println(occil.getModelName());

        //String name = occil.getModelName();
       // OscilloscopeType type = OscilloscopeType.getConfigByName(name);
        //System.out.println(type.getSeries());


        Calibrator calibrator = new Calibrator(cal);
        Oscilloscope oscilloscope = new Oscilloscope(occil);

        System.out.println(ProcedurePointsFactory.getMapForDevice(oscilloscope.getType()));


        //impedanceMeas(oscilloscope, calibrator);
      // scaleMeas(oscilloscope, calibrator);
        //offsetMeas(oscilloscope, calibrator);
        //BWMeas(oscilloscope, calibrator);
        //refGeneratorMeas(oscilloscope, calibrator);
        occil.close();
        rm.close();
    }

    public static void impedanceMeas(Oscilloscope os, Calibrator cal) throws JVisaException, InterruptedException {
        List<String> impedance = List.of("high");
        Scanner input = new Scanner(System.in);
        os.write("*RST");
        cal.write("*RST");
        Thread.sleep(5000);
        for (int i = 1; i <= NUMBER_OF_CHANNELS; i++) {
            System.out.println("Connect to Ch " + i + " and press any button");
            input.nextLine();
            os.setDisplayChannel(i,1);
            cal.measOhm();
            for (String imp : impedance) {
                System.out.println("Ch " + i + " impedance " + imp);
                cal.setImp(imp);
                os.setImp(i, imp);
                os.setVScale(i, 0.05);
                cal.setOutput(1);
                Thread.sleep(3000);
                System.out.println(Double.parseDouble(cal.queryString("READ?")));

                os.setVScale(i, 0.2);
                Thread.sleep(3000);
                System.out.println(Double.parseDouble(cal.queryString("READ?")));
                cal.setOutput(0);
            }
            os.setDisplayChannel(i,0);
        }
    }
    public static void scaleMeas(Oscilloscope os, Calibrator cal) throws JVisaException, InterruptedException {
        List<String> impedance = List.of("high");
        List<Double> points = List.of(0.003, 0.006, 0.015, 0.03, 0.06, 0.15, 0.3, 0.6, 1.5, 3.0, 6.0, 15.0, 30.0);
        Scanner input = new Scanner(System.in);

        os.write("*RST");
        cal.write("*RST");
        Thread.sleep(5000);
        cal.write(":FUNC DC");

        for (int i = 1; i <= NUMBER_OF_CHANNELS; i++) {
            System.out.println("Connect to Ch " + i + " and press any button");
            input.nextLine();
            os.write(":TRIGger:EDGE:SOURce CHANnel" + i);
            os.setDisplayChannel(i,1);
            os.setBW(i, "20M");
            os.setCoupling(i, "DC");
            os.write(":MEASure:STATistic:ITEM VAVG,CHANnel" + i);
            Thread.sleep(1000);
            for (String imp : impedance) {
                System.out.println("Ch " + i + " impedance " + imp);
                cal.setVoltage(0.01);
                Thread.sleep(500);
                cal.setImp(imp);
                Thread.sleep(2000);
                os.setImp(i, imp);
                Thread.sleep(2000);
                for (Double point : points) {
                    if (imp.equals("low") && point > 3.0) {
                        break;
                    }
                    double rounder = point > 1.0 ? 1.0 : 1000.0;
                    cal.setVoltage(point);
                    os.setVScale(i, Math.round(point / 3 * 10000) / 10000.0);
                    cal.setOutput(1);
                    Thread.sleep(1000);
                    os.write(":MEASure:STATistic:RESet");
                    Thread.sleep(3000);
                    System.out.println(Math.round(Double.parseDouble(os.queryString(":MEASure:STATistic:ITEM? AVERages,VAVG,CHANnel" + i)) * rounder * 100)/100.0);

                    cal.setVoltage(-point);
                    Thread.sleep(1000);
                    os.write(":MEASure:STATistic:RESet");
                    Thread.sleep(3000);
                    System.out.println(Math.round(Double.parseDouble(os.queryString(":MEASure:STATistic:ITEM? AVERages,VAVG,CHANnel" + i)) * rounder * 100)/100.0);
                }
                cal.setOutput(0);
                Thread.sleep(1000);
            }
            os.write(":MEASure:CLEar ITEM1");
            os.setDisplayChannel(i,0);
            cal.setOutput(0);
        }
    }
    public static void offsetMeas(Oscilloscope os, Calibrator cal) throws JVisaException, InterruptedException {
        List<Double> scales = List.of(0.01, 0.1, 0.2, 0.5, 1.0, 10.0);
        List<Double> pointsLow = List.of(1.0, 1.0, 2.0, 4.0, 4.0);
        List<Double> pointsHigh = List.of(1.0, 10.0, 30.0, 40.0, 50.0, 100.0);
        List<String> impedance = List.of("high");
        Scanner input = new Scanner(System.in);

        os.write("*RST");
        cal.write("*RST");
        Thread.sleep(5000);
        cal.write(":FUNC DC");

        for (int i = 1; i <= NUMBER_OF_CHANNELS; i++) {
            System.out.println("Connect to Ch " + i + " and press any button");
            input.nextLine();
            os.write(":TRIGger:EDGE:SOURce CHANnel" + i);
            os.setDisplayChannel(i, 1);
            os.setBW(i, "20M");
            os.setCoupling(i, "DC");
            os.write(":MEASure:STATistic:ITEM VAVG,CHANnel" + i);
            Thread.sleep(1000);
            for (String imp : impedance) {
                System.out.println("Ch " + i + " impedance " + imp);
                cal.setVoltage(0.01);
                Thread.sleep(500);
                cal.setImp(imp);
                Thread.sleep(2000);
                os.setImp(i, imp);
                Thread.sleep(2000);
                int counter = 0;
                List<Double> points = switch (imp) {
                    case "low" -> pointsLow;
                    case "high" -> pointsHigh;
                    default -> throw new IllegalStateException("Unexpected value: " + imp);
                };
                for (Double point : points) {
                    double scale = scales.get(counter);
                    cal.setVoltage(point);
                    os.setVScale(i, scale);
                    os.setOffset(i,-point);
                    cal.setOutput(1);
                    Thread.sleep(1000);
                    if (point >= 100.0) {
                        Thread.sleep(5000);
                    }
                    os.write(":MEASure:STATistic:RESet");
                    Thread.sleep(3000);
                    System.out.println(Math.round(Double.parseDouble(os.queryString(":MEASure:STATistic:ITEM? AVERages,VAVG,CHANnel" + i)) * 1000)/1000.0);

                    cal.setVoltage(-point);
                    os.setOffset(i,point);
                    Thread.sleep(1000);
                    os.write(":MEASure:STATistic:RESet");
                    Thread.sleep(3000);
                    System.out.println(Math.round(Double.parseDouble(os.queryString(":MEASure:STATistic:ITEM? AVERages,VAVG,CHANnel" + i)) * 1000)/1000.0);
                    counter++;
                }
                cal.setOutput(0);
            }
            os.write(":MEASure:CLEar ITEM1");
            os.setDisplayChannel(i,0);
            cal.setOutput(0);
        }
    }
    public static void BWMeas(Oscilloscope os, Calibrator cal) throws JVisaException, InterruptedException {
        List<Double> points = List.of(0.01, 0.025, 0.05, 0.1, 0.25, 0.5, 1.0, 2.5, 5.0);
        List<String> impedance = List.of("high");
        Scanner input = new Scanner(System.in);

        os.write("*RST");
        cal.write("*RST");
        Thread.sleep(5000);
        cal.write(":FUNC SIN");
        cal.setImp("high");

        for (int i = 1; i <= NUMBER_OF_CHANNELS; i++) {
            System.out.println("Connect to Ch " + i + " and press any button");
            input.nextLine();
            os.setImp(i, "high");
            os.write(":TRIGger:EDGE:SOURce CHANnel" + i);
            os.setDisplayChannel(i, 1);
            os.write(":MEASure:STATistic:ITEM VAMP,CHANnel" + i);
            Thread.sleep(1000);
            for (double point : points) {
                cal.setVoltage(point);
                os.setVScale(i, Math.round(point / 5 * 1000) / 1000.0);
                cal.setFreq(50000.0);
                os.setHScale(0.0001);
                cal.setOutput(1);
                Thread.sleep(1000);
                os.write(":MEASure:STATistic:RESet");
                Thread.sleep(3000);
                System.out.println(Math.round(Double.parseDouble(os.queryString(":MEASure:STATistic:ITEM? AVERages,VAMP,CHANnel" + i)) * 100000)/100.0);
                cal.setFreq(100000000.0);
                os.setHScale(0.00000001);
                Thread.sleep(1000);
                os.write(":MEASure:STATistic:RESet");
                Thread.sleep(3000);
                System.out.println(Math.round(Double.parseDouble(os.queryString(":MEASure:STATistic:ITEM? AVERages,VAMP,CHANnel" + i)) * 100000)/100.0);
            }
            os.write(":MEASure:CLEar ITEM1");
            os.setDisplayChannel(i,0);
            cal.setOutput(0);
        }
    }
    public static void refGeneratorMeas(Oscilloscope os, Calibrator cal) throws JVisaException, InterruptedException {
        Scanner input = new Scanner(System.in);
        os.write("*RST");
        cal.write("*RST");
        Thread.sleep(5000);
        System.out.println("Connect to Ch " + 1 + " and press any button");
        input.nextLine();

        cal.write(":FUNC SIN");
        cal.setImp("high");
        cal.setVoltage(0.6);
        cal.setFreq(10000000.0);
        os.setImp(1, "high");
        os.setVScale(1, 0.1);
        os.setHScale(0.1);
        os.write(":ACQuire:MDEPth 1k ");
        os.write(":MEASure:STATistic:ITEM FREQ,CHANnel1");
        cal.setOutput(1);
        Thread.sleep(5000);
        System.out.println(Double.parseDouble(os.queryString(":MEASure:STATistic:ITEM? AVERages,FREQ,CHANnel1")));
        cal.setOutput(0);
    }
}
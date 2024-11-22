package com.example.element_things.util;

public class SoundRMSCalculate {
    public static double value(byte[] audioData){
        long lSum = 0;
        for (byte audioDatum : audioData) {
            lSum += audioDatum;
        }
        double dAvg = (double) lSum / audioData.length;
        double sumMeanSquare = 0d;
        for (byte audioDatum : audioData) {
            sumMeanSquare += Math.pow(audioDatum - dAvg, 2d);
        }
        double averageMeanSquare = sumMeanSquare / audioData.length;
        double value = Math.sqrt(averageMeanSquare);
        return value > 50.0d ? value - 50d : 0d;
    }
}

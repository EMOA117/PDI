/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package color;

/**
 * Interface que tiene el cojunto de iluminantes de 1931 y 1964
 * @author sdelaot
 */
public interface Iluminante {
    /**
     * Color normalizado de los iluminantes dos y diez (fuentes de luz),
     * de 0 a 19 X, Y, Z dos (1931) y de 20 a 39 X, Y, Z diez (1964).
     */
    double [][] REFERENCIA = {
        //  X 2   ,   Y 2  ,   Z 2  1931
        { 109.8500, 100.000,  35.5850 },// DOS A    Incandescent/tungsten
        {  99.0927, 100.000,  85.3130 },// DOS B    Old direct sunlight at noon
        {  98.0740, 100.000, 118.2320 },// DOS C    Old daylight
        {  96.4220, 100.000,  82.5210 },// DOS D50  ICC profile PCS
        {  95.6820, 100.000,  92.1490 },// DOS D55  Mid-morning daylight
        {  95.0470, 100.000, 108.8830 },// DOS D65  Daylight, sRGB, Adobe-RGB
        {  94.9720, 100.000, 122.6380 },// DOS D75  North sky daylight
        { 100.0000, 100.000, 100.0000 },// DOS E    Equal energy
        {  92.8340, 100.000, 103.6650 },// DOS F1   Daylight Fluorescent
        {  99.1870, 100.000,  67.3950 },// DOS F2   Cool fluorescent
        { 103.7540, 100.000,  49.8610 },// DOS F3   White Fluorescent
        { 109.1470, 100.000,  38.8130 },// DOS F4   Warm White Fluorescent
        {  90.8720, 100.000,  98.7230 },// DOS F5   Daylight Fluorescent
        {  97.3090, 100.000,  60.1910 },// DOS F6   Lite White Fluorescent
        {  95.0440, 100.000, 108.7550 },// DOS F7   Daylight fluorescent, 
                                        //          D65 simulator
        {  96.4130, 100.000,  82.3330 },// DOS F8   Sylvania F40, D50 simulator
        { 100.3650, 100.000,  67.8680 },// DOS F9   Cool White Fluorescent
        {  96.1740, 100.000,  81.7120 },// DOS F10  Ultralume 50, Philips TL85
        { 100.9660, 100.000,  64.3700 },// DOS F11  Ultralume 40, Philips TL84
        { 108.0460, 100.000,  39.2280 },// DOS F12  Ultralume 30, Philips TL83
        //  X 10  ,   Y 10 ,   Z 10 1964
        { 111.1440, 100.000,  35.2000 },// DIEZ A   Incandescent/tungsten 
        {  99.1780, 100.000,  84.3493 },// DIEZ B   Old direct sunlight at noon
        {  97.2850, 100.000, 116.1450 },// DIEZ C   Old daylight
        {  96.7200, 100.000,  81.4270 },// DIEZ D50 ICC profile PCS
        {  95.7990, 100.000,  90.9260 },// DIEZ D55 Mid-morning daylight
        {  94.8110, 100.000, 107.3040 },// DIEZ D65 Daylight, sRGB, Adobe-RGB
        {  94.4160, 100.000, 120.6410 },// DIEZ D75 North sky daylight
        { 100.0000, 100.000, 100.0000 },// DIEZ E   Equal energy
        {  94.7910, 100.000, 103.1910 },// DIEZ F1  Daylight Fluorescent
        { 103.2800, 100.000,  69.0260 },// DIEZ F2  Cool fluorescent
        { 108.9680, 100.000,  51.9650 },// DIEZ F3  White Fluorescent
        { 114.9610, 100.000,  40.9630 },// DIEZ F4  Warm White Fluorescent
        {  93.3690, 100.000,  98.6360 },// DIEZ F5  Daylight Fluorescent
        { 102.1480, 100.000,  62.0740 },// DIEZ F6  Lite White Fluorescent
        {  95.7920, 100.000, 107.6870 },// DIEZ F7  Daylight fluorescent, 
                                        //          D65 simulator
        {  97.1150, 100.000,  81.1350 },// DIEZ F8  Sylvania F40,D50 simulator
        { 102.1160, 100.000,  67.8260 },// DIEZ F9  Cool White Fluorescent
        {  99.0010, 100.000,  83.1340 },// DIEZ F10 Ultralume 50, Philips TL85
        { 103.8660, 100.000,  65.6270 },// DIEZ F11 Ultralume 40, Philips TL84
        { 111.4280, 100.000,  40.3530 } // DIEZ F12 Ultralume 30, Philips TL83

        /*
        {109.850, 100.000,  35.585}, // A       0   DOS
        { 98.074, 100.000, 118.222}, // C       1   DOS
        { 96.422, 100.000,  82.521}, // D50     2   DOS
        { 95.682, 100.000,  92.149}, // D55     3   D0S
        { 95.047, 100.000, 108.883}, // D65     4   DOS
        { 94.972, 100.000, 122.638}, // D75     5   DOS
        { 99.186, 100.000,  67.393}, // F2      6   DOS
        { 95.041, 100.000, 108.747}, // F7      7   DOS
        {100.962, 100.000,  64.350}, // F11     8   DOS
        {111.114, 100.000,  35.200}, // A       9   DIEZ
        { 97.285, 100.000, 116.145}, // C      10   DIEZ
        { 96.720, 100.000,  81.427}, // D50    11   DIEZ
        { 95.799, 100.000,  90.926}, // D55    12   DIEZ
        { 95.811, 100.000, 107.304}, // D65    13   DIEZ
        { 94.416, 100.000, 120.641}, // D75    14   DIEZ
        {103.279, 100.000,  69.027}, // F2     15   DIEZ
        { 95.792, 100.000, 107.686}, // F7     16   DIEZ
        {103.863, 100.000,  65.607}  // F11    17   DIEZ
        */
    };
}

package com.arny.sentry.data.spaceutils;


import com.arny.sentry.data.utils.MathUtils;


/**
 * @author i.sedoy
 */
public class AstroUtils {

    public enum DistanceTypes {
        metre, km, AU, LY, PC
    }

    /**
     * @param distance in m
     * @return converted distance
     */
    public static String getSmartDistance(double distance) {
        return getSmartDistance(distance, true);
    }

    /**
     * @param distance     in m
     * @param addDimension
     * @return converted distance
     */
    public static String getSmartDistance(double distance, boolean addDimension) {
        if (distance < 1e3) {
            return MathUtils.simpleDoubleFormat(distance) + (addDimension ? "(м)" : "");
        } else if (distance < AstroConst.AU) {
            double convert = distanceConvert(distance, DistanceTypes.metre, DistanceTypes.km);
            return MathUtils.simpleDoubleFormat(MathUtils.round(convert, 3)) + (addDimension ? "км" : "");
        } else if (distance < AstroConst.LY) {
            double convert = distanceConvert(distance, DistanceTypes.metre, DistanceTypes.AU);
            return MathUtils.simpleDoubleFormat(MathUtils.round(convert, 6)) + (addDimension ? "AU" : "");
        }
        return MathUtils.simpleDoubleFormat(distance) + (addDimension ? "(м)" : "");
    }

    public static double distanceConvert(double distance, DistanceTypes input, DistanceTypes output) {
        double res = 0;
        switch (input) {
            case metre:
                switch (output) {
                    case metre:
                        res = distance;
                        break;
                    case km:
                        res = distance / 1000;
                        break;
                    case AU:
                        res = distance / AstroConst.AU;
                        break;
                    case LY:
                        res = distance / AstroConst.LY;
                        break;
                    case PC:
                        res = distance / AstroConst.PC;
                        break;
                }
                break;
            case km:
                switch (output) {
                    case metre:
                        res = distance * 1000;
                        break;
                    case km:
                        res = distance;
                        break;
                    case AU:
                        res = (distance * 1000) / AstroConst.AU;
                        break;
                    case LY:
                        res = (distance * 1000) / AstroConst.LY;
                        break;
                    case PC:
                        res = (distance * 1000) / AstroConst.PC;
                        break;
                }
                break;
            case AU:
                switch (output) {
                    case metre:
                        res = distance * AstroConst.AU;
                        break;
                    case km:
                        res = (distance * AstroConst.AU) / 1000;
                        break;
                    case AU:
                        res = distance;
                        break;
                    case LY:
                        res = (distance * AstroConst.AU) / AstroConst.LY;
                        break;
                    case PC:
                        res = (distance * AstroConst.AU) / AstroConst.PC;
                        break;
                }
                break;
            case PC:
                switch (output) {
                    case metre:
                        res = distance * AstroConst.PC;
                        break;
                    case km:
                        res = (distance * AstroConst.PC) / 1000;
                        break;
                    case AU:
                        res = (distance * AstroConst.PC) / AstroConst.AU;
                        break;
                    case LY:
                        res = (distance * AstroConst.PC) / AstroConst.LY;
                        break;
                    case PC:
                        res = distance;
                        break;
                }
                break;
        }
        return res;
    }

    /**
     * Вычисление радиуса астеройда по апсолютной магнитюде
     *
     * @param magn
     * @param albedo
     * @return metre
     */
    public static double getRadiusFromAbsoluteMagn(double magn, double albedo) {
        double logr = Math.log10(albedo);
        double step = 0.5 * (6.259 - logr - (0.4 * magn));
        double result = Math.pow(10, step) / 2;
        return MathUtils.round(result * 1000, 0);
    }

}
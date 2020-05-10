package ev3dev.utils;

public class Interpolation {

    /**
     * Method implemented with the ideas from:
     * http://wwwprof.uniandes.edu.co/~gprieto/classes/compufis/interpolacion.pdf
     *
     * @param x parameter
     * @param x0 parameter
     * @param x1 parameter
     * @param y0 parameter
     * @param y1 parameter
     * @return result from interpolation
     */
    public static float interpolate(
            float x,
            float x0,
            float x1,
            float y0,
            float y1) {

        return y0 + ((x - x0) * ((y1 - y0) / (x1 - x0)));
    }
}

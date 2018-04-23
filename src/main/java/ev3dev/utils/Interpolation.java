package ev3dev.utils;

public class Interpolation {

    /**
     * Method implemented with the ideas from:
     * http://wwwprof.uniandes.edu.co/~gprieto/classes/compufis/interpolacion.pdf
     *
     * @param x
     * @param x0
     * @param x1
     * @param y0
     * @param y1
     * @return
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

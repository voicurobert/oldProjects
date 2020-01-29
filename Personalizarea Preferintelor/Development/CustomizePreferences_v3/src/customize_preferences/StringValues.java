/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customize_preferences;

/**
 *
 * @author Ionut
 */
public class StringValues {
    
    public static StringValues instance = new StringValues();
    private final String[] setValoriCriteriu1 = { "Mic", "Mediu", "Mare"};
    private final String[] setValoriCriteriu2 = { "iOS", "Android", "Windows Phone", "BlackBerry OS"};
    private final String[] setValoriCriteriu3 = { "LCD", "IPS", "Amoled" };

    public StringValues() {
    }

    public String[] getSetValoriCriteriu1() {
        return setValoriCriteriu1;
    }

    public String[] getSetValoriCriteriu2() {
        return setValoriCriteriu2;
    }

    public String[] getSetValoriCriteriu3() {
        return setValoriCriteriu3;
    }
    
    
    
}

package tutorials.hackro.com.calculadoraexamplemvp.Interactors;

import tutorials.hackro.com.calculadoraexamplemvp.Interfaces.MainInteractor;

/**
 * Created by david on 21/07/16.
 */
public class MainInteractorImpl implements MainInteractor {
    @Override
    public double processOperation(String number1, String number2, int operation) {
            switch (operation){
                case 1: return Double.valueOf(number1) + Double.valueOf(number2);
                case 2: return Double.valueOf(number1) - Double.valueOf(number2);
                case 3: return Double.valueOf(number1) * Double.valueOf(number2);
                case 4: return Double.valueOf(number1) / Double.valueOf(number2);
                default:return 0;
            }
    }
}

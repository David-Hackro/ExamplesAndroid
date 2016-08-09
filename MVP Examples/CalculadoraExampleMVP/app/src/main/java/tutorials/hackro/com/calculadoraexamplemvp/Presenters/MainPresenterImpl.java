package tutorials.hackro.com.calculadoraexamplemvp.Presenters;

import tutorials.hackro.com.calculadoraexamplemvp.Interactors.MainInteractorImpl;
import tutorials.hackro.com.calculadoraexamplemvp.Interfaces.MainInteractor;
import tutorials.hackro.com.calculadoraexamplemvp.Interfaces.MainPresenter;
import tutorials.hackro.com.calculadoraexamplemvp.Interfaces.MainView;
import tutorials.hackro.com.calculadoraexamplemvp.Interfaces.OnOperationsFinished;

/**
 * Created by david on 21/07/16.
 */
public class MainPresenterImpl implements MainPresenter, OnOperationsFinished {

    //View
    MainView mainView;//interface
    //Interactor
    MainInteractor mainInteractor;//interface

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        //class
        mainInteractor = new MainInteractorImpl();
    }

    @Override
    public void calcular(String number1, String number2, int operation) {
        if (mainView != null) {

            try {
                if (!number1.equals("") && !number2.equals("")) {
                    double tempResult = mainInteractor.processOperation(number1, number2, operation);

                    mainView.showResult(String.valueOf(tempResult));

                } else if (number1.equals("") && number2.equals("")) {
                    mainView.setErrorFirstNumber();
                    mainView.setErrorSecondNumber();
                } else if (number1.equals("")) {
                    mainView.setErrorFirstNumber();
                } else if (number2.equals("")) {
                    mainView.setErrorSecondNumber();
                }
            } catch (Exception e) {
                mainView.showError();
            }

        }
    }

    @Override
    public void setErrorFirstNumber() {
        if (mainView != null) {
            mainView.setErrorFirstNumber();
        }
    }

    @Override
    public void setErrorSecondNumber() {
        if (mainView != null) {
            mainView.setErrorSecondNumber();
        }
    }
}

package tutorials.hackro.com.registermvp.Interactors;

import android.os.Handler;
import tutorials.hackro.com.registermvp.Interfaces.OnRegisterFinishedListener;
import tutorials.hackro.com.registermvp.Interfaces.RegisterInteractor;
import tutorials.hackro.com.registermvp.Utils.UtilsImpl;

/**
 * Created by david on 22/07/16.
 */
              //class                           //interface
public class RegisterInteractorImpl implements RegisterInteractor {

    private UtilsImpl utils;

    public RegisterInteractorImpl() {
        utils = new UtilsImpl();
    }

    @Override
    public void register(final String username,
                         final String email,
                         final String password,
                         final String repeatPassoword,
                         final OnRegisterFinishedListener listener) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Logic :D

                if(!username.equals("") && !email.equals("") &&!password.equals("") &&!repeatPassoword.equals("") ){

                    if(utils.validateEmail(email) && utils.validatePassword(password)){

                        if(password.equals(repeatPassoword)){
                            listener.onSucess();
                        }
                        else{
                            listener.setErrorRepeatPassword();
                        }
                    }
                    else if(!utils.validateEmail(email)){
                        listener.setErrorEmail();
                    }
                    else{
                        listener.setErrorEmail();
                        listener.setErrorPassword();
                    }


                }

                if(username.equals("")){
                    listener.setErrorUsername();
                }
                if(email.equals("")){
                    listener.setErrorEmail();
                }
                if(password.equals("")){
                    listener.setErrorPassword();
                }
                if(repeatPassoword.equals("")){
                    listener.setErrorRepeatPassword();
                }



            }
        },2000);

    }
}

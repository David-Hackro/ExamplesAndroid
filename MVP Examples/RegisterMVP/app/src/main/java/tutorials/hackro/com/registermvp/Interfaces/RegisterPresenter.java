package tutorials.hackro.com.registermvp.Interfaces;

/**
 * Created by david on 22/07/16.
 */
public interface RegisterPresenter {

    void validateRegister(String username,String email,String password,String repeatPassoword);
    void onDestroy();

}

package tutorials.hackro.com.registermvp.Interfaces;

/**
 * Created by david on 22/07/16.
 */
public interface RegisterInteractor {

    void register(String username,String email,String password,String repeatPassoword,OnRegisterFinishedListener listener);

}

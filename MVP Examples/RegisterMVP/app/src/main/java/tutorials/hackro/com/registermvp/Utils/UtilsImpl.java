package tutorials.hackro.com.registermvp.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tutorials.hackro.com.registermvp.Interfaces.Utils;

/**
 * Created by david on 22/07/16.
 */
public class UtilsImpl implements Utils {

    //validate email correct example@example.com
    private final Pattern regexEmail =   Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    @Override
    public boolean validateEmail(String email) {
        Matcher matcher = regexEmail.matcher(email);

        return matcher.find();
    }

    @Override
    public boolean validatePassword(String password) {
        if(password.length() > 6)
                return true;

            return false;
    }


}

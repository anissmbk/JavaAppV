package provider;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public interface Environment {

    //nom que vous avez donnee à la base de donnée
    public final String environement = "jdbc:postgresql://localhost:5432/JavaApp";

    public final String userPath = "public.user" ;
    public final String invitedPath = "public.invited" ;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public final String TIME_FORMAT = "yyyy--dd";

    public final String ACCEPTED_INV = "accepted" ;
    public final String REJECTED_INV = "rejected" ;
    public final String LOADING_INV = "loading" ;

    public final String USERSIDINV = "fromuser" ;
    public final String USERIDINV = "touser";
}

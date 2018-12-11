package Entity;

import com.google.gson.Gson;
import common.AuthorizationResponseBody;
import common.PayloadBody;
import org.json.simple.JSONObject;
import provider.ApiCrud;
import provider.Environment;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static common.Constants.*;

public class User {
    protected int id;
    protected String username;
    protected String password;
    protected String email;
    protected String nom;
    protected boolean sexe;
    protected String photo;
    protected Date birth;

    public User(String username, String password, String email , String sexe) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.sexe = Boolean.parseBoolean(sexe);
        System.out.println("nouvelle utilisateur");
    }

    public User(String username, String password) throws SQLException, ParseException {
        this.username=username;
        this.password=password;
    }

    public User(){
        System.out.println("verification utilisateur");
    }

    public User(int id, String username, String password,
                String email,String nom,boolean sexe,String photo
                ,Date birth) throws ParseException {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nom = nom;
        this.sexe = sexe;
        this.photo = photo;
        this.birth = birth;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getNom() {
        return nom;
    }

    public boolean isSexe() {
        return sexe;
    }

    public String getPhoto() {
        return photo;
    }

    public Date getBirth() {
        return birth;
    }

    public String[] Verification_data_user(String username, String password) throws SQLException, ParseException {
        ApiCrud crud = new ApiCrud();
        String[] data = crud.get_data_user(username,password);
        return data;
    }

    public static void main(String[] args) throws SQLException, ParseException {
        User user = new User("ouail","ouaillsq");
        System.out.println(User.get_Data_User("ouail",null).getId());
    }

    public static User get_Data_User(String username, String password) throws SQLException, ParseException {
        ApiCrud crud = new ApiCrud();
        String[] data = crud.get_data_user(username,password);
        if(data[7]!=null)
            return new User(Integer.parseInt(data[0]),data[1],data[2],data[3],data[4],
                Boolean.parseBoolean(data[5]),data[6], new SimpleDateFormat(Environment.TIME_FORMAT).parse(data[7]));
        else{
            return new User(Integer.parseInt(data[0]),data[1],data[2],data[3],data[4],
                    Boolean.parseBoolean(data[5]),data[6],null);
        }
    }

    public boolean Add_data_user() throws SQLException, ParseException {
        ApiCrud crud = new ApiCrud();
        return crud.add_data_user(this.username,this.password,this.email,this.sexe);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setSexe(boolean sexe) {
        this.sexe = sexe;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

}

package Entity;

import provider.ApiCrud;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

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

    public boolean Verification_data_user(String username,String password) throws SQLException, ParseException {
        ApiCrud crud = new ApiCrud();
        return crud.get_data_user(username,password) != null;
    }

    public boolean Add_data_user() throws SQLException, ParseException {
        ApiCrud crud = new ApiCrud();
        return crud.add_data_user(this.username,this.password,this.email,this.sexe);
    }

    public static void main(String[] args) throws SQLException, ParseException {
        User user = new User("ouail","ouaillsq");
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

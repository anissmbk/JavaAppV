package provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApiCrud {

    public ApiCrud(){
        System.out.println( "connection base de donnée" );
    }

    public String[] get_data_user(String username, String password) throws SQLException {

        DB_connection db = new DB_connection();
        Connection con = db.get_Connection();
        String[] data = new String[8];

        try{

            String query = password == null ? "select * from " + Environment.userPath + " where username='" + username + "'" : "select * from " + Environment.userPath + " where username='" + username + "' AND password='" + password + "'";
            ResultSet rs = con.createStatement().executeQuery(query);

            if(rs.next()){
                    data[0] = rs.getString("id");
                    data[1] = rs.getString("username");
                    data[2] = rs.getString("password");
                    data[3] = rs.getString("email");
                    data[4] = rs.getString("nom");
                    data[5] = rs.getString("sexe");
                    data[6] = rs.getString("photo");
                    data[7] = rs.getString("birth");

                    rs.close();
                    db.close_Connection();
                    con.close();

                return data;
            }
            else{
                rs.close();
                return null;
            }

        }catch (Exception e){
                System.out.println(e);
        }finally {
            System.out.println("close connection");
            db.close_Connection();
            con.close();
        }

        return null;
    }

    public boolean add_data_user(String username, String password , String email , boolean sexe) throws SQLException {

        DB_connection db = new DB_connection();
        Connection con = db.get_Connection();
        try {

            String query = "INSERT INTO " + Environment.userPath + " (username,password,email,sexe) VALUES ('" + username + "', '" + password + "', '" + email + "', '" + sexe +"')";

            PreparedStatement post = con.prepareStatement(query);
            post.executeUpdate();
            post.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            System.out.println("close connection");
            db.close_Connection();
            con.close();
        }
        return false;
    }

    public int[] get_Inv_userID(int id){
        DB_connection db = new DB_connection();
        Connection con = db.get_Connection();
        int[] data = new int[200];
        String query = "select * from " + Environment.invitedPath + " where" + Environment.USERIDINV + "='" + id + "' AND status='" + Environment.LOADING_INV + "'";
        try {
            ResultSet rs = con.createStatement().executeQuery(query);
            int i=0;
            while(rs.next()){
                data[i]= Integer.parseInt(rs.getString(Environment.USERSIDINV));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

        public static void main(String[] args) throws SQLException {
            ApiCrud crud = new ApiCrud();
            System.out.println(crud.get_Inv_userID(1)[0]);
        }

}

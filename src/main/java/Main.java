import jdk.nashorn.internal.runtime.UserAccessorProperty;
import org.sqlite.SQLiteConfig;

import java.sql.*;

public class Main {

    public static Connection db = null;

    public static void main(String[] args) {
        ListUsers();
        openDatabase("MainDatabase.db");
        try{
            PreparedStatement ps = db.prepareStatement("INSERT INTO Userdetails(UserID, Surname, Company) VALUES(?,?,?)");
            ps.setString(1, UserID);
            //continue copying from:
            // https://docs.google.com/presentation/d/1EQgc30X0YsDHEOxOEdwa192FPpnHVtQFUEBgv7sIiyY/edit#slide=id.g5972025f29_0_105
        }
        catch(Exception ex){
            System.out.println("Database Error: " + ex.getMessage());

        }
    }

    private static void ListUsers(){
        openDatabase("MainDatabase.db");
            try {
                PreparedStatement ps = db.prepareStatement("SELECT UserID, Surname, Company FROM Userdetails");
                ResultSet results = ps.executeQuery();
                while (results.next()) {
                    int UserID = results.getInt(1);
                    String Company = results.getString(3);
                    String Surname = results.getString(2);
                    System.out.println(UserID + " " + Surname + " " + Company);
                }

            } catch (Exception exception) {
                System.out.println("database error" + exception.getMessage());
            }
        closeDatabase();
    }



    private static void openDatabase(String dbFile) {
        try  {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
            System.out.println("Database connection successfully established.");
        } catch (Exception exception) {
            System.out.println("Database connection error: " + exception.getMessage());
        }

    }

    private static void closeDatabase(){
        try {
            db.close();
            System.out.println("Disconnected from database.");
        } catch (Exception exception) {
            System.out.println("Database disconnection error: " + exception.getMessage());
        }
    }

}


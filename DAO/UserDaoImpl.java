package DAO;

 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

 

import DBconnection.DBconnection;
import pojo.User;

 

public class UserDaoImpl implements UserDao {
    private Connection con;
    String id;
    String pass;
    private static UserDaoImpl userDao;

 

    private UserDaoImpl() {
        con = DBconnection.getConnection();
        id = "karthik2001chy@gmail.com";
        pass = "karthik";
    }
    public static UserDaoImpl getUserDaoInstance() { // Using Singleton pattern
        if (userDao == null) {
            synchronized (UserDaoImpl.class) {
                if (userDao == null) {
                    userDao = new UserDaoImpl();
                }
            }
        }
        return userDao;
    }

 

    @Override
    public int isUserAvilable() { // to check user database empty or not
        try {
            String q = "select count(customer_Id) from customers";
            PreparedStatement p = con.prepareStatement(q);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                int count = rs.getInt(1);
                return count;
            }

 

        } catch (SQLException e) {
            System.out.println(e.getMessage() + " \n");
        }
        return 0;
    }

 

    @Override
    public boolean adminLogin(String emailId, String password) { // admin login

 

        if (id.equals(emailId) && pass.equals(password)) {
            return true;
        } else
            return false;
    }

 

    @Override
    public boolean customerRegister(User t) { // to register function for users
        try {
            String q = "insert into customers(customer_Name,email_Id,cus_Password,supercoins) values(?,?,?,?) ";
            PreparedStatement p;
            p = con.prepareStatement(q);
            p.setString(1, t.getUserName());
            p.setString(2, t.getEmailId());
            p.setString(3, t.getPassword());
            p.setInt(4, 100);

 

            int n = p.executeUpdate();
            if (n > 0)
                return true;
            else
                return false;
        } catch (SQLException e) {
            System.out.println("can't add users!please contact admin " + e.getMessage());
            return false;
        }

 

    }

 

    @Override
    public User customerLogin(String email, String password) { // to login function for users
        try {
            String q = "select * from customers where email_Id= ? and cus_Password =?";
            PreparedStatement p = con.prepareStatement(q);
            p.setString(1, email);
            p.setString(2, password);

 

            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                String userName = rs.getString(2);
                String emailId = rs.getString(3);
                String pass = rs.getString(4);
                int superCoins = rs.getInt(5);
                User u = new User(userName, emailId, pass, superCoins, null);
                return u;
            }

 

        } catch (SQLException e) {
            System.out.println(e.getMessage() + " \n");
            return null;
        }

 

        return null;
    }

 

    @Override
    public boolean isRegistered(String emailId) { // to check whether customer is already registered or not
        try {
            String q = "select * from customers where email_Id= ?";
            PreparedStatement p = con.prepareStatement(q);
            p.setString(1, emailId);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                return true;
            }

 

        } catch (SQLException e) {
            System.out.println("Database issue :" + e.getMessage());
        }
        return false;
    }

 

}
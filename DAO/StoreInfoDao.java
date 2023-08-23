package DAO;

 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

 

import DBconnection.DBconnection;
import pojo.Item;
import pojo.Product;

 

public class StoreInfoDaoImpl implements StoreInfoDao {
    List<Item> products;
    private Connection con;
    private static StoreInfoDaoImpl storeInfo;

 

    private StoreInfoDaoImpl() {
        con = DBconnection.getConnection();
    }

 

    public static StoreInfoDaoImpl getStoreInfoInstance() {
        if (storeInfo == null) {
            synchronized (StoreInfoDaoImpl.class) {
                if (storeInfo == null) {
                    storeInfo = new StoreInfoDaoImpl();
                }
            }
        }
        return storeInfo;
    }

 

    @Override
    public boolean isProductExist(String productName) { // to check whether the product name already exist or not
        try {

 

            String q = "select * from products where product_Name=?";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, productName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

 

    @Override
    public boolean addProduct(Item p) { // to Add product
        try {
            String q = "insert into products(product_Name,category,quantity,buying_Price,item_Name) values(?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, p.getProductName());
            ps.setString(2, p.getCategory());
            ps.setInt(3, p.getAvailableQuantity());
            ps.setFloat(4, p.getBuyingPrice());
            ps.setString(5, p.getItemName());
            int n = ps.executeUpdate();
            if (n > 0)
                return true;
            else
                return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

 

    @Override
    public Product searchProduct(int productId) { // to search the product
        try {
            String q = "select * from products where product_Id=?";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productid = rs.getInt(1);
                String productName = rs.getString(2);
                String category = rs.getString(3);
                int availableQuantity = rs.getInt(4);
                float buyingPrice = rs.getFloat(5);
                float sellingPrice = rs.getFloat(6);
                String itemName = rs.getString(7);
                Product p = new Item(productid, productName, sellingPrice, availableQuantity, buyingPrice, itemName,
                        category);
                return p;

 

            }
            return null;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

 

    @Override
    public int searchCategotyDetails(String category) { // to search the products by category
        int temp = 0;
        try {
            products = new ArrayList<Item>();
            String q = "select * from products where category=?";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productid = rs.getInt(1);
                String productName = rs.getString(2);
                String category1 = rs.getString(3);
                int availableQuantity = rs.getInt(4);
                float buyingPrice = rs.getFloat(5);
                float sellingPrice = rs.getFloat(6);
                String itemName = rs.getString(7);

 

                Product p = new Item(productid, productName, sellingPrice, availableQuantity, buyingPrice, itemName,
                        category1);

 

                if (temp == 0) {
                    System.out.println(
                            "-----------------------------------------------------------------------------------------");
                    System.out.printf("%10s %10s %10s %10s %10s %10s %10s%n", "productid", "productName1",
                            "availableQuantity", "category", "itemName", "buyingPrice", "sellingPrice");
                    System.out.println(
                            "-----------------------------------------------------------------------------------------");
                }
                temp = 1;
                System.out.printf("%10s %12s %17s %10s %10s %11s %12s%n", productid, productName, availableQuantity,
                        category1, itemName, buyingPrice, sellingPrice);
            }
            return temp;

 

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return temp;
        }

 

    }

 

    @Override
    public int displayProductDetails(String productName) { // to display ProductDetails by product name
        int temp = 0;
        try {
            products = new ArrayList<Item>();
            String q = "select * from products where product_Name=?";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, productName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productid = rs.getInt(1);
                String productName1 = rs.getString(2);
                String category = rs.getString(3);
                int availableQuantity = rs.getInt(4);
                float buyingPrice = rs.getFloat(5);
                float sellingPrice = rs.getFloat(6);
                String itemName = rs.getString(7);
                if (temp == 0) {
                    System.out.println(
                            "-----------------------------------------------------------------------------------------");
                    System.out.printf("%10s %10s %10s %10s %10s %10s %10s%n", "productid", "productName1",
                            "availableQuantity", "category", "itemName", "buyingPrice", "sellingPrice");
                    System.out.println(
                            "-----------------------------------------------------------------------------------------");
                }
                temp = 1;
                System.out.printf("%10s %12s %17s %10s %10s %11s %12s%n", productid, productName1, availableQuantity,
                        category, itemName, buyingPrice, sellingPrice);

 

                System.out.println(
                        "-----------------------------------------------------------------------------------------");

 

            }

 

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return temp;
    }

 

    @Override
    public void amountSpent() { // to check Amount spent on the products
        int temp = 0;
        try {
            String q1 = "select product_Name ,buying_Price*quantity AmountSpent from products"; // to return the price
                                                                                                // in product wise
            String q2 = "select sum(buying_Price*quantity) as Total_Amount from products"; // to return the total amount
                                                                                            // spent on the product
            PreparedStatement ps1 = con.prepareStatement(q1);
            PreparedStatement ps2 = con.prepareStatement(q2);

 

            ResultSet rs1 = ps1.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            while (rs1.next()) {
                if (temp == 0) {
                    System.out.println("--------------------------------------------------------");
                    System.out.println("Product Name   " + "      Amount Spent");
                    System.out.println("--------------------------------------------------------");
                    temp = 1;
                }
                System.out.println(rs1.getString(1) + "                " + rs1.getFloat(2));
                System.out.println("--------------------------------------------------------");
            }
            while (rs2.next()) {
                System.out.println("Total Amount spent     " + rs2.getString(1));
                System.out.println("--------------------------------------------------------");
            }

 

        } catch (SQLException e) {
            System.out.println(e);
        }

 

    }

 

    @Override
    public float categoryProfit(String category) { // to Display the profit by Category
        try {
            String q = "select category,sum((selling_Price-buying_Price)*quantity) as Total_profit from products where category=? group by category;";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, category);

 

            ResultSet rs = ps.executeQuery();

 

            while (rs.next()) {
                Float TotalProfit = rs.getFloat(2);
                return TotalProfit;
            }

 

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

 

    @Override
    public int displayAllProduct() { // to Display the product details
        int temp = 0;
        try {
            products = new ArrayList<Item>();
            String q = "select * from products";
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productid = rs.getInt(1);
                String productName1 = rs.getString(2);
                String category = rs.getString(3);
                int availableQuantity = rs.getInt(4);
                float buyingPrice = rs.getFloat(5);
                float sellingPrice = rs.getFloat(6);
                String itemName = rs.getString(7);

 

                if (temp == 0) {
                    System.out.println(
                            "-----------------------------------------------------------------------------------------");
                    System.out.printf("%10s %10s %10s %10s %10s %10s %10s%n", "productid", "productName1",
                            "availableQuantity", "category", "itemName", "buyingPrice", "sellingPrice");
                    System.out.println(
                            "-----------------------------------------------------------------------------------------");
                }
                temp = 1;
                System.out.printf("%10s %12s %17s %10s %10s %11s %12s%n", productid, productName1, availableQuantity,
                        category, itemName, buyingPrice, sellingPrice);

 

                System.out.println(
                        "-----------------------------------------------------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return temp;

 

    }

 

    @Override
    public int viewProducts() { // to view the products
        int temp = 0;
        try {
            String q = "select product_Id,product_Name,category,quantity,selling_Price from products";
            PreparedStatement ps = con.prepareStatement(q);

 

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt(1);
                String ProductName = rs.getString(2);
                String category = rs.getString(3);
                int quantity = rs.getInt(4);
                float sellingPrice = rs.getFloat(5);
                if (temp == 0) {
                    System.out.println(
                            "-----------------------------------------------------------------------------------------");
                    System.out.printf("%10s %10s %10s %10s %10s%n", "ProductId", "ProductName", "Category", "Quantity",
                            "Price");
                    System.out.println(
                            "-----------------------------------------------------------------------------------------");
                }
                temp = 1;
                System.out.printf("%10s %10s %10s %10s %10s%n", productId, ProductName, category, quantity,
                        sellingPrice);

 

                System.out.println(
                        "-----------------------------------------------------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Database issues : " + e.getMessage());
        }
        return temp;
    }

 

    @Override
    public int displayProduct(String category) { // to display the products on categorywise
        int temp = 0;
        try {
            String q = "select product_Id,product_Name,category,quantity,selling_Price  from products where category=?";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt(1);
                String ProductName = rs.getString(2);
                String category1 = rs.getString(3);
                int quantity = rs.getInt(4);
                float sellingPrice = rs.getFloat(5);
                if (temp == 0) {
                    System.out.println(
                            "-----------------------------------------------------------------------------------------");
                    System.out.printf("%10s %10s %10s %10s %10s%n", "ProductId", "ProductName", "Category", "Quantity",
                            "Price");
                    System.out.println(
                            "-----------------------------------------------------------------------------------------");
                }
                temp = 1;
                System.out.printf("%10s %10s %10s %10s %10s%n", productId, ProductName, category1, quantity,
                        sellingPrice);

 

                System.out.println(
                        "-----------------------------------------------------------------------------------------\n");

 

            }
        } catch (

 

        SQLException e) {
            System.out.println("Database issues : " + e.getMessage());
        }
        return temp;
    }

 

    @Override
    public int sortbyPrice() { // display the products with price low to high
        int temp = 0;
        try {
            String q = "select product_Id,product_Name,category,quantity,selling_Price from products order by selling_Price";
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt(1);
                String ProductName = rs.getString(2);
                String category = rs.getString(3);
                int quantity = rs.getInt(4);
                float sellingPrice = rs.getFloat(5);
                if (temp == 0) {
                    System.out.println(
                            "-----------------------------------------------------------------------------------------");
                    System.out.printf("%10s %10s %10s %10s %10s%n", "ProductId", "ProductName", "Category", "Quantity",
                            "Price");
                    System.out.println(
                            "-----------------------------------------------------------------------------------------");
                }
                temp = 1;
                System.out.printf("%10s %10s %10s %10s %10s%n", productId, ProductName, category, quantity,
                        sellingPrice);

 

                System.out.println(
                        "-----------------------------------------------------------------------------------------");

 

            }
        } catch (SQLException e) {
            System.out.println("Database issues : " + e.getMessage());
        }
        return temp;
    }

 

    @Override
    public Item displayByProductId(int productId) { // display the product by product id
        try {
            String q = "select * from products where product_Id=?";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productid = rs.getInt(1);
                String productName = rs.getString(2);
                String category = rs.getString(3);
                int availableQuantity = rs.getInt(4);
                float buyingPrice = rs.getFloat(5);
                float sellingPrice = rs.getFloat(6);
                String itemName = rs.getString(7);

 

                System.out.println(
                        "-----------------------------------------------------------------------------------------");
                System.out.printf("%10s %10s %10s %10s %10s%n", "ProductId", "ProductName", "Category", "Quantity",
                        "Price");
                System.out.println(
                        "-----------------------------------------------------------------------------------------");

 

                Item i = new Item(productid, productName, sellingPrice, availableQuantity, buyingPrice, itemName,
                        category);
                System.out.printf("%10s %10s %10s %10s %10s%n", productId, productName, category, availableQuantity,
                        sellingPrice);

 

                System.out.println(
                        "-----------------------------------------------------------------------------------------");

 

                return i;
            }
            return null;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }

 

    }

 

    @Override
    public int quantityDecreaser(int productId, int currentQuantity) { // to decrease the quantity after customer bought
                                                                        // the product
        try {
            String q = "update products set quantity =? where product_Id= ?";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setInt(1, currentQuantity);
            ps.setInt(2, productId);
            int n = ps.executeUpdate();
            if (n > 0)
                return 1;
        } catch (SQLException e) {
            System.out.println("Database Issue : " + e.getMessage());
        }
        return 0;
    }

 

    @Override
    public Item displayByProductName(String productName) { // display the product by product name
        int temp = 0;
        try {
            String q = "select * from products where product_Name=?";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, productName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productid = rs.getInt(1);
                String productName1 = rs.getString(2);
                String category = rs.getString(3);
                int availableQuantity = rs.getInt(4);
                float buyingPrice = rs.getFloat(5);
                float sellingPrice = rs.getFloat(6);
                String itemName = rs.getString(7);
                Item i = new Item(productid, productName, sellingPrice, availableQuantity, buyingPrice, itemName,
                        category);
                if (temp == 0) {
                    System.out.println(
                            "-----------------------------------------------------------------------------------------");
                    System.out.printf("%10s %10s %10s %10s %10s %10s %10s%n", "productid", "productName1",
                            "availableQuantity", "category", "itemName", "buyingPrice", "sellingPrice");
                    System.out.println(
                            "-----------------------------------------------------------------------------------------");
                }
                temp = 1;
                System.out.printf("%10s %12s %17s %10s %10s %11s %12s%n", productid, productName1, availableQuantity,
                        category, itemName, buyingPrice, sellingPrice);

 

                System.out.println(
                        "-----------------------------------------------------------------------------------------");
                return i;
            }

 

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

 

    @Override
    public int quantityDecreaser(String productName, int currentQuantity) { // to decrease the quantity after customer
                                                                            // bought the product
        try {
            String q = "update products set quantity =? where product_Name= ?";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setInt(1, currentQuantity);
            ps.setString(2, productName);
            int n = ps.executeUpdate();
            if (n > 0)
                return 1;
        } catch (SQLException e) {
            System.out.println("Database Issue : " + e.getMessage());
        }
        return 0;
    }

 

    @Override
    public int superCoinDecreaser(String emailId) { // to decrease the super coin count after customer uses the discount
        try {
            String q = "update customers set superCoins = ? where email_Id= ?";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setInt(1, 0);
            ps.setString(2, emailId);
            int n = ps.executeUpdate();
            if (n > 0)
                return 1;
        } catch (SQLException e) {
            System.out.println("Database Issue : " + e.getMessage());
        }
        return 0;

 

    }

 

    @Override
    public int getSuperCoins(String emailId) { // to check the whether the customer have super coins or not
        try {
            String q = "select supercoins from customers where email_Id= ?";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, emailId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Database Issue : " + e.getMessage());
        }
        return 0;
    }

 

}
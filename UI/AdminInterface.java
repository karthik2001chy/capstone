package UI;

 

import java.util.InputMismatchException;
import java.util.Scanner;

 

import DAO.StoreInfoDaoImpl;
import exception.MyException;
import pojo.Item;
import pojo.Product;

 

public class AdminInterface implements Runnable {
    int choice = 1;
    Scanner sc = new Scanner(System.in);
    int productId, availableQuantity;
    String productName, category, itemName;
    float sellingPrice, buyingPrice;
    Product p;
    Item I;
    StoreInfoDaoImpl storeInfo = StoreInfoDaoImpl.getStoreInfoInstance();

 

    public AdminInterface() {

 

    }

 

    @Override
    public void run() {
        do {
            System.out.println(); // to print new line
            System.out.println("Press 1 to add the Products!");
            System.out.println("Press 2 to Search the Products!");
            System.out.println("Press 3 to Display the Products by Category!");
            System.out.println("Press 4 to view the Products Details!");
            System.out.println("Press 5 to get the Total Amount spent on the Products!");
            System.out.println("Press 6 to see the Profit Amount");
            System.out.println("Press 7 to display all the products");
            System.out.println("Press 0 to the LogOut");
            try { // to throw exception if user enter any value other than integer
                System.out.println("Enter the Choice \n");
                choice = sc.nextInt();

 

                switch (choice) {

 

                // to list the product
                case 1:
                    System.out.println("Enter product Name");
                    productName = sc.next();
                    if (storeInfo.isProductExist(productName)) { // product already exist or not
                        System.out.println("Product Name already exist!");
                        break;
                    }
                    System.out.println("Enter Item Name");
                    itemName = sc.next();
                    System.out.println("Enter category");
                    category = sc.next();
                    System.out.println("Enter Quantity");
                    availableQuantity = sc.nextInt();
                    try {
                        if (availableQuantity < 0) {
                            throw new MyException("Quantity cannot be minus value!\n"); // userdefined exception for
                                                                                        // negative value
                        }
                    } catch (MyException e) {
                        System.out.println("Exception " + e.getMessage());
                        break;
                    }
                    System.out.println("Enter Buying price");
                    buyingPrice = sc.nextFloat();
                    try {
                        if (buyingPrice < 0) {
                            throw new MyException("Price cannot be minus value!\n"); // userdefined exception for
                                                                                        // negative value
                        }
                    } catch (MyException e) {
                        System.out.println("Exception " + e.getMessage());
                        break;
                    }
                    p = new Item(productName, availableQuantity, itemName, buyingPrice, category);
                    if (storeInfo.addProduct((Item) p)) {
                        System.out.println("Product added sucessfully!\n");

                    } else
                        System.out.println("Can't add the product.please contact developer");

 

                    break;

 

                // to search the product
                case 2:
                    System.out.println("Enter product id to search the products");
                    productId = sc.nextInt();
                    try {
                        if (productId < 0) {
                            throw new MyException("Product Id cannot be minus value!\n"); // userdefined exception for
                                                                                            // negative value
                        }
                    } catch (MyException e) {
                        System.out.println("Exception " + e.getMessage());
                        break;
                    }
                    p = storeInfo.searchProduct(productId); // Funtion to Search the product by Product ID
                    if (p == null) {
                        System.out.println(" No such product available in the List!");
                    } else
                        System.out.println("\n" + p.getProductName() + " available in the Product List!\n");

 

                    break;

 

                // to Display the Products by Category
                case 3:
                    System.out.println("Enter Category to List the products");
                    category = sc.next();
                    int temp = storeInfo.searchCategotyDetails(category);
                    if (temp == 0) {
                        System.out.println("No such Category available to List the products!");
                    }
                    break;

 

                // to view the Products Details
                case 4:
                    int temp1 = 0;
                    System.out.println("Enter product name to search the products");
                    productName = sc.next();
                    temp1 = storeInfo.displayProductDetails(productName); // Funtion to Display the product Details
                    if (temp1 == 0) {
                        System.out.println("no Such product are available");
                    }
                    break;

 

                // to check Amount spent on the products
                case 5: {
                    storeInfo.amountSpent(); // Funtion to calculate the Amount spent on the products
                }
                    break;

 

                // to Display the profit by Category
                case 6: {
                    System.out.println("Enter Category to Display the Profit");
                    category = sc.next();
                    float profit = storeInfo.categoryProfit(category); // Funtion to Display the profit by Category
                                                                        // wise
                    if (profit == 0) {
                        System.out.println("No Such Category are available to Calculate the Profit\n");
                    } else {
                        System.out.println("Profit Amount for " + category + " category is " + profit);
                    }
                }
                    break;

 

                // to Display the product details
                case 7: {
                    int n = 0;
                    n = storeInfo.displayAllProduct(); // Display the product details
                    if (n == 0) {
                        System.out.println("No products available to display! \n");
                    }
                }
                    break;

 

                case 0:
                    System.out.println("Logged out sucessfully!\n");

                    break;
                default:
                    System.out.println("Enter valid choice!");
                    break;
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Please, Enter Numbers Only\n");
            }

 

        } while (choice != 0);

 

    }
}
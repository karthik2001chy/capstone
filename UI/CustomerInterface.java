[10:16 AM] Karthik M

package UI;

 

import java.util.ArrayList;

import java.util.InputMismatchException;

import java.util.List;

import java.util.Scanner;

 

import DAO.StoreInfoDaoImpl;

import exception.MyException;

import pojo.Item;

import pojo.User;

 

public class CustomerInterface implements Runnable {

    Scanner sc = new Scanner(System.in);

    int choice = 1, ch = 1;

    String userName;

    User u;

    List<Item> productList = new ArrayList<>();

    float total = 0;

    StoreInfoDaoImpl storeInfo = StoreInfoDaoImpl.getStoreInfoInstance();

 

    public CustomerInterface(User u) {

        this.u = u;

    }

 

    public void run() {

        int n = storeInfo.getSuperCoins(u.getEmailId());

        System.out.println("Welcome " + u.getUserName() + "! you have " + n + " super coins to Spend");

        do {

            System.out.println("\npress 1 to view the products");

            System.out.println("press 2 to display the products with price low to high");

            System.out.println("press 3 to display the products on categorywise");

            System.out.println("press 4 to Buy a product");

            System.out.println("press 0 to Log Out\n");

 

            try { // to throw exception if user enter any value other than integer

                System.out.println("Enter a choice");

                choice = sc.nextInt();

                switch (choice) {

 

                // to view the products

                case 1:

                    n = 0;

                    n = storeInfo.viewProducts();

                    if (n == 0) {

                        System.out.println("No products available to display");

                    }

                    break;

 

                // display the products with price low to high

                case 2:

                    n = 0;

                    n = storeInfo.sortbyPrice();

                    if (n == 0) {

                        System.out.println("No products available to display");

                    }

                    break;

 

                // to display the products on categorywise

                case 3:

                    n = 0;

                    System.out.println("Enter category to display the products\n");

                    String category = sc.next();

                    n = storeInfo.displayProduct(category);

                    if (n == 0) {

                        System.out.println("No products available to display");

                    }

                    break;

                // to buy the product

                case 4:

                    do {

                        System.out.println("press 1 to buy a product by product Id");

                        System.out.println("press 2 to buy a product by product Name");

                        System.out.println("press 0 after you purchased");

                        System.out.println("Enter a choice");

                        ch = sc.nextInt();

                        switch (ch) {

                        case 1:

                            Item i = null;

                            System.out.println("Enter product Id"); // to buy the product using product Id

                            int productId = sc.nextInt();

                            try {

                                if (productId < 0) {

                                    throw new MyException("Exception : please,Enter postive number! \n"); // userdefined

                                                                                                            // exception

                                                                                                            // for

                                                                                                            // negative

                                                                                                            // value

                                }

                            } catch (MyException e) {

                                sc.nextLine();

                                System.out.println(e.getMessage());

                                break;

                            }

                            i = storeInfo.displayByProductId(productId); // to display the product details

                            if (i == null) {

                                System.out.println("No products are available with this product Id\n");

                                break;

                            } else {

                                if (i.getAvailableQuantity() == 0) {

                                    System.out.println("No stock available for " + i.getProductName() + "\n");

                                    break;

                                }

                                System.out.println("Enter a Quantity");

                                int quantity = sc.nextInt();

                                try {

                                    if (quantity < 0) {

                                        throw new MyException("Exception : please,Enter postive number! \n"); // userdefined

                                                                                                                // exception

                                                                                                                // for

                                                                                                                // negative

                                                                                                                // value

                                    }

                                } catch (MyException e) {

                                    sc.nextLine();

                                    System.out.println(e.getMessage());

                                    break;

                                }

                                if (i.getAvailableQuantity() >= quantity) {

                                    int currentQuantity = i.getAvailableQuantity() - quantity;

                                    i.setAvailableQuantity(currentQuantity);

                                    Item temp = new Item(productId, i.getProductName(), i.getSellingPrice(), quantity,

                                            i.getCategory());

                                    productList.add(temp); // to add the ordered item in list

                                    System.out.println("Product added to your cart!");

                                    storeInfo.quantityDecreaser(productId, currentQuantity); // update the quantity in

                                                                                                // database

                                } else {

                                    System.out.println("Quantity number out of range!\n");

                                }

                            }

                            break;

                        case 2:

                            i = null;

                            int quantity = 0;

                            System.out.println("Enter product name"); // to buy the product using product Id

                            String productName = sc.next();

                            i = storeInfo.displayByProductName(productName); // to display the product details

                            if (i == null) {

                                System.out.println("No products are available with this product Name\n");

                                break;

                            } else {

                                if (i.getAvailableQuantity() == 0) {

                                    System.out.println("No stock available for " + i.getProductName());

                                    break;

                                }

                                System.out.println("Enter a Quantity");

                                quantity = sc.nextInt();

                                try {

                                    if (quantity < 0) {

                                        throw new MyException("Exception : please,Enter postive number! \n"); // userdefined

                                                                                                                // exception

                                                                                                                // for

                                                                                                                // negative

                                                                                                                // value

                                    }

                                } catch (MyException e) {

                                    sc.nextLine();

                                    System.out.println(e.getMessage());

                                    break;

                                }

                                if (i.getAvailableQuantity() >= quantity) {

                                    int currentQuantity = i.getAvailableQuantity() - quantity;

                                    i.setAvailableQuantity(currentQuantity);

                                    Item temp = new Item(i.getProductId(), productName, i.getSellingPrice(), quantity,

                                            i.getCategory());

                                    productList.add(temp); // to add the ordered item in list

                                    System.out.println("Product added to your cart!");

                                    storeInfo.quantityDecreaser(productName, currentQuantity); // update the quantity in

                                                                                                // database

                                } else {

                                    System.out.println("Quantity number out of range!\n");

                                }

                            }

                            break;

                        case 0:

                            break;

                        default:

                            System.out.println("Enter valid choice!\n");

                            break;

                        }

                    } while (ch != 0);

                    break;


                case 0: //as per the question while logging out we need to show the total bill


                    if (productList.isEmpty() != true) { // checking the list whether they ordered or not

                        if (storeInfo.getSuperCoins(u.getEmailId()) != 0) {

                            System.out.println("Do you want to redeem your super coins \n");

                            System.out.println("if YES Enter 1");

                            System.out.println("if NO Enter 0");

                            int redeem = sc.nextInt();

 

                            switch (redeem) {

                            // bill display

                            case 1:

                                n = 0;

                                n = storeInfo.superCoinDecreaser(u.getEmailId()); // to decrease the super coin count

                                if (n == 1) {

                                    System.out.println("                       Final Bill                       \n");

                                    System.out.println(

                                            "-----------------------------------------------------------------------------------------");

                                    System.out.printf("%10s %10s %10s %10s %10s %10s%n", "ProductId", "ProductName",

                                            "Category", "Quantity", "Price", "total price by product");

                                    System.out.println(

                                            "-----------------------------------------------------------------------------------------");

 

                                    productList.forEach(e ->

 

                                    {

                                        System.out.printf("%10s %10s %10s %10s %13s %12s%n", e.getProductId(),

                                                e.getProductName(), e.getCategory(), e.getAvailableQuantity(),

                                                e.getSellingPrice(), e.getSellingPrice() * e.getAvailableQuantity());

                                        total += e.getSellingPrice() * e.getAvailableQuantity();

                                    });

 

                                    System.out.println(

                                            "-----------------------------------------------------------------------------------------");

                                    System.out.println("Total Amount   " + (total - 5)); // super coin discount for new

                                                                                            // user

                                    System.out.println(

                                            "-----------------------------------------------------------------------------------------");

                                }

                                break;

                            // bill display

                            case 0:

                                System.out.println("                       Final Bill                       \n");

                                System.out.println(

                                        "-----------------------------------------------------------------------------------------");

                                System.out.printf("%10s %10s %10s %10s %10s %10s%n", "ProductId", "ProductName",

                                        "Category", "Quantity", "Price", "total price by product");

                                System.out.println(

                                        "-----------------------------------------------------------------------------------------");

 

                                productList.forEach(e -> {

                                    System.out.printf("%10s %10s %10s %10s %13s %12s%n", e.getProductId(),

                                            e.getProductName(), e.getCategory(), e.getAvailableQuantity(),

                                            e.getSellingPrice(), e.getSellingPrice() * e.getAvailableQuantity());

                                    total += e.getSellingPrice() * e.getAvailableQuantity();

                                });

 

                                System.out.println(

                                        "-----------------------------------------------------------------------------------------");

                                System.out.println("Total Amount   " + total);

                                System.out.println(

                                        "-----------------------------------------------------------------------------------------");

 

                                break;

                            default:

                                System.out.println("Please enter vallid choice!");

                                break;

 

                            }

//bill display

                        } else {

                            System.out.println("                       Final Bill                       \n");

                            System.out.println(

                                    "-----------------------------------------------------------------------------------------");

                            System.out.printf("%10s %10s %10s %10s %10s %10s%n", "ProductId", "ProductName", "Category",

                                    "Quantity", "Price", "total price by product");

                            System.out.println(

                                    "-----------------------------------------------------------------------------------------");

 

                            productList.forEach(e -> {

                                System.out.printf("%10s %10s %10s %10s %13s %12s%n", e.getProductId(),

                                        e.getProductName(), e.getCategory(), e.getAvailableQuantity(),

                                        e.getSellingPrice(), e.getSellingPrice() * e.getAvailableQuantity());

                                total += e.getSellingPrice() * e.getAvailableQuantity();

                            });

 

                            System.out.println(

                                    "-----------------------------------------------------------------------------------------");

                            System.out.println("Total Amount   " + total);

                            System.out.println(

                                    "-----------------------------------------------------------------------------------------");

                        }

 

                    }

                    System.out.println("Sucessfully logged out\n");

                    break;

                default:

                    System.out.println("Enter a valid choice!\n");

                }

            } catch (InputMismatchException e) {

                sc.nextLine(); // to clear the buffer

                System.out.println("please, Enter Numbers only");

            }

        } while (choice != 0);

 

    }

}

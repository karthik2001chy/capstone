package pojo;

 

import java.util.List;

 

public class User {

private String userName;

private String emailId;

private String password;

private int superCoins;

private List<Item> productList;

 

public User(String userName, String emailId, String password, int superCoins, List<Item> productList) {

this.userName = userName;

this.emailId = emailId;

this.password = password;

this.superCoins = 100;

this.productList = productList;

}

 

public String getUserName() {

return userName;

}

 

public void setUserName(String userName) {

this.userName = userName;

}

 

public String getEmailId() {

return emailId;

}

 

public void setEmailId(String emailId) {

this.emailId = emailId;

}

 

public String getPassword() {

return password;

}

 

public void setPassword(String password) {

this.password = password;

}

 

public int getSuperCoins() {

return superCoins;

}

 

public List<Item> getProductList() {

return productList;

}

 

public void setProductList(List<Item> productList) {

this.productList = productList;

}

 

@Override

public String toString() {

return "User [userName=" + userName + ", emailId=" + emailId + ", password=" + password + ", superCoins="

+ superCoins + "]";

}

 

}
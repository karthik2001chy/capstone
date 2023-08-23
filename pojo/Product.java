package pojo;

 

public class Product {

private int productId;

private String productName;

private float sellingPrice;

private int availableQuantity;

 

public Product(String productName, int availableQuantity) {

this.productName = productName;

this.availableQuantity = availableQuantity;

}

public Product(int productId, String productName, float sellingPrice, int availableQuantity) {

super();

this.productId = productId;

this.productName = productName;

this.sellingPrice = sellingPrice;

this.availableQuantity = availableQuantity;

}

 

public int getProductId() {

return productId;

}

 

public void setProductId(int productId) {

this.productId = productId;

}

 

public String getProductName() {

return productName;

}

 

public void setProductName(String productName) {

this.productName = productName;

}

 

public float getSellingPrice() {

return sellingPrice;

}

 

public int getAvailableQuantity() {

return availableQuantity;

}

 

public void setAvailableQuantity(int availableQuantity) {

this.availableQuantity = availableQuantity;

}

 

@Override

public String toString() {

return "Product [productId=" + productId + ", productName=" + productName + ", sellingPrice=" + sellingPrice

+ ", availableQuantity=" + availableQuantity + "]";

}

 

}
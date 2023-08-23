package pojo;

 

public class Item extends Product {

private String itemName;

private float buyingPrice;

private String category;

 

public Item(String productName, int availableQuantity, String itemName, float buyingPrice, String category) {

super(productName, availableQuantity);

this.itemName = itemName;

this.buyingPrice = buyingPrice;

this.category = category;

}

 

public Item(int productId, String productName, float sellingPrice, int availableQuantity, float buyingPrice,

String itemName, String category) {

super(productId, productName, sellingPrice, availableQuantity);

this.buyingPrice = buyingPrice;

this.itemName = itemName;

this.category = category;

}

 

public Item(int productId, String productName, float sellingPrice, int availableQuantity, String category) {

super(productId, productName, sellingPrice, availableQuantity);

this.category = category;

}

 

public String getItemName() {

return itemName;

}

 

public void setItemName(String itemName) {

this.itemName = itemName;

}

 

public float getBuyingPrice() {

return buyingPrice;

}

 

public void setBuyingPrice(float buyingPrice) {

this.buyingPrice = buyingPrice;

}

 

public String getCategory() {

return category;

}

 

public void setCategory(String category) {

this.category = category;

}

 

@Override

public String toString() {

return super.toString() + "Item [itemName=" + itemName + ", buyingPrice=" + buyingPrice + ", category="

+ category + "]";

}

 

}
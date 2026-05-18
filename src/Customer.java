import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public  class Customer extends User {
    private String username;
    private Cart personalCart;
    private String fullName;
    private int age;
    private String address;
    private String paymentMethod;
    private String cardNumber; 
    private List<PurchaseOrder> orderHistory;
    public Customer(String username, String password, String fullName, int age, String address, String paymentMethod , String cardNumber) {
        super(username, password);
        this.username = username;
        this.personalCart = new Cart();
        this.fullName = fullName;
        this.age = age;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
        this.orderHistory = new ArrayList<>();
    }
    public Customer(String username, String password) {
    super(username, password); 
    this.username = username;
    this.personalCart = new Cart();
    this.orderHistory = new ArrayList<>();
}
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getFullName(){
        return fullName;
    }
    public void setFullName(String fullName){
        this.fullName = fullName;
    }
    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age = age;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getPaymentMethod(){
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod){
        this.paymentMethod = paymentMethod;
    }
    public List<PurchaseOrder> getOrderHistory(){
        return orderHistory;
    }
    public Cart getPersonalCart() {
        return personalCart;
    }
    public boolean checkout() {
        if (personalCart.getItems().isEmpty()) {
            System.out.println("Cart is empty. Cannot proceed to checkout.");
            return false;
        }
    List<CartItem> itemsToPurchase = new ArrayList<>(personalCart.getItems());
        PurchaseOrder newOrder = new PurchaseOrder(itemsToPurchase, LocalDateTime.now());
        this.orderHistory.add(newOrder);
        System.out.println("Successfully checked out for customer: " + getUsername());
        personalCart.clearCart();
        return true;
    }
}

public  class Customer extends User {
    private Cart personalCart;
    public Customer(String username, String password) {
        super(username, password);
        this.personalCart = new Cart();
    }

    public Cart getPersonalCart() {
        return personalCart;
    }
    public boolean checkout() {
        if (personalCart.getItems().isEmpty()) {
            System.out.println("Cart is empty. Cannot proceed to checkout.");
            return false;
        }
        System.out.println("Successfully checked out for customer: " + getUsername());
        personalCart.clearCart();
        return true;
    }
}

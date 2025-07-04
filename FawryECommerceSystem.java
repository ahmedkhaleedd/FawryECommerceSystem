
// File: FawryECommerceSystem.java

import java.util.ArrayList;
import java.util.List;

interface Shippable {
    String getName();
    double getWeight();
}

class Product {
    private String name;
    private double price;
    private int quantity;
    private boolean isPerishable;
    private boolean requiresShipping;
    private double weight;
    private boolean isExpired;

    public Product(String name, double price, int quantity, boolean isPerishable,
                   boolean requiresShipping, double weight, boolean isExpired) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.isPerishable = isPerishable;
        this.requiresShipping = requiresShipping;
        this.weight = weight;
        this.isExpired = isExpired;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public boolean isPerishable() { return isPerishable; }
    public boolean isRequiresShipping() { return requiresShipping; }
    public double getWeight() { return weight; }
    public boolean isExpired() { return isExpired; }

    public void reduceQuantity(int amount) {
        this.quantity -= amount;
    }
}

class Customer {
    private String name;
    private double balance;

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public boolean deduct(double amount) {
        if (amount > balance) return false;
        balance -= amount;
        return true;
    }

    public double getBalance() { return balance; }
    public String getName() { return name; }
}

class CartItem {
    Product product;
    int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    public double getTotalWeight() {
        return product.getWeight() * quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
}

class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void add(Product product, int quantity) {
        if (product.getQuantity() < quantity) {
            System.out.println("Not enough stock for product: " + product.getName());
            return;
        }
        items.add(new CartItem(product, quantity));
    }

    public List<CartItem> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getSubtotal() {
        return items.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }


    public double getShippingFee() {
        double totalShippingWeight = 0;
    
        for (CartItem item : items) {
            Product product = item.getProduct();
            if (product.isRequiresShipping()) {
                totalShippingWeight += product.getWeight() * item.getQuantity();
            }
        }   
        double kg = totalShippingWeight / 1000.0;
    
        return (kg) * 10;  
    }

    

    public double getTotalAmount() {
        return getSubtotal() + getShippingFee();
    }
}

class ShippingService {
    public static void shipItems(List<CartItem> items) {
        double totalWeight = 0;
        System.out.println("** Shipment notice **");
        for (CartItem item : items) {
            Product product = item.getProduct();
            if (product.isRequiresShipping()) {
                double itemWeight = product.getWeight() * item.getQuantity();
                System.out.println(item.getQuantity() + "x " + product.getName() + "   " + (int)itemWeight + "g");
                totalWeight += itemWeight;
            }
        }
        System.out.println("Total package weight " + totalWeight / 1000 + "kg\n");
    }
}

class CheckoutService {
    public static void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Error: Cart is empty");
            return;
        }

        for (CartItem item : cart.getItems()) {
            if (item.getProduct().isExpired()) {
                System.out.println("Error: Product " + item.getProduct().getName() + " is expired");
                return;
            }
        }

        double total = cart.getTotalAmount();
        if (!customer.deduct(total)) {
            System.out.println("Error: Insufficient balance");
            return;
        }

        ShippingService.shipItems(cart.getItems());

        System.out.println("** Checkout receipt **");
        for (CartItem item : cart.getItems()) {
            System.out.println(item.getQuantity() + "x " + item.getProduct().getName() + "   " + (int)item.getTotalPrice());
        }
        System.out.println("------------------------");
        System.out.println("Subtotal   " + (int)cart.getSubtotal());
        System.out.println("Shipping   " + (int)cart.getShippingFee());
        System.out.println("Amount     " + (int)total);
        System.out.println("Customer balance after payment: " + (int)customer.getBalance());
    }
}

public class FawryECommerceSystem {
    public static void main(String[] args) {
        Product cheese = new Product("Cheese", 100, 10, true, true, 200, false);
        Product tv = new Product("TV", 5000, 5, false, true, 10000, false);
        Product scratchCard = new Product("ScratchCard", 50, 20, false, false, 0, false);
        Product biscuits = new Product("Biscuits", 150, 15, true, true, 700, false);

        Customer customer = new Customer("Ahmed", 30000);

        Cart cart = new Cart();
        cart.add(cheese, 4);
        cart.add(biscuits, 1);
        cart.add(scratchCard, 1);
        cart.add(tv, 2);

        CheckoutService.checkout(customer, cart);
    }
}

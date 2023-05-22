
import java.util.*;

public class GroceryStore {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int choice;
        GroceryStoreImpl store = new GroceryStoreImpl();

        do {
            System.out.println("Welcome to the Grocery Store!");
            System.out.println("Are you a customer or an owner?");
            System.out.println("1. Customer");
            System.out.println("2. Owner");
            System.out.println("3.Exit");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    CustomerImpl customer = new CustomerImpl();
                    customer.manage(store);
                    break;
                case 2:
                    OwnerImpl owner = new OwnerImpl();
                    owner.manage(store);
                    break;

            }
        } while (choice != 3);
        input.close();
    }
}

class GroceryStoreImpl {
    public List<Product> inventory;

    public GroceryStoreImpl() {
        inventory = new ArrayList<>();
    }

    public void addProduct(Product product) {
        inventory.add(product);
    }

    public void removeProduct(int productId) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getId() == productId) {
                inventory.remove(i);
                break;
            }
        }
    }

    public void checkout(List<Product> cart) {
        double totalCost = 0;
        for (int i = 0, cartSize = cart.size(); i < cartSize; i++) {
            Product product = cart.get(i);
            totalCost += product.getPrice();
        }
        System.out.println("     Here is your receipt:   ");
        System.out.println("---------------------------------");
        System.out.println("           PCMC MART    ");
        System.out.println("---------------------------------");
        System.out.println("     No.0023 Chinchwad Road,PCMC                ");
        System.out.println("     Maharashtra,India                  ");
        System.out.println("     www.pcmcgroc.com                   ");
        System.out.println("     +917058964345                      ");
        System.out.println("---------------------------------");
        System.out.println("Item Name                 Price");
        System.out.println("---------------------------------");
        for (int i = 0, cartSize = cart.size(); i < cartSize; i++) {
            Product product = cart.get(i);
            System.out.println(product.getName() + "                      Rs." + product.getPrice());
        }
        System.out.println("---------------------------------");
        System.out.println("Total: Rs." + totalCost);
        System.out.println("---------------------------------");
        
        
        System.out.println("*********************************");
        System.out.println("THANK YOU COME AGAIN ");
        System.out.println("*********************************");
    }

    public Product getProduct(int productId) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getId() == productId) {
                return inventory.get(i);
            }

        }
        return null;
    }

    public void updateProduct(int remainStock, int id) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getId() == id) {
                inventory.get(i).setStock(remainStock);
            }
        }
    }

}

class CustomerImpl {

    public void manage(GroceryStoreImpl store) {
        Scanner input = new Scanner(System.in);
        int choose;
        List<Product> cart = new ArrayList<>();
        double tprice = 0;
        if (store.inventory.isEmpty()) {
            System.out.println("Sorry, the inventory is empty. Please try again later.");
            return;
        }
        System.out.println("Available products:");
        for (Product product : store.inventory) {
            System.out.println(product.getId() + ". " + product.getName() + " - Rs. " + product.getPrice()+"  Quantity-"+product.getStock());
        }

        do {
            System.out.println("Enter the ID of the product you want to buy:");
            int productId = input.nextInt();

            Product product = store.getProduct(productId);
            if (product == null) {
                System.out.println("Product not available.");
            } else {
                System.out.println("Enter the quantity:");
                int quantity = input.nextInt();


                if (quantity > product.getStock()) {
                    System.out.println("Insufficient stock.");
                } else {
                    cart.add(product);
                    double totalPrice = quantity * product.getPrice();
                    int remainStock = product.getStock() - quantity;
                    store.updateProduct(remainStock, product.getId());
                    tprice = tprice + totalPrice;

                }
            }
            System.out.println("Want to generate receipt (1) OR Continue (0):");
            choose = input.nextInt();
        } while (choose != 1);

        store.checkout(cart);

    }

}

class OwnerImpl {

    public void manage(GroceryStoreImpl store) {
        Scanner input = new Scanner(System.in);

        System.out.println("Current inventory:");
        List<Product> inventory = store.inventory;
        for (int i = 0, inventorySize = inventory.size(); i < inventorySize; i++) {
            Product product = inventory.get(i);
             System.out.println(product.getId() + ". " + product.getName() + " - Rs" + product.getPrice()+"  Quantity-"+product.getStock());
        }
        System.out.println("Choose an action:");
        System.out.println("1. Add product");
        System.out.println("2. Remove product");
        int choice = input.nextInt();

        switch (choice) {
            case 1:

                System.out.println("Enter the product ID:");
                int id = input.nextInt();
                input.nextLine();
                System.out.println("Enter the product name:");
                String name = input.nextLine();
                System.out.println("Enter the product price:");
                double price = input.nextDouble();
                System.out.println("Enter the product stock:");
                int stock = input.nextInt();


                Product product = new Product(id, name, price, stock);
                store.addProduct(product);
                System.out.println("Product added successfully.");
                break;
            case 2:
                 if (store.inventory.isEmpty()) {
                     System.out.println(" the inventory is empty. ");
                     return;
                 }
                System.out.println("Enter the ID of the product to remove:");
                int productId = input.nextInt();

                store.removeProduct(productId);
                System.out.println("Product removed successfully.");
                break;

        }


    }

}

class Product {
    private int id;
    private String name;
    private double price;
    private int stock;

    public Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}


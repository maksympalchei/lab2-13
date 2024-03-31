/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab2.pkg13;

/**
 *
 * @author korjk
 */
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Lab213 {

    /**
     * @return the name
     */
    public String getName() {
        return info.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.info.name = name;
    }

    /**
     * @return the balance
     */
    public double getBalance() {
        return info.balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.info.balance = balance;
    }

    /**
     * @return the inventory
     */
    public List<MusicalInstrument> getInventory() {
        return info.inventory;
    }

    /**
     * @param inventory the inventory to set
     */
    public void setInventory(List<MusicalInstrument> inventory) {
        this.info.inventory = inventory;
    }

    /**
     * @return the cart
     */
    public List<MusicalInstrument> getCart() {
        return info.cart;
    }

    /**
     * @param cart the cart to set
     */
    public void setCart(List<MusicalInstrument> cart) {
        this.info.cart = cart;
    }
    Info info = new Info();

    static class Info {

        private String name;
        private double balance;
        private List<MusicalInstrument> inventory;
        private List<MusicalInstrument> cart;
    }

    interface MusicalInstrument {

        void play();

        double getPrice();

        String getBrand();
    }

    class Guitar implements MusicalInstrument {

        private String brand;
        private double price;

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        @Override
        public void play() {
            System.out.println("Playing the guitar");
        }

        @Override
        public double getPrice() {
            return price;
        }

        @Override
        public String getBrand() {
            return brand;
        }
    }

    class Piano implements MusicalInstrument {

        private String brand;
        private double price;

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        @Override
        public void play() {
            System.out.println("Playing the piano");
        }

        @Override
        public double getPrice() {
            return price;
        }

        @Override
        public String getBrand() {
            return brand;
        }
    }

    public void addToCart(MusicalInstrument instrument) {
        Runnable logAddToCart = new Runnable() {
            @Override
            public void run() {
                System.out.println("Інструмент '" + instrument.getBrand() + "' був доданий у кошик.");
            }
        };
        getCart().add(instrument);
        getInventory().remove(instrument);
        System.out.println("Інструмент додано в кошик.");
        logAddToCart.run();
    }

    public void removeFromCart(MusicalInstrument instrument) {
        getCart().remove(instrument);
        getInventory().add(instrument);
        System.out.println("Інструмент повернуто в наявність.");
    }

    public double calculateTotalPriceInCart() {
        double totalPrice = 0;
        for (MusicalInstrument instrument : getCart()) {
            totalPrice += instrument.getPrice();
        }
        return totalPrice;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Lab213 shop = new Lab213();
            shop.setBalance(3000);

            shop.setName("My Musical Instrument Shop");

            Guitar guitar1 = shop.new Guitar();
            guitar1.setBrand("Fender");
            guitar1.setPrice(599.99);

            Guitar guitar2 = shop.new Guitar();
            guitar2.setBrand("Gibson");
            guitar2.setPrice(899.99);
            Guitar guitar3 = shop.new Guitar(){
                @Override
                public String getBrand()
                {
                  return "У цієї гітари бренду нема";
                }
            };
            guitar3.setPrice(20.00); 
            Piano piano1 = shop.new Piano();
            piano1.setBrand("Yamaha");
            piano1.setPrice(2999.99);

            Piano piano2 = shop.new Piano();
            piano2.setBrand("Steinway & Sons");
            piano2.setPrice(8999.99);

            shop.setInventory(new ArrayList<>());
            shop.getInventory().add(guitar1);
            shop.getInventory().add(guitar2);
            shop.getInventory().add(piano1);
            shop.getInventory().add(piano2);
            shop.getInventory().add(guitar3);
            shop.setCart(new ArrayList<>());

            int choice;
            do {
                try {
                    class Menu {

                        public void display() {
                            System.out.println("\nМеню:");
                            System.out.println("1. Переглянути інвентар магазину");
                            System.out.println("2. Додати інструмент у кошик");
                            System.out.println("3. Розрахувати загальну вартість покупок у кошику");
                            System.out.println("4. Придбати все з кошику");
                            System.out.println("0. Вийти");
                        }
                    }
                    Menu menu = new Menu();
                    menu.display();
                    System.out.print("Виберіть опцію: ");
                    choice = scanner.nextInt();

                    switch (choice) {
                        case 1 ->
                            shop.printInventory();
                        case 2 -> {
                            shop.printInventory();
                            System.out.print("Введіть номер інструменту, який ви хочете додати у кошик: ");
                            int instrumentIndex = scanner.nextInt();
                            if (instrumentIndex >= 0 && instrumentIndex < shop.getInventory().size()) {
                                MusicalInstrument selectedInstrument = shop.getInventory().get(instrumentIndex);
                                if (shop.getBalance() >= selectedInstrument.getPrice()) {
                                    shop.addToCart(selectedInstrument);
                                } else {
                                    System.out.println("У вас недостатньо грошей для придбання цього товару.");
                                }
                            } else {
                                System.out.println("Неправильний номер інструменту.");
                            }
                        }
                        case 3 -> {
                            double totalPrice = shop.calculateTotalPriceInCart();
                            System.out.println("Загальна вартість покупок у кошику: " + totalPrice);
                        }
                        case 4 -> {
                            if (!shop.getCart().isEmpty()) {
                                if (shop.getBalance() >= shop.calculateTotalPriceInCart()) {
                                    int price = 0;
                                    for (MusicalInstrument instrument : new ArrayList<>(shop.getCart())) {
                                        shop.removeFromCart(instrument);
                                        price += instrument.getPrice();
                                    }
                                    System.out.println("Ви успішно придбали все з кошику.");
                                    shop.info.balance -= price;
                                } else {
                                    System.out.println("У вас недостатньо грошей для придбання цього товару.");
                                    for (MusicalInstrument instrument : shop.getCart()) {
                                        shop.getInventory().add(instrument);
                                    }
                                    shop.getCart().clear();
                                }
                            } else {
                                System.out.println("Кошик покупок пустий.");
                            }

                        }
                        case 0 ->
                            System.out.println("До побачення!");
                        default ->
                            System.out.println("Неправильний вибір. Будь ласка, спробуйте знову.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Помилка: Введено некоректне значення. Будь ласка, введіть число.");
                    scanner.nextLine();
                    choice = -1;
                }
            } while (choice != 0);
        }
    }

    public void printInventory() {
        System.out.println("\nМузичні інструменти в наявності:");
        for (int i = 0; i < getInventory().size(); i++) {
            MusicalInstrument instrument = getInventory().get(i);
            System.out.println(i + ". " + instrument.getClass().getSimpleName() + " '" + instrument.getBrand() + "' " + ": " + instrument.getPrice());
        }
        System.out.println("Ваш баланс: " + getBalance());
    }
}

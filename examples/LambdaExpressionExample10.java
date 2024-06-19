/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

/**
 *
 * @author Chiew Thiam Kian
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LambdaExpressionExample10 {

    static class Product {

        int id;
        String name;
        float price;

        public Product(int id, String name, float price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
    }

    public static void main(String[] args) {
        List<Product> list = new ArrayList<Product>();

        //Adding Products 
        list.add(new Product(1, "HP Laptop", 2500));
        list.add(new Product(3, "Keyboard", 300));
        list.add(new Product(2, "Dell Mouse", 150));

        System.out.println("Sorting on the basis of name...");

        // implementing lambda expression 
        Collections.sort(list, (p1, p2) -> {
            return p2.name.compareTo(p1.name);
        });
        for (Product p : list) {
            System.out.println(p.id + " " + p.name + " " + p.price);
        }

    }
}

package com.tugas.dianintent;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final List<CartItem> cartItems = new ArrayList<>();

    // Method addProduct sekarang menerima kuantitas
    public static void addProduct(Product product, int quantity) {
        // Cek apakah produk sudah ada di keranjang
        for (CartItem item : cartItems) {
            if (item.getProduct().getName().equals(product.getName())) {
                // Jika sudah ada, cukup tambahkan kuantitasnya
                item.setQuantity(item.getQuantity() + quantity);
                return; // Keluar dari method
            }
        }
        // Jika produk belum ada, buat CartItem baru dan tambahkan ke list
        cartItems.add(new CartItem(product, quantity));
    }

    public static List<CartItem> getCartItems() {
        return cartItems;
    }

    // Method getTotalPrice sekarang menghitung berdasarkan kuantitas
    public static double getTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) {
            // Bersihkan string harga (menghapus "Rp" dan ".")
            String priceString = item.getProduct().getPrice().replaceAll("[^\\d]", "");
            if (!priceString.isEmpty()) {
                double price = Double.parseDouble(priceString);
                // Kalikan harga dengan kuantitas
                total += price * item.getQuantity();
            }
        }
        return total;
    }

    public static void clearCart() {
        cartItems.clear();
    }
}
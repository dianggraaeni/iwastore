package com.tugas.dianintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private TextView totalPriceText, emptyCartText;
    private Button buyButton;
    // --- INI BAGIAN YANG DIPERBAIKI ---
    // Variabel ini sekarang harus bertipe List<CartItem> agar cocok dengan CartManager
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Ambil daftar item dari CartManager. Hasilnya adalah List<CartItem>
        cartItems = CartManager.getCartItems();

        // Inisialisasi komponen UI
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        totalPriceText = findViewById(R.id.totalPriceText);
        buyButton = findViewById(R.id.buyButton);
        emptyCartText = findViewById(R.id.emptyCartText);

        // Setup RecyclerView dan tampilan ringkasan
        setupRecyclerView();
        updateCartSummary();

        // Listener untuk tombol "Beli Sekarang"
        buyButton.setOnClickListener(v -> {
            if (!cartItems.isEmpty()) {
                // Tampilkan notifikasi pembelian berhasil
                Toast.makeText(CartActivity.this, "Pembelian Berhasil! Terima kasih.", Toast.LENGTH_LONG).show();

                // Kosongkan keranjang di CartManager
                CartManager.clearCart();

                // Refresh tampilan halaman keranjang (akan menampilkan pesan kosong)
                updateCartSummary();
            } else {
                Toast.makeText(CartActivity.this, "Keranjang Anda kosong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Fungsi onResume dipanggil setiap kali pengguna kembali ke halaman ini.
        // Ini penting untuk me-refresh total harga jika pengguna kembali dari halaman lain.
        updateCartSummary();
    }

    private void setupRecyclerView() {
        // Berikan List<CartItem> ke CartAdapter
        cartAdapter = new CartAdapter(this, cartItems);
        cartRecyclerView.setAdapter(cartAdapter);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void updateCartSummary() {
        // Cek apakah keranjang kosong
        if (cartItems.isEmpty()) {
            cartRecyclerView.setVisibility(View.GONE);
            emptyCartText.setVisibility(View.VISIBLE);
            buyButton.setEnabled(false); // Nonaktifkan tombol beli jika keranjang kosong
        } else {
            cartRecyclerView.setVisibility(View.VISIBLE);
            emptyCartText.setVisibility(View.GONE);
            buyButton.setEnabled(true); // Aktifkan tombol beli
        }

        // Hitung dan format total harga dari CartManager
        double totalPrice = CartManager.getTotalPrice();
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        totalPriceText.setText(formatRupiah.format(totalPrice));

        // Beri tahu adapter jika ada perubahan data (sangat penting setelah clear cart)
        cartAdapter.notifyDataSetChanged();
    }
}
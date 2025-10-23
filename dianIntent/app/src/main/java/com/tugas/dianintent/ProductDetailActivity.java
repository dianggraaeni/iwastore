package com.tugas.dianintent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailActivity extends AppCompatActivity {

    private int currentQuantity = 1;
    private TextView quantityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Ambil data yang dikirim
        String name = getIntent().getStringExtra("PRODUCT_NAME");
        String price = getIntent().getStringExtra("PRODUCT_PRICE");
        String description = getIntent().getStringExtra("PRODUCT_DESC");
        int imageRes = getIntent().getIntExtra("PRODUCT_IMAGE", 0);

        // Hubungkan ke komponen UI
        ImageView productImage = findViewById(R.id.productDetailImage);
        TextView productName = findViewById(R.id.productDetailName);
        TextView productPrice = findViewById(R.id.productDetailPrice);
        TextView productDesc = findViewById(R.id.productDetailDesc);
        Button addToCartButton = findViewById(R.id.addToCartButton);
        ImageButton plusButton = findViewById(R.id.plusButton);
        ImageButton minusButton = findViewById(R.id.minusButton);
        quantityText = findViewById(R.id.quantityText);

        // Set data ke komponen
        productImage.setImageResource(imageRes);
        productName.setText(name);
        productPrice.setText(price);
        productDesc.setText(description);

        // Logika untuk tombol Plus (+)
        plusButton.setOnClickListener(v -> {
            currentQuantity++;
            quantityText.setText(String.valueOf(currentQuantity));
        });

        // Logika untuk tombol Minus (-)
        minusButton.setOnClickListener(v -> {
            // Pastikan kuantitas tidak kurang dari 1
            if (currentQuantity > 1) {
                currentQuantity--;
                quantityText.setText(String.valueOf(currentQuantity));
            }
        });

        // Logika tombol "Tambah ke Keranjang"
        addToCartButton.setOnClickListener(v -> {
            Product productToAdd = new Product(name, description, price, imageRes, "");

            // Gunakan method addProduct yang baru, dengan kuantitas
            CartManager.addProduct(productToAdd, currentQuantity);

            // Beri notifikasi yang lebih informatif
            String message = name + " (" + currentQuantity + "x) berhasil ditambahkan";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

            finish(); // Tutup halaman
        });
    }
}
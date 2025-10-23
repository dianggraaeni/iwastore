package com.tugas.dianintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private List<Product> allProducts = new ArrayList<>();
    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;

    private Button btnCatMakan, btnCatTas, btnCatPerawatan;

    // Definisikan nama kategori agar konsisten
    private final String CAT_MAKAN = "Peralatan Makan";
    private final String CAT_TAS = "Tas & Kemasan";
    private final String CAT_PERAWATAN = "Perawatan Diri";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Siapkan semua data produk
        setupAllProducts();

        // 2. Siapkan RecyclerView
        setupRecyclerView();

        // 3. Siapkan tombol kategori dan tombol lainnya
        setupButtons();
    }

    private void setupAllProducts() {
        // KATEGORI: PERALATAN MAKAN (6 item)
        allProducts.add(new Product("Sedotan Bambu Reusable", getString(R.string.desc_sedotan_bambu), "Rp15.000", R.drawable.product_sedotan_bambu, CAT_MAKAN));
        allProducts.add(new Product("Botol Minum Stainless", getString(R.string.desc_botol_stainless), "Rp75.000", R.drawable.product_botol_stainless, CAT_MAKAN));
        allProducts.add(new Product("Lunch Box Bambu", getString(R.string.desc_lunch_box_bambu), "Rp45.000", R.drawable.product_lunch_box_bambu, CAT_MAKAN));
        allProducts.add(new Product("Sendok Garpu Kayu", getString(R.string.desc_sendok_kayu), "Rp22.000", R.drawable.product_sendok_kayu, CAT_MAKAN));
        allProducts.add(new Product("Tumbler Kopi", getString(R.string.desc_tumbler_kopi), "Rp89.000", R.drawable.product_tumbler_kopi, CAT_MAKAN));
        allProducts.add(new Product("Cangkir Bambu", getString(R.string.desc_cangkir_bambu), "Rp30.000", R.drawable.product_cangkir_bambu, CAT_MAKAN));

        // KATEGORI: TAS & KEMASAN (3 item)
        allProducts.add(new Product("Tote Bag Daur Ulang", getString(R.string.desc_tote_bag), "Rp25.000", R.drawable.product_tote_bag, CAT_TAS));
        allProducts.add(new Product("Pouch Kain Perca", getString(R.string.desc_pouch_perca), "Rp18.000", R.drawable.product_pouch_perca, CAT_TAS));
        allProducts.add(new Product("Tas Belanja Lipat", getString(R.string.desc_tas_lipat), "Rp12.000", R.drawable.product_tas_lipat, CAT_TAS));

        // KATEGORI: PERAWATAN DIRI (4 item)
        allProducts.add(new Product("Shampoo Bar Herbal", getString(R.string.desc_shampoo_bar), "Rp35.000", R.drawable.product_shampoo_bar, CAT_PERAWATAN));
        allProducts.add(new Product("Sabun Alami Handmade", getString(R.string.desc_sabun_alami), "Rp20.000", R.drawable.product_sabun_alami, CAT_PERAWATAN));
        allProducts.add(new Product("Spons Loofah Alami", getString(R.string.desc_spons_loofah), "Rp8.000", R.drawable.product_spons_loofah, CAT_PERAWATAN));
        allProducts.add(new Product("Deodoran Natural", getString(R.string.desc_deodoran_natural), "Rp40.000", R.drawable.product_deodoran_natural, CAT_PERAWATAN));
    }

    private void setupRecyclerView() {
        productRecyclerView = findViewById(R.id.productRecyclerView);
        // Tampilkan kategori pertama (Peralatan Makan) secara default
        List<Product> initialProducts = filterProductsByCategory(CAT_MAKAN);
        productAdapter = new ProductAdapter(this, initialProducts);
        productRecyclerView.setAdapter(productAdapter);
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void setupButtons() {
        // Inisialisasi Tombol Kategori
        btnCatMakan = findViewById(R.id.btnCatMakan);
        btnCatTas = findViewById(R.id.btnCatTas);
        btnCatPerawatan = findViewById(R.id.btnCatPerawatan);

        // Pasang Listener untuk Tombol Kategori
        btnCatMakan.setOnClickListener(v -> filterAndDisplay(CAT_MAKAN));
        btnCatTas.setOnClickListener(v -> filterAndDisplay(CAT_TAS));
        btnCatPerawatan.setOnClickListener(v -> filterAndDisplay(CAT_PERAWATAN));

        // Set tombol kategori pertama sebagai aktif secara default
        updateButtonStyles(btnCatMakan);

        // Pasang Listener untuk Tombol Keranjang
        findViewById(R.id.cartButton).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CartActivity.class));
        });

        // Pasang Listener untuk Tombol Kembali
        findViewById(R.id.backButton).setOnClickListener(v -> {
            // Aksi untuk tombol kembali, contoh: menutup activity
            finish();
        });
    }

    private void filterAndDisplay(String category) {
        // 1. Saring daftar produk berdasarkan kategori yang dipilih
        List<Product> filteredList = filterProductsByCategory(category);
        // 2. Update data di adapter
        productAdapter.updateProducts(filteredList);

        // 3. Update style tombol
        if (category.equals(CAT_MAKAN)) updateButtonStyles(btnCatMakan);
        else if (category.equals(CAT_TAS)) updateButtonStyles(btnCatTas);
        else if (category.equals(CAT_PERAWATAN)) updateButtonStyles(btnCatPerawatan);
    }

    private List<Product> filterProductsByCategory(String category) {
        // Menggunakan Java Stream untuk menyaring produk, lebih modern dan efisien
        return allProducts.stream()
                .filter(product -> product.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    // Fungsi untuk mengubah tampilan tombol yang aktif
    private void updateButtonStyles(Button activeButton) {
        Button[] allButtons = {btnCatMakan, btnCatTas, btnCatPerawatan};
        for (Button button : allButtons) {
            MaterialButton materialButton = (MaterialButton) button;
            if (button == activeButton) {
                // Style untuk tombol aktif
                materialButton.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_green));
                materialButton.setTextColor(ContextCompat.getColor(this, R.color.white));
                materialButton.setStrokeWidth(0);
            } else {
                // Style untuk tombol tidak aktif (outline)
                materialButton.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                materialButton.setTextColor(ContextCompat.getColor(this, R.color.text_dark));
                materialButton.setStrokeColorResource(R.color.text_light);
                materialButton.setStrokeWidth(2);
            }
        }
    }
}
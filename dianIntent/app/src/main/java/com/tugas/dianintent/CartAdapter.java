package com.tugas.dianintent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems; // Diubah dari List<Product> menjadi List<CartItem>
    private Context context;

    // Constructor juga diubah untuk menerima List<CartItem>
    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Bagian ini tetap sama, karena layout-nya tidak berubah
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        // --- Logika di sini berubah total ---

        // 1. Dapatkan objek CartItem pada posisi saat ini
        CartItem cartItem = cartItems.get(position);
        // 2. Dapatkan objek Product dari dalam CartItem
        Product product = cartItem.getProduct();

        // 3. Set data produk ke komponen UI
        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice());
        holder.productImage.setImageResource(product.getImageResource());

        // 4. Set data kuantitas ke TextView kuantitas
        holder.productQuantity.setText("Jumlah: " + cartItem.getQuantity());
    }

    @Override
    public int getItemCount() {
        // Bagian ini tetap sama, mengembalikan jumlah item di keranjang
        return cartItems.size();
    }

    // ViewHolder di-update untuk "memegang" TextView kuantitas
    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView productQuantity; // <-- DITAMBAHKAN

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.cartProductImage);
            productName = itemView.findViewById(R.id.cartProductName);
            productPrice = itemView.findViewById(R.id.cartProductPrice);
            productQuantity = itemView.findViewById(R.id.cartProductQuantity); // <-- DITAMBAHKAN
        }
    }
}
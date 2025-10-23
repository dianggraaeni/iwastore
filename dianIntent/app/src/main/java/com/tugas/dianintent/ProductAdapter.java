package com.tugas.dianintent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    // Fungsi untuk mengupdate daftar produk yang ditampilkan
    public void updateProducts(List<Product> newProductList) {
        this.productList = newProductList;
        notifyDataSetChanged(); // Memberi tahu RecyclerView untuk refresh
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Membuat tampilan untuk setiap item dari layout item_product.xml
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Mengambil data produk pada posisi tertentu
        Product product = productList.get(position);

        // Menampilkan data ke komponen di dalam ViewHolder
        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice());
        holder.productImage.setImageResource(product.getImageResource());

        // Menambahkan OnClickListener untuk setiap item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("PRODUCT_NAME", product.getName());
            intent.putExtra("PRODUCT_PRICE", product.getPrice());
            intent.putExtra("PRODUCT_DESC", product.getDescription());
            intent.putExtra("PRODUCT_IMAGE", product.getImageResource());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        // Mengembalikan jumlah total item dalam daftar
        return productList.size();
    }

    // ViewHolder: Kelas ini "memegang" komponen-komponen dari layout item_product.xml
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}
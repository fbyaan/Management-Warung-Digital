// src/model/TransaksiItem.java
package model;

public class TransaksiItem {
    private Produk produk;
    private int qty;

    public TransaksiItem(Produk produk, int qty) {
        this.produk = produk;
        this.qty = qty;
    }

    public Produk getProduk() {
        return produk;
    }

    public int getQty() {
        return qty;
    }
}
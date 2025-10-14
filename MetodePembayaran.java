import java.text.DecimalFormat;
// MetodePembayaran.java
public abstract class MetodePembayaran {
    // Enkapsulasi: field dilindungi
    private double jumlahPembayaran;

    protected static final DecimalFormat FORMATTER = new DecimalFormat("#,##0.00");

    public MetodePembayaran(double jumlah) {
        this.jumlahPembayaran = jumlah;
    }

    public double getJumlahPembayaran() {
        return jumlahPembayaran;
    }

    // Metode Abstrak (Polimorfisme/Overriding): Harus diimplementasikan oleh subkelas
    public abstract boolean validasi();
    public abstract boolean proses() throws PembayaranGagalException;
    public abstract void cetakStruk();

    // Metode non-abstrak: bisa diwariskan
    public void tampilkanStatus() {
        // Menggunakan FORMATTER untuk menampilkan jumlah
        String jumlahTerformat = FORMATTER.format(jumlahPembayaran);
        System.out.println("Memproses pembayaran sejumlah: Rp " + jumlahTerformat);
    }

}
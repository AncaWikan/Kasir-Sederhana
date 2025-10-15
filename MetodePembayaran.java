import java.text.DecimalFormat;
public abstract class MetodePembayaran {
    private double jumlahPembayaran;

    protected static final DecimalFormat FORMATTER = new DecimalFormat("#,##0.00");

    public MetodePembayaran(double jumlah) {
        this.jumlahPembayaran = jumlah;
    }

    public double getJumlahPembayaran() {
        return jumlahPembayaran;
    }

    public abstract boolean validasi();
    public abstract boolean proses() throws PembayaranGagalException;
    public abstract void cetakStruk();

    public void tampilkanStatus() {
        String jumlahTerformat = FORMATTER.format(jumlahPembayaran);
        System.out.println("Memproses pembayaran sejumlah: Rp " + jumlahTerformat);
    }

}
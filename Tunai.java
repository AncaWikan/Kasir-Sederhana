import java.text.DecimalFormat;
public class Tunai extends MetodePembayaran {
    private double jumlahBayarTunai;

    public Tunai(double jumlah, double jumlahBayarTunai) {
        super(jumlah);
        this.jumlahBayarTunai = jumlahBayarTunai;
    }

    @Override
    public boolean validasi() {
        return jumlahBayarTunai >= getJumlahPembayaran();
    }

    @Override
    public boolean proses() throws PembayaranGagalException {
    if (!validasi()) {
        throw new PembayaranGagalException("Pembayaran Tunai Gagal. Uang yang diberikan kurang dari " + getJumlahPembayaran());
    }

    double kembalian = jumlahBayarTunai - getJumlahPembayaran();
    
    DecimalFormat formatter = new DecimalFormat("#,##0.00");
    
    System.out.printf("Pembayaran Tunai berhasil. Kembalian: Rp %s\n", formatter.format(kembalian));
    return true;
}

    @Override
    public void cetakStruk() {
        System.out.println("Struk: Pembayaran Tunai. Jumlah dibayar: " + jumlahBayarTunai + ". Total: " + getJumlahPembayaran());
    }

}
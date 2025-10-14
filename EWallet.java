public class EWallet extends MetodePembayaran {
    //enkapsulasi
    private String idPelanggan;
    private double saldoEWallet; 

    public EWallet(double jumlah, String idPelanggan, double saldo) {
        super(jumlah);
        this.idPelanggan = idPelanggan;
        this.saldoEWallet = saldo;
    }

    public double getSaldoEWallet() {
        return saldoEWallet;
    }

    @Override
    public boolean validasi() {
        // untuk validasi e wallet
        System.out.println("Validasi E-Wallet: Berhasil.");
        return true;
    }

    public void setSaldoEWallet(double saldoEWallet) {
    this.saldoEWallet = saldoEWallet;
}

    @Override
    public boolean proses() throws PembayaranGagalException {
        if (!validasi()) {
            throw new PembayaranGagalException("Validasi E-Wallet Gagal.");
        }
        // jika gagal
        if (idPelanggan.equals("GAGAL_TOLAK")) {
             throw new PembayaranGagalException("Transaksi E-Wallet ditolak oleh server.");
        }
        if (saldoEWallet < getJumlahPembayaran()) {
        // pake formatter dari metode pembayaran 
        String saldoTerformat = FORMATTER.format(saldoEWallet);
        throw new PembayaranGagalException("Saldo E-Wallet tidak mencukupi. Saldo saat ini: Rp " + saldoTerformat);
        }
        // jika berhasil
        if (saldoEWallet >= getJumlahPembayaran()) {
        saldoEWallet -= getJumlahPembayaran();
        System.out.printf("Pembayaran E-Wallet berhasil. Saldo sisa: Rp %s\n", FORMATTER.format(saldoEWallet));
        return true;
        } else {
        throw new PembayaranGagalException(String.format("Saldo E-Wallet tidak mencukupi. Saldo saat ini: Rp %,.2f", saldoEWallet));
        }
    }

    @Override
    public void cetakStruk() {
        System.out.println("Struk: Pembayaran E-Wallet. ID Pelanggan: " + idPelanggan);
    }

}


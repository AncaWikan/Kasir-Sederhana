public class KartuDebit extends MetodePembayaran {
    // enkapsulasi
    private String nomorKartu;
    private double saldoKartu;

    public KartuDebit(double jumlah, String nomorKartu, double saldo) {
        super(jumlah); // constructor warisan dari metode pembayaran
        this.nomorKartu = nomorKartu;
        this.saldoKartu = saldo;
    }

    public double getSaldoKartu() {
        return saldoKartu;
    }

    // Overriding: Implementasi validasi spesifik
    @Override
    public boolean validasi() {
        // Logika validasi kartu: cek nomor kartu, tanggal kedaluwarsa, dll.
        System.out.println("Validasi Kartu Debit: Berhasil.");
        return true;
    }

    public void setSaldoKartu(double saldoKartu) {
    this.saldoKartu = saldoKartu;
    }

    // Overriding: Implementasi proses spesifik dengan Exception Handling
    @Override
    public boolean proses() throws PembayaranGagalException {
        if (!validasi()) {
            throw new PembayaranGagalException("Validasi Kartu Debit Gagal.");
        }
        if (saldoKartu < getJumlahPembayaran()) {
            // Skema Kegagalan: Saldo tidak cukup
            throw new PembayaranGagalException("Saldo Kartu Debit tidak mencukupi. Saldo saat ini: " + saldoKartu);
        }

        // Skema Keberhasilan
        if (saldoKartu >= getJumlahPembayaran()) {
        saldoKartu -= getJumlahPembayaran(); // Update saldo
        System.out.printf("Pembayaran Kartu Debit berhasil. Saldo sisa: Rp %,.2f\n", saldoKartu);
        return true;
    } else {
        throw new PembayaranGagalException(String.format("Saldo Kartu Debit tidak mencukupi. Saldo saat ini: Rp %,.2f", saldoKartu));
    }

        
    }

    @Override
    public void cetakStruk() {
        System.out.println("Struk: Pembayaran Kartu Debit. Nomor Kartu: XXXX-XXXX-" + nomorKartu.substring(12));
    }

}




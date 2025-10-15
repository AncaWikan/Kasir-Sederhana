public class KartuDebit extends MetodePembayaran {
    private String nomorKartu;
    private double saldoKartu;

    public KartuDebit(double jumlah, String nomorKartu, double saldo) {
        super(jumlah);
        this.nomorKartu = nomorKartu;
        this.saldoKartu = saldo;
    }

    public double getSaldoKartu() {
        return saldoKartu;
    }

    @Override
    public boolean validasi() {
        System.out.println("Validasi Kartu Debit: Berhasil.");
        return true;
    }

    public void setSaldoKartu(double saldoKartu) {
    this.saldoKartu = saldoKartu;
    }

    @Override
    public boolean proses() throws PembayaranGagalException {
        if (!validasi()) {
            throw new PembayaranGagalException("Validasi Kartu Debit Gagal.");
        }
        if (saldoKartu < getJumlahPembayaran()) {
            throw new PembayaranGagalException("Saldo Kartu Debit tidak mencukupi. Saldo saat ini: " + saldoKartu);
        }
        if (saldoKartu >= getJumlahPembayaran()) {
        saldoKartu -= getJumlahPembayaran();
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




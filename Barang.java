public class Barang {
    private String nama;
    private double hargaSatuan;

    public Barang(String nama, double hargaSatuan) {
        this.nama = nama;
        this.hargaSatuan = hargaSatuan;
    }

    public String getNama() {
        return nama;
    }

    public double getHargaSatuan() {
        return hargaSatuan;
    }
}
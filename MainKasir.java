import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.DecimalFormat;

public class MainKasir {
    
    private static double saldoDebitSaatIni = 50000000.0;
    private static double saldoEWalletSaatIni = 5000000.0;
    private static final String NO_KARTU_DEBIT = "1234567890123456";
    private static final String ID_EWALLET = "pelanggan@ewallet.com";
    
    private static final List<Barang> INVENTARIS = new ArrayList<>();
    
    static {
        INVENTARIS.add(new Barang("RTX 3090", 25000000.0));
        INVENTARIS.add(new Barang("Ryzen 9 7950X", 9500000.0));
        INVENTARIS.add(new Barang("RAM DDR5 32GB", 2800000.0));
        INVENTARIS.add(new Barang("SSD NVMe 1TB", 1500000.0));
        INVENTARIS.add(new Barang("Monitor 4K 144Hz", 8000000.0));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Kasir kasir = new Kasir();
        boolean belanjaLagi = true;
        
        System.out.println("================ SISTEM KASIR KELOMPOK 6 ================");
    
        while (belanjaLagi) {
            System.out.println("\n*** TRANSAKSI BARU ***");
            System.out.printf("Saldo Debit Saat Ini: Rp %,.2f | Saldo E-Wallet Saat Ini: Rp %,.2f\n", saldoDebitSaatIni, saldoEWalletSaatIni);
            
            double totalBelanja = 0;
            try {
                totalBelanja = prosesInputBarang(scanner);
            } catch (java.util.InputMismatchException e) {
                System.err.println("Input tidak valid. Membatalkan transaksi ini.");
                scanner.nextLine();
                continue;
            }

            if (totalBelanja <= 0) {
                System.out.println("Tidak ada barang yang dibeli. Melanjutkan...");
            } else {
                System.out.println("======================================================");
                System.out.printf("Total Belanja yang Harus Dibayar: Rp %,.2f\n", totalBelanja);
                System.out.println("======================================================");

                try {
        prosesPembayaranDanUpdateSaldo(kasir, scanner, totalBelanja);
    } catch (RuntimeException e) {
        if (e.getMessage().equals("KELUAR_KASIR")) {
            belanjaLagi = false; 
        } else if (e.getMessage().equals("ULANG_PEMESANAN")) {
            System.out.println("\n*** Transaksi dibatalkan. Silakan pilih barang kembali. ***");
        }
    }
            }
            
            boolean inputValid = false;
            while (!inputValid) {
                System.out.print("\nApakah Anda ingin belanja kembali? (ya/tidak): ");
                String jawaban = scanner.next().toLowerCase();
                
                if (jawaban.equals("ya")) {
                    belanjaLagi = true;
                    inputValid = true;
                } else if (jawaban.equals("tidak")) {
                    belanjaLagi = false;
                    inputValid = true;
                } else {
                    System.out.println("Input tidak valid. Harap masukkan 'ya' atau 'tidak'.");
                }
            }
        }
        
        System.out.println("\nTerima kasih telah menggunakan sistem kasir.");
        scanner.close();
    }

private static void prosesPembayaranDanUpdateSaldo(Kasir kasir, Scanner scanner, double totalBelanja) {
    boolean transaksiSelesai = false;

    while (!transaksiSelesai) {
        MetodePembayaran metodePilihan = null;

        System.out.println("\nPilih Metode Pembayaran:");
        System.out.println("1. Kartu Debit");
        System.out.println("2. E-Wallet");
        System.out.println("3. Tunai");
        System.out.print("Pilihan Anda (1/2/3): ");
        int pilihan = scanner.nextInt();
        
        try {
            switch (pilihan) {
                case 1:
                    metodePilihan = new KartuDebit(totalBelanja, NO_KARTU_DEBIT, saldoDebitSaatIni);
                    break;
                case 2:
                    metodePilihan = new EWallet(totalBelanja, ID_EWALLET, saldoEWalletSaatIni);
                    break;
                case 3:
                    System.out.print("Masukkan Jumlah Uang Tunai yang Dibayarkan: Rp ");
                    double uangTunai = scanner.nextDouble();
                    metodePilihan = new Tunai(totalBelanja, uangTunai);
                    break;
                default:
                    System.err.println("Pilihan tidak valid. Transaksi dibatalkan.");
                    return;
            }

            System.out.println("\n*** Memproses Transaksi ***");
            kasir.prosesPembayaran(metodePilihan); 
            
            transaksiSelesai = true; 
            
            if (metodePilihan instanceof KartuDebit) {
                KartuDebit kd = (KartuDebit) metodePilihan;
                saldoDebitSaatIni = kd.getSaldoKartu(); 
            } else if (metodePilihan instanceof EWallet) {
                EWallet ew = (EWallet) metodePilihan;
                saldoEWalletSaatIni = ew.getSaldoEWallet();
            }

        } catch (PembayaranGagalException e) {
            System.err.println(" " + e.getMessage());
            System.out.println("------------------------------------");
            
            boolean menuGagalValid = false;
            while (!menuGagalValid) {
                System.out.println("\n[ Pemberitahuan Transaksi Gagal ]");
                System.out.println("1. Ganti Metode Pembayaran (Ulangi Proses Pembayaran)");
                System.out.println("2. Ulang Kembali Memesan Barang (Membatalkan Transaksi Saat Ini)");
                System.out.println("3. Tidak Jadi Membeli (Keluar dari Kasir)");
                System.out.print("Pilihan Anda (1/2/3): ");
                
                if (scanner.hasNextInt()) {
                    int pilihanGagal = scanner.nextInt();
                    
                    if (pilihanGagal == 1) {
                        menuGagalValid = true;
                    } else if (pilihanGagal == 2) {
                        throw new RuntimeException("ULANG_PEMESANAN"); 
                    } else if (pilihanGagal == 3) {
                        throw new RuntimeException("KELUAR_KASIR");
                    } else {
                        System.out.println("Pilihan tidak valid.");
                    }
                } else {
                    System.out.println("Input tidak valid. Harap masukkan angka (1/2/3).");
                    scanner.next(); 
                }
            }
            
        } catch (java.util.InputMismatchException e) {
            System.err.println("Input tidak valid (harus angka). Transaksi dibatalkan.");
            scanner.nextLine();
            return;
        } 
    }
}
    
    private static double prosesInputBarang(Scanner scanner) {
        double total = 0;
        
        System.out.println("--- DAFTAR INVENTARIS ---");
        for (int i = 0; i < INVENTARIS.size(); i++) {
            Barang b = INVENTARIS.get(i);
            System.out.printf("%d. %-20s (Rp %,.2f)\n", i + 1, b.getNama(), b.getHargaSatuan());
        }
        System.out.println("0. Selesai Memilih");
        
        int pilihanBarang = -1;
        while (pilihanBarang != 0) {
            System.out.print("\nPilih No. Barang (1-" + INVENTARIS.size() + ") atau 0 untuk Selesai: ");
            if (scanner.hasNextInt()) {
                pilihanBarang = scanner.nextInt();
            } else {
                System.out.println("Pilihan tidak valid.");
                scanner.next(); 
                continue;
            }

            if (pilihanBarang > 0 && pilihanBarang <= INVENTARIS.size()) {
                Barang barangDipilih = INVENTARIS.get(pilihanBarang - 1);
                System.out.print("Masukkan Kuantitas " + barangDipilih.getNama() + ": ");
                int kuantitas = scanner.nextInt();
                
                if (kuantitas > 0) {
                    double subTotal = barangDipilih.getHargaSatuan() * kuantitas;
                    total += subTotal;
                    System.out.printf("-> Ditambahkan: %s x%d. Subtotal: Rp %,.2f\n", barangDipilih.getNama(), kuantitas, subTotal);
                }
            } else if (pilihanBarang != 0) {
                System.out.println("Nomor barang tidak ditemukan.");
            }
        }
        return total;
    }

    
}
package menjacnica.gui;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;

public class GUIKontroler {
	private static MenjacnicaInterface sistem;
	
	private static MenjacnicaGUI menjacnicaGUI;
	private static DodajKursGUI dodajKursGUI;
	private static ObrisiKursGUI obrisiKursGUI;
	private static IzvrsiZamenuGUI izvrsiZamenuGUI;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sistem = new Menjacnica();
					menjacnicaGUI = new MenjacnicaGUI();
					menjacnicaGUI.setVisible(true);
					menjacnicaGUI.setLocationRelativeTo(null);
					menjacnicaGUI.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent e) {
							ugasiAplikaciju();
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public static void otvoriDodajKursProzor() {
		dodajKursGUI = new DodajKursGUI(menjacnicaGUI);
		dodajKursGUI.setLocationRelativeTo(menjacnicaGUI);
		dodajKursGUI.setVisible(true);
	}


	public static void otvoriObrisiKursProzor(Valuta valuta) {
		obrisiKursGUI = new ObrisiKursGUI(menjacnicaGUI, valuta);
		obrisiKursGUI.setLocationRelativeTo(menjacnicaGUI);
		obrisiKursGUI.setVisible(true);
	}


	public static void otvoriIzvrsiZamenuProzor(Valuta valuta) {
		izvrsiZamenuGUI = new IzvrsiZamenuGUI(menjacnicaGUI, valuta);
		izvrsiZamenuGUI.setLocationRelativeTo(menjacnicaGUI);
		izvrsiZamenuGUI.setVisible(true);
	}


	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(menjacnicaGUI);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				sistem.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGUI, e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}


	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(menjacnicaGUI, "Da li ZAISTA zelite da izadjete iz apliacije",
				"Izlazak", JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}


	public static void prikaziAboutProzor() {
		JOptionPane.showMessageDialog(menjacnicaGUI, "Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}


	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(menjacnicaGUI);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				sistem.ucitajIzFajla(file.getAbsolutePath());
				menjacnicaGUI.prikaziSveValute();
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGUI, e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	
		
	}

	public static List<Valuta> vratiKursnuListu() {
		return sistem.vratiKursnuListu();
	}


	public static void unesiKurs(Valuta valuta) {
		// Dodavanje valute u kursnu listu
		sistem.dodajValutu(valuta);

		// Osvezavanje glavnog prozora
		menjacnicaGUI.prikaziSveValute();
		
	}


	public static double izvrsiZamenuValute(Valuta valuta, double iznos, boolean selected) {
		return sistem.izvrsiTransakciju(valuta, selected, iznos);
	}


	public static void obrisiValutu(Valuta valuta) {
		sistem.obrisiValutu(valuta);
		menjacnicaGUI.prikaziSveValute();
		
	}
	
}

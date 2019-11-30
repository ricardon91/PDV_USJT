package ExpoUSJT;

import java.awt.EventQueue;
import java.awt.Image;
import java.sql.Connection;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.pnuema.java.barcode.Barcode;

public class MaisProdutos extends TelaPrincipal {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MaisProdutos frame = new MaisProdutos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MaisProdutos() {

		Connection conn = Conexao.getConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(750, 30, 800, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Barcode b = new Barcode();

		Mercadoria m = new Mercadoria();
		List<Mercadoria> lista = m.semCodigo(conn);

		int falta = 12 - lista.size();

		Mercadoria mm;
		for (int i = 0; i < falta; i++) {
			mm = new Mercadoria();
			mm.setCodigo(Util.getEan13Random());
			System.out.println(mm.getCodigo());
			mm.setDescricao("Vazio");
			lista.add(mm);
		}

		JLabel codigoBarra = null;
		int x = 60;
		int y = 30;
		int width = 300;
		int height = 70;
		int c = 0;

		for (int j = 0; j < 6; j++) {
			for (int i = 0; i < 2; i++) {
				Image img = b.encode(Barcode.TYPE.EAN13, lista.get(c).getCodigo());
				ImageIcon icon2 = new ImageIcon(img);
				codigoBarra = new JLabel(icon2);
				codigoBarra.setBounds(x, y, width, height);
				contentPane.add(codigoBarra);
				codigoBarra = new JLabel(lista.get(c).getDescricao());
				codigoBarra.setBounds(x + 20, y + 70, width, 50);
				contentPane.add(codigoBarra);
				x += 350;
				c++;
			}
			y += 120;
			x = 60;
		}

	}
}

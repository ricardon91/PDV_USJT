package ExpoUSJT;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class AlteraPreco extends JFrame {

	private JPanel contentPane;
	private JTextField txtCodigo;
	private JTextField txtPrecoNovo;
	private static Timer timer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlteraPreco frame = new AlteraPreco();
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
	public AlteraPreco() {

		setTitle("Alterar Preço");

		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
			@Override
			public void eventDispatched(AWTEvent event) {
				Object source = event.getSource();
				if (source instanceof Component) {
					Component comp = (Component) source;
					Window win = null;
					if (comp instanceof Window) {
						win = (Window) comp;
					} else {
						win = SwingUtilities.windowForComponent(comp);
					}
					if (win == win) {
						timer.restart();
					}
				}
			}
		}, AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK
				| AWTEvent.MOUSE_WHEEL_EVENT_MASK);

		timer = new Timer(10000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		timer.start();

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCodigoDoProduto = new JLabel("Codigo do produto");
		lblCodigoDoProduto.setBounds(21, 36, 119, 14);
		contentPane.add(lblCodigoDoProduto);

		JLabel lblProduto = new JLabel("Produto");
		lblProduto.setBounds(33, 85, 46, 14);
		contentPane.add(lblProduto);

		JLabel lblNome = new JLabel("");
		lblNome.setBounds(99, 85, 228, 14);
		contentPane.add(lblNome);

		JLabel lblPreco = new JLabel("Pre\u00E7o");
		lblPreco.setBounds(33, 125, 46, 14);
		contentPane.add(lblPreco);

		JLabel lblPrecoVelho = new JLabel("");
		lblPrecoVelho.setBounds(99, 125, 68, 14);
		contentPane.add(lblPrecoVelho);

		txtPrecoNovo = new JTextField();
		txtPrecoNovo.setBounds(99, 167, 75, 20);
		contentPane.add(txtPrecoNovo);
		txtPrecoNovo.setColumns(10);

		JLabel lblNovoPreo = new JLabel("Novo Pre\u00E7o");
		lblNovoPreo.setBounds(21, 170, 68, 14);
		contentPane.add(lblNovoPreo);

		txtCodigo = new JTextField();
		txtCodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn = Conexao.getConnection();

				try {

					conn.setAutoCommit(false);
					Mercadoria mercadoria = new Mercadoria();

					mercadoria.setCodigo(txtCodigo.getText());

					mercadoria.carregar(conn);
//					conn.commit();

					if (mercadoria.getId() != -1) {

						lblNome.setText(mercadoria.getDescricao());
						lblPrecoVelho.setText(
								"R$ " + mercadoria.getPreco().toString().format("%.2f", mercadoria.getPreco()));
						txtPrecoNovo.requestFocus();

					} else {

						JOptionPane.showMessageDialog(null, "O produto não existe");

					}

				} catch (SQLException erro) {
					JOptionPane.showMessageDialog(null, erro + "erro1");
					if (conn != null) {
						try {
							conn.rollback();
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, e1 + "erro2");
						}
					}
				} finally {
					if (conn != null) {
						try {
							conn.close();
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, e1 + "erro3");
						}
					}
				}

			}
		});
		txtCodigo.setBounds(133, 33, 260, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);

		JButton btnConfirma = new JButton("Confirma");
		btnConfirma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn = Conexao.getConnection();

				try {

					conn.setAutoCommit(false);
					Mercadoria mercadoria = new Mercadoria();

					mercadoria.setCodigo(txtCodigo.getText());

					mercadoria.carregar(conn);

					if (mercadoria.getId() != -1) {

						int confirma = JOptionPane.showConfirmDialog(null,
								"O novo preço esta correto?\n\nDescrição: " + lblNome.getText()
										+ "\nPreço anterior: R$ " + lblPrecoVelho.getText() + "\nPreço novo: R$ "
										+ txtPrecoNovo.toString().format("%.2f",
												Double.parseDouble(txtPrecoNovo.getText())),
								"Confirme", JOptionPane.YES_NO_OPTION);

						if (confirma == 0) {
							mercadoria.setPreco(Double.parseDouble(txtPrecoNovo.getText()));
							mercadoria.atualizar(conn);
							conn.commit();
							JOptionPane.showMessageDialog(null, "Preco atualizado");
//						txtCodigo.setText("");
//						txtPrecoNovo.setText("");
//						lblNome.setText("");
//						lblPrecoVelho.setText("");
							dispose();

						}

					} else {

						JOptionPane.showMessageDialog(null, "Produto não existe, favor verificar ");

					}

				} catch (SQLException erro) {
					JOptionPane.showMessageDialog(null, erro + "erro1");
					if (conn != null) {
						try {
							conn.rollback();
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, e1 + "erro2");
						}
					}
				} finally {
					if (conn != null) {
						try {
							conn.close();
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, e1 + "erro3");
						}
					}
				}

			}

		});
		btnConfirma.setBounds(317, 209, 89, 23);
		contentPane.add(btnConfirma);

		getRootPane().setDefaultButton(btnConfirma);
	}
}

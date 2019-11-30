package ExpoUSJT;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;
import javax.swing.JCheckBox;

public class Cadastro extends JFrame {

	private JPanel contentPane;
	private JTextField txtCodigoDeBarras;
	private JTextField txtDesc;
	private JTextField txtUni;
	private JLabel lblValor;
	private JTextField txtValor;
	private JLabel lblDataAtualizao;
	private JLabel lblStatus;
	private String codigo;
	private Mercadoria mercadoria;
	public boolean veioDoLeitor = false;
	private static Timer timer;
	MaisProdutos maisprodutos;

	// Timer timer;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cadastro frame = new Cadastro("", false, new TelaPrincipal());
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Cadastro(String codigo, boolean veioDoLeitor, TelaPrincipal telaPrincipal) {

		Connection conn = Conexao.getConnection();
		setTitle("Cadastro de Produtos");
		this.codigo = codigo;
		this.veioDoLeitor = veioDoLeitor;

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

		timer = new Timer(5000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		timer.start();

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 485, 278);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblCodigoBarras = new JLabel("Codigo de barras");
		lblCodigoBarras.setBounds(22, 30, 105, 14);
		contentPane.add(lblCodigoBarras);

		txtCodigoDeBarras = new JTextField();
		txtCodigoDeBarras.setBounds(123, 27, 227, 20);
		contentPane.add(txtCodigoDeBarras);
		txtCodigoDeBarras.setColumns(10);
		txtCodigoDeBarras.setText(codigo);

		JLabel lblDesc = new JLabel("Descri\u00E7\u00E3o");
		lblDesc.setBounds(22, 61, 71, 14);
		contentPane.add(lblDesc);

		txtDesc = new JTextField();
		txtDesc.setBounds(123, 58, 227, 20);
		contentPane.add(txtDesc);
		txtDesc.setColumns(10);

		if (veioDoLeitor) {
			System.out.println("TEXTO");

			addWindowListener(new WindowAdapter() {
				public void windowOpened(WindowEvent e) {
					txtDesc.requestFocus();
					txtCodigoDeBarras.setEditable(false);
				}
			});

		} else {
			System.out.println("BARRAS");
			txtCodigoDeBarras.requestFocus();
		}

		JLabel lblUnidades = new JLabel("Unidade(s)");
		lblUnidades.setBounds(22, 92, 71, 14);
		contentPane.add(lblUnidades);

		txtUni = new JTextField();
		txtUni.setBounds(123, 89, 86, 20);
		contentPane.add(txtUni);
		txtUni.setColumns(10);

		lblValor = new JLabel("Valor");
		lblValor.setBounds(22, 123, 46, 14);
		contentPane.add(lblValor);

		txtValor = new JTextField();
		txtValor.setBounds(123, 120, 86, 20);
		contentPane.add(txtValor);
		txtValor.setColumns(10);

		lblDataAtualizao = new JLabel("Data Atualiza\u00E7\u00E3o");
		lblDataAtualizao.setBounds(22, 153, 96, 14);
		contentPane.add(lblDataAtualizao);

		lblStatus = new JLabel("Status");
		lblStatus.setBounds(22, 184, 46, 14);
		contentPane.add(lblStatus);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Ativo", "Desativado" }));
		comboBox.setToolTipText("");
		comboBox.setBounds(123, 181, 105, 20);
		contentPane.add(comboBox);

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
		dateChooser.setBounds(123, 151, 105, 20);
		dateChooser.setEnabled(false);
		;
		contentPane.add(dateChooser);

		JCheckBox chckbxSemCod = new JCheckBox("Produto sem c\u00F3digo");
		chckbxSemCod.setBounds(253, 92, 148, 23);
		contentPane.add(chckbxSemCod);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					conn.setAutoCommit(false);
					mercadoria = new Mercadoria();
					boolean resp = (comboBox.getSelectedItem().equals("Ativo")) ? true : false;
					boolean selecionado = (chckbxSemCod.isSelected()) ? false : true;

					java.util.Date utilDate = dateChooser.getDate();
					java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

					mercadoria.setCodigo(txtCodigoDeBarras.getText());
					mercadoria.setDescricao(txtDesc.getText());
					mercadoria.setUnidade(txtUni.getText());
					mercadoria.setPreco(Double.parseDouble(txtValor.getText()));
					mercadoria.setDataAtualizacao(sqlDate);
					mercadoria.setAtivo(resp);
					mercadoria.setCodigoExiste(selecionado);

					mercadoria.carregar(conn);

					if (mercadoria.getId() != -1) {

						JOptionPane.showMessageDialog(null, "O cadastro já existe");
						txtCodigoDeBarras.setText("");
						txtDesc.setText("");
						txtUni.setText("");
						txtValor.setText("");
						txtCodigoDeBarras.requestFocus();

					} else {
						SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
						String dataString = formato.format(utilDate);

						int confirma = JOptionPane.showConfirmDialog(null,
								"Os dados estão corretos:\n\nCodigo de barras: " + txtCodigoDeBarras.getText()
										+ "\nDescrição: " + txtDesc.getText() + "\nUnidade: " + txtUni.getText()
										+ "\nPreço: R$ " + txtValor.getText() + "\nData: " + dataString + "\nStatus: "
										+ comboBox.getSelectedItem(),
								"Confirme", JOptionPane.YES_NO_OPTION);

						if (confirma == 0) {

							mercadoria.inserir(conn);
							conn.commit();

							if (!selecionado) {
								telaPrincipal.atualizarMaisProdutos();
								JOptionPane.showMessageDialog(null, "Cadastro efetuado");

								dispose();
							}

							else {
								dispose();
							}

						}

					}

				} catch (SQLException erro) {
					JOptionPane.showMessageDialog(null, erro);
					if (conn != null) {
						try {
							conn.rollback();
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, e1);
						}
					}
				} finally {
					if (conn != null) {
						try {
							conn.close();
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, e1);
						}
					}
				}
			}

		});
		btnCadastrar.setBounds(353, 201, 105, 23);
		contentPane.add(btnCadastrar);

		getRootPane().setDefaultButton(btnCadastrar);

	}
}

package ExpoUSJT;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class MaisOpcoes extends TelaPrincipal {

	private JPanel contentPane;
	private static Timer timer;
	private String leitura = "";
	private AWTEventListener listener;
	private int IdPedido;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MaisOpcoes frame = new MaisOpcoes(0, new TelaPrincipal());
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

	public MaisOpcoes(int idPedido, TelaPrincipal telaPrincipal) {

		this.IdPedido = idPedido;

		setTitle("Mais Opções");

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
		setBounds(100, 100, 440, 611);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnCadastrar = new JButton("");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String codigo = "";
				JFrame cadastro = new Cadastro(codigo, false, telaPrincipal);
				cadastro.setVisible(true);
				Toolkit.getDefaultToolkit().removeAWTEventListener(listener);
				dispose();

			}
		});
		btnCadastrar.setIcon(new ImageIcon(MaisOpcoes.class.getResource("/ImageLib/CADASTRO (99999).jpg")));
		btnCadastrar.setBounds(21, 22, 171, 90);
		contentPane.add(btnCadastrar);

		JButton btnDesativar = new JButton("");
		btnDesativar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame desativa = new DesativaProduto();
				desativa.setVisible(true);
				Toolkit.getDefaultToolkit().removeAWTEventListener(listener);
				dispose();
			}
		});
		btnDesativar.setIcon(new ImageIcon(MaisOpcoes.class.getResource("/ImageLib/DESATIVAR PRODUTO (88888).jpg")));
		btnDesativar.setBounds(21, 137, 171, 86);
		contentPane.add(btnDesativar);

		JButton btnCancelarPedido = new JButton("");
		btnCancelarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame cancelapedido = new CancelaPedido(idPedido, telaPrincipal);
				cancelapedido.setVisible(true);
				Toolkit.getDefaultToolkit().removeAWTEventListener(listener);
				dispose();
			}
		});
		btnCancelarPedido.setIcon(new ImageIcon(MaisOpcoes.class.getResource("/ImageLib/CANCELAR COMPRA (77777).jpg")));
		btnCancelarPedido.setBounds(21, 248, 171, 86);
		contentPane.add(btnCancelarPedido);

		JButton btnAlterarPreco = new JButton("");
		btnAlterarPreco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame alterar = new AlteraPreco();
				alterar.setVisible(true);
				Toolkit.getDefaultToolkit().removeAWTEventListener(listener);
				dispose();
			}
		});
		btnAlterarPreco.setIcon(new ImageIcon(MaisOpcoes.class.getResource("/ImageLib/ALTERAR PREÇO (66666).jpg")));
		btnAlterarPreco.setBounds(21, 358, 171, 83);
		contentPane.add(btnAlterarPreco);

		JLabel lblCadastrar = new JLabel("Cadastrar");
		lblCadastrar.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCadastrar.setBounds(250, 52, 133, 29);
		contentPane.add(lblCadastrar);

		JLabel lblDesativar = new JLabel("Desativar");
		lblDesativar.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDesativar.setBounds(250, 165, 133, 29);
		contentPane.add(lblDesativar);

		JLabel lblCancelarPedido = new JLabel("Cancelar Compra");
		lblCancelarPedido.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCancelarPedido.setBounds(250, 269, 133, 29);
		contentPane.add(lblCancelarPedido);

		JLabel lblAlterarPreco = new JLabel("Alterar Pre\u00E7o");
		lblAlterarPreco.setFont(new Font("Arial", Font.PLAIN, 12));
		lblAlterarPreco.setBounds(250, 385, 133, 29);
		contentPane.add(lblAlterarPreco);

		JLabel lblCode = new JLabel("");
		lblCode.setBounds(299, 22, 46, 14);
		contentPane.add(lblCode);

		JButton btnExcel = new JButton("");
		btnExcel.setIcon(new ImageIcon(MaisOpcoes.class.getResource("/ImageLib/EXPORTAR EXCEL (55555).jpg")));
		btnExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame excel = new ExportacaoExcel();
				excel.setVisible(true);
				Toolkit.getDefaultToolkit().removeAWTEventListener(listener);
				dispose();
			}
		});
		btnExcel.setBounds(21, 458, 171, 82);
		contentPane.add(btnExcel);

		JLabel lblNewLabel = new JLabel("Gerar Arquivo");
		lblNewLabel.setBounds(250, 496, 100, 14);
		contentPane.add(lblNewLabel);

		listener = new AWTEventListener() {
			public void eventDispatched(AWTEvent event) {
				try {
					KeyEvent evt = (KeyEvent) event;
					if (evt.getID() == KeyEvent.KEY_PRESSED) {
						System.out.println("Tecla: " + evt.getKeyChar());
						leitura += evt.getKeyChar();
						if (leitura.length() == 2) {
					    	  
							if (leitura.equals("99999")) {
								System.out.println("Cadastra");
								btnCadastrar.doClick();
							} else if (leitura.equals("88888")) {
								System.out.println("Desativa");
								btnDesativar.doClick();
							} else if (leitura.equals("77777")) {
								System.out.println("Cancela produto");
								btnCancelarPedido.doClick();
							} else if (leitura.equals("66666")) {
								System.out.println("Altera preço");
								btnAlterarPreco.doClick();
							} else if (leitura.equals("55555")) {
								System.out.println("Gerar XLS");
								btnExcel.doClick();
							}
							leitura = "";
						}

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		Toolkit.getDefaultToolkit().addAWTEventListener(listener, AWTEvent.KEY_EVENT_MASK);
	}
}

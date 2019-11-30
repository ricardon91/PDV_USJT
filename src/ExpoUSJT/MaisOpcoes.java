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
		setBounds(100, 100, 374, 446);
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
		btnCadastrar.setIcon(new ImageIcon(MaisOpcoes.class.getResource("/ImageLib/Cadastrar.jpg")));
		btnCadastrar.setBounds(21, 22, 133, 56);
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
		btnDesativar.setIcon(new ImageIcon(MaisOpcoes.class.getResource("/ImageLib/Desativar.jpg")));
		btnDesativar.setBounds(21, 103, 133, 56);
		contentPane.add(btnDesativar);

		JButton btnCancelarPedido = new JButton("");
		btnCancelarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame cancelapedido = new CancelaPedido(idPedido);
				cancelapedido.setVisible(true);
				Toolkit.getDefaultToolkit().removeAWTEventListener(listener);
				dispose();
			}
		});
		btnCancelarPedido.setIcon(new ImageIcon(MaisOpcoes.class.getResource("/ImageLib/CancelarProduto.jpg")));
		btnCancelarPedido.setBounds(21, 183, 133, 56);
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
		btnAlterarPreco.setIcon(new ImageIcon(MaisOpcoes.class.getResource("/ImageLib/AlterarPreco.jpg")));
		btnAlterarPreco.setBounds(21, 262, 133, 56);
		contentPane.add(btnAlterarPreco);

		JLabel lblCadastrar = new JLabel("Cadastrar");
		lblCadastrar.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCadastrar.setBounds(185, 22, 133, 29);
		contentPane.add(lblCadastrar);

		JLabel lblDesativar = new JLabel("Desativar");
		lblDesativar.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDesativar.setBounds(185, 103, 133, 29);
		contentPane.add(lblDesativar);

		JLabel lblCancelarPedido = new JLabel("Cancelar Compra");
		lblCancelarPedido.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCancelarPedido.setBounds(185, 183, 133, 29);
		contentPane.add(lblCancelarPedido);

		JLabel lblAlterarPreco = new JLabel("Alterar Pre\u00E7o");
		lblAlterarPreco.setFont(new Font("Arial", Font.PLAIN, 12));
		lblAlterarPreco.setBounds(185, 262, 133, 29);
		contentPane.add(lblAlterarPreco);

		JLabel lblCode = new JLabel("");
		lblCode.setBounds(299, 22, 46, 14);
		contentPane.add(lblCode);

		JButton btnExcel = new JButton("New button");
		btnExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame excel = new ExportacaoExcel();
				excel.setVisible(true);
				Toolkit.getDefaultToolkit().removeAWTEventListener(listener);
				dispose();
			}
		});
		btnExcel.setBounds(21, 349, 89, 23);
		contentPane.add(btnExcel);

		JLabel lblNewLabel = new JLabel("Gerar Arquivo");
		lblNewLabel.setBounds(185, 353, 100, 14);
		contentPane.add(lblNewLabel);

		listener = new AWTEventListener() {
			public void eventDispatched(AWTEvent event) {
				try {
					KeyEvent evt = (KeyEvent) event;
					if (evt.getID() == KeyEvent.KEY_PRESSED) {
						System.out.println("Tecla: " + evt.getKeyChar());
						leitura += evt.getKeyChar();
						if (leitura.length() == 2) {
					    	  
							if (leitura.equals("04")) {
								System.out.println("Cadastra");
								btnCadastrar.doClick();
							} else if (leitura.equals("05")) {
								System.out.println("Desativa");
								btnDesativar.doClick();
							} else if (leitura.equals("06")) {
								System.out.println("Cancela produto");
								btnCancelarPedido.doClick();
							} else if (leitura.equals("07")) {
								System.out.println("Altera preço");
								btnAlterarPreco.doClick();
							} else if (leitura.equals("08")) {
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

package ExpoUSJT;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;
//import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class TelaPrincipal extends JFrame {

	private JLabel lblMaisOpcoes;
	private JTextField txtTroco;
	private JTextField txtValorRecebido;
	private JLabel lblValorRecebido;

	private JPanel contentPane;
	private JTextField txtCodigoDeBarra;
	private JTextField txtValorUnitario;
	private JTextField txtDescricao;
	private JTextField txtTotal;
	private JLabel lblNewLabel;
	private JLabel lblCancelar;
	private JLabel lblMais;
	private JLabel lblFinalizar;
	private JLabel lblMaisProdutos;
	private JLabel label_2;
	private JLabel label;
	private JLabel label_1;
	private JLabel lblData;
	private JTextField txtProdutos;
	private String conteudo;
	private JTextArea textArea;
	private Pedido pedido;
	private Mercadoria mercadoria;
	private Venda venda;
	private static Timer timer;
	private boolean finalizado = false;
	private int unidades = 0;
	Double total;
	Double soma = 0.0;
	private JScrollPane scrollPane;
	private int idPedido;
	static TelaPrincipal frame;
	static MaisProdutos frame2;
	Connection conn;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame2 = new MaisProdutos();
					frame2.setVisible(true);
					frame = new TelaPrincipal();
					frame.setVisible(true);
					frame.setTitle("ExpoUSJT");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public TelaPrincipal() {

		conn = Conexao.getConnection();

		conteudo = "";

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 711, 618);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblDate = new JLabel("Data");
		lblDate.setBounds(10, 42, 46, 14);
		contentPane.add(lblDate);

		JLabel lblValorUnitario = new JLabel("Valor Unitario");
		lblValorUnitario.setBounds(10, 174, 81, 14);
		contentPane.add(lblValorUnitario);

		JLabel lblTotal = new JLabel("Total");
		lblTotal.setBounds(194, 309, 93, 14);
		contentPane.add(lblTotal);

		txtValorUnitario = new JTextField();
		txtValorUnitario.setBounds(101, 164, 86, 34);
		contentPane.add(txtValorUnitario);
		txtValorUnitario.setColumns(10);
		txtValorUnitario.setEditable(false);

		txtDescricao = new JTextField();
		txtDescricao.setBounds(195, 76, 376, 38);
		contentPane.add(txtDescricao);
		txtDescricao.setColumns(10);
		txtDescricao.setEditable(false);

		txtTotal = new JTextField();
		txtTotal.setBounds(195, 338, 138, 56);
		contentPane.add(txtTotal);
		txtTotal.setColumns(10);
		txtTotal.setEditable(false);

		JLabel lblDescricao = new JLabel("Descri\u00E7\u00E3o do Produto");
		lblDescricao.setBounds(193, 54, 138, 14);
		contentPane.add(lblDescricao);

		lblCancelar = new JLabel("Cancelar venda");
		lblCancelar.setBounds(245, 405, 116, 14);
		contentPane.add(lblCancelar);

		lblFinalizar = new JLabel("Finalizar Compra");
		lblFinalizar.setBounds(39, 405, 122, 14);
		contentPane.add(lblFinalizar);

		lblData = new JLabel(Util.getData());
		lblData.setBounds(51, 37, 93, 25);
		contentPane.add(lblData);

		lblMaisOpcoes = new JLabel("Mais Op\u00E7\u00F5es");
		lblMaisOpcoes.setBounds(476, 405, 84, 14);
		contentPane.add(lblMaisOpcoes);

		JLabel lblTroco = new JLabel("Troco");
		lblTroco.setBounds(505, 309, 46, 14);
		contentPane.add(lblTroco);

		txtTroco = new JTextField();
		txtTroco.setBounds(476, 338, 107, 56);
		contentPane.add(txtTroco);
		txtTroco.setColumns(10);
		txtTroco.setEditable(false);

		txtValorRecebido = new JTextField();
		txtValorRecebido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtTotal.getText();
			}
		});
		txtValorRecebido.setBounds(343, 338, 122, 56);
		contentPane.add(txtValorRecebido);
		txtValorRecebido.setColumns(10);

		lblValorRecebido = new JLabel("Valor Recebido");
		lblValorRecebido.setBounds(343, 309, 93, 14);
		contentPane.add(lblValorRecebido);

		JButton btnFinalizar = new JButton("");
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtCodigoDeBarra.setText("");

				if (!txtTotal.getText().equalsIgnoreCase("")) {

					if (!finalizado) {
						txtValorRecebido.requestFocus();
						finalizado = !finalizado;
					}

					else if (!txtValorRecebido.getText().equalsIgnoreCase("")) {

						Double recebido = Double.parseDouble(txtValorRecebido.getText());
						Double troco = recebido - total;

						if (troco < 0) {
							JOptionPane.showMessageDialog(null, "Valor recebido inferior ao Total da Compra");
							txtValorRecebido.setText("");
							txtValorRecebido.requestFocus();
						}

						else {
							txtTroco.setText("R$ " + troco.toString().format("%.2f", troco));
							int delay = 0;
							java.util.Timer timer = new java.util.Timer();
							timer.schedule(new TimerTask() {
								public void run() {

									zeraPedido();
								}

							}, delay);
						}
					}

					else {
						JOptionPane.showMessageDialog(null, "Por favor verifique o Valor a ser recebido");
					}

				}

				else {

					JOptionPane.showMessageDialog(null, "Não há nada no carrinho");
				}

			}
		});
		btnFinalizar.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/ImageLib/FINALIZAR (22222).jpg")));
		btnFinalizar.setBounds(39, 424, 177, 89);
		contentPane.add(btnFinalizar);

		JButton btnCancelar = new JButton("");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pedido == null) {
					JOptionPane.showMessageDialog(null, "Não há pedidos em aberto");
				} else {
					JFrame cancelar = new CancelaVenda(pedido.getId(), frame);
					cancelar.setVisible(true);
					txtCodigoDeBarra.setText("");

				}
			}
		});
		btnCancelar.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/ImageLib/CANCELAR VENDA (33333).jpg")));
		btnCancelar.setBounds(245, 424, 177, 89);
		contentPane.add(btnCancelar);

		JButton btnMaisOpcoes = new JButton("");
		btnMaisOpcoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pedido == null) {
					idPedido = 0;
				}

				else {
					idPedido = pedido.getId();
				}

				MaisOpcoes opcoes = new MaisOpcoes(idPedido, frame);
				opcoes.setVisible(true);
				txtCodigoDeBarra.setText("");
			}
		});
		btnMaisOpcoes.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/ImageLib/MAIS OPCOES (44444).jpg")));
		btnMaisOpcoes.setBounds(476, 424, 177, 89);
		contentPane.add(btnMaisOpcoes);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(197, 143, 376, 146);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		txtCodigoDeBarra = new JTextField();
		txtCodigoDeBarra = new JTextField();
		txtCodigoDeBarra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (txtCodigoDeBarra.getText().equalsIgnoreCase("22222")) {
					btnFinalizar.doClick();
				}

				else if (txtCodigoDeBarra.getText().equalsIgnoreCase("33333")) {
					btnCancelar.doClick();
				}

				else if (txtCodigoDeBarra.getText().equalsIgnoreCase("44444")) {
					btnMaisOpcoes.doClick();
				}

				else if (txtCodigoDeBarra.getText().equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(null, "Codigo de Barra vazio");
				}

				else {
					try {
						conn.setAutoCommit(false);
						mercadoria = new Mercadoria();

						mercadoria.setCodigo(txtCodigoDeBarra.getText());
						mercadoria.carregar(conn);
						conn.commit();

						if (mercadoria.getId() != -1) {

							if (mercadoria.isAtivo()) {
								System.out.println(mercadoria.getId());
								txtDescricao.setText(mercadoria.getDescricao());
								txtValorUnitario.setText(mercadoria.getPreco() + "");
								conteudo += geraLinhaVenda(mercadoria);
								unidades++;
								textArea.setText(conteudo);
								total = soma += Double.parseDouble(txtValorUnitario.getText());
//								txtTotal.setText("R$ " + total.toString().format("%.2f", total));
								txtCodigoDeBarra.setText("");

								if (pedido == null) {
									pedido = new Pedido(Util.getSqlDate(), unidades, total, false);
									pedido.inserir(conn);

								}

								venda = new Venda(mercadoria, pedido, 1, mercadoria.getPreco());
								venda.inserir(conn);
								venda.trazQuantidade(conn, pedido.getId());
								venda.trazTotal(conn, pedido.getId());
								total = venda.getPreco();
								txtTotal.setText("R$ " + total.toString().format("%.2f", total));

								pedido.setTotalPedido(total);
								pedido.setTotalItens(venda.getQuantidade());
								pedido.atualizar(conn, pedido.getId());

								conn.commit();

							}

							else {
								// Não esta ativo
								JOptionPane.showMessageDialog(null, "Produto não esta ativo");

							}
						} else if (mercadoria.getId() == -1) {
							String codigo = txtCodigoDeBarra.getText();
							JFrame jj = new Cadastro(codigo, true, frame);
							jj.setVisible(true);
							txtCodigoDeBarra.setText("");

						}

					}

					catch (Exception e2) {
						e2.printStackTrace();
					}

				}
			}
		});
		txtCodigoDeBarra.setBounds(39, 75, 146, 41);
		contentPane.add(txtCodigoDeBarra);
		txtCodigoDeBarra.setColumns(10);

	}

	public void zeraPedido() {
		cancelaVenda();
		pedido = null;
	}

	public void cancelaVenda() {

		txtCodigoDeBarra.requestFocus();
		txtCodigoDeBarra.setText("");
		txtDescricao.setText("");
		txtTotal.setText("");
		txtTroco.setText("");
		txtValorRecebido.setText("");
		txtValorUnitario.setText("");
		total = 0.0;
		soma = 0.0;
		conteudo = "";
		textArea.setText("");
		finalizado = !finalizado;
		unidades = 0;
	}

	public void atualizarMaisProdutos() {
		frame2.dispose();
		frame2 = new MaisProdutos();
		frame2.setVisible(true);
		txtCodigoDeBarra.requestFocus();
	}

	public String geraLinhaVenda(Mercadoria mercadoria) {
		return mercadoria.getDescricao() + " - R$ " + mercadoria.getPreco() + "\n";
	}

	public void repopulaTextAreaVenda(int idPedido) {
		List<Mercadoria> lista = venda.getMercadoriasByPedido(conn, idPedido);
		conteudo = "";
		for (Mercadoria m : lista) {
			conteudo += geraLinhaVenda(m);
		}
		textArea.setText(conteudo);
	}

}

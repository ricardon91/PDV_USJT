package ExpoUSJT;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;

public class DesativaProduto extends JFrame {

	private JPanel contentPane;
	private JTextField txtCodigo;
	private Mercadoria mercadoria;
	private static Timer timer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DesativaProduto frame = new DesativaProduto();
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
	public DesativaProduto() {

		setTitle("Desativar Produto");
		Connection conn = Conexao.getConnection();

//	    Toolkit.getDefaultToolkit().addAWTEventListener(
//	            new AWTEventListener() {
//	              @Override
//	              public void eventDispatched(AWTEvent event) {
//	                Object source = event.getSource();
//	                if (source instanceof Component) {
//	                  Component comp = (Component) source;
//	                  Window win = null;
//	                  if (comp instanceof Window) {
//	                    win = (Window) comp;
//	                  } else {
//	                    win = SwingUtilities.windowForComponent(comp);
//	                  }
//	                  if (win == win) {
//	                    timer.restart();
//	                    }
//	                }
//	              }
//	            },
//	            AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK
//	                | AWTEvent.MOUSE_MOTION_EVENT_MASK
//	                | AWTEvent.MOUSE_WHEEL_EVENT_MASK);
//
//	        timer = new Timer(10000, new ActionListener() {
//	          @Override
//	          public void actionPerformed(ActionEvent e) {
//	            dispose();
//	          }
//	        });
//	        timer.start();

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 448, 276);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("Codigo do produto");
		label.setBounds(10, 35, 119, 14);
		contentPane.add(label);

		JLabel lblProd = new JLabel("Produto");
		lblProd.setBounds(10, 75, 56, 14);
		contentPane.add(lblProd);

		JLabel lblProduto = new JLabel("");
		lblProduto.setBounds(76, 75, 228, 14);
		contentPane.add(lblProduto);

		JLabel lblStats = new JLabel("Status");
		lblStats.setBounds(10, 116, 46, 14);
		contentPane.add(lblStats);

		JLabel lblStatus = new JLabel("");
		lblStatus.setBounds(76, 116, 70, 14);
		contentPane.add(lblStatus);

		JLabel lblAtualizarStatus = new JLabel("Atualizar Status");
		lblAtualizarStatus.setBounds(10, 160, 99, 14);
		contentPane.add(lblAtualizarStatus);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Desativado", "Ativo" }));
		comboBox.setBounds(116, 157, 88, 20);
		contentPane.add(comboBox);

		JButton btnConfirma = new JButton("Confirma");
		btnConfirma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					conn.setAutoCommit(false);
					mercadoria = new Mercadoria();
					boolean resp = (comboBox.getSelectedItem().equals("Ativo")) ? true : false;

					mercadoria.setCodigo(txtCodigo.getText());
					mercadoria.carregar(conn);

					if (mercadoria.getId() != -1) {

						int confirma = JOptionPane.showConfirmDialog(null,
								"Tem certeza que deseja alterar o produto: " + lblProduto.getText(), "Confirme",
								JOptionPane.YES_NO_OPTION);

						if (confirma == 0) {
							mercadoria.setAtivo(resp);
							mercadoria.desativar(conn);
							conn.commit();

							if (resp) {
								JOptionPane.showMessageDialog(null, "Produto Ativado");
							}

							else {
								JOptionPane.showMessageDialog(null, "Produto Desativado");
							}
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
		btnConfirma.setBounds(321, 191, 89, 23);
		contentPane.add(btnConfirma);

		txtCodigo = new JTextField();
		txtCodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					conn.setAutoCommit(false);
					Mercadoria mercadoria = new Mercadoria();

					mercadoria.setCodigo(txtCodigo.getText());
					mercadoria.carregar(conn);

					if (mercadoria.getId() != -1) {

						lblProduto.setText(mercadoria.getDescricao());
						String resp = (mercadoria.isAtivo()) ? "Ativo" : "Desativado";
						lblStatus.setText(resp);
						comboBox.requestFocus();
						txtCodigo.setEditable(false);

					} else {

						JOptionPane.showMessageDialog(null, "O produto não existe");
						txtCodigo.setText("");

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
				}
//			      finally{
//			         if(conn != null){
//			            try{
//			               conn.close();
//			            } 
//			            catch(SQLException e1){
//			            	JOptionPane.showMessageDialog(null, e1 + "erro3");
//			            }
//			         }      
//			      }

			}
		});
		txtCodigo.setBounds(116, 32, 257, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);

		getRootPane().setDefaultButton(btnConfirma);

	}
}

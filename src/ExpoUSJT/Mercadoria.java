package ExpoUSJT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.swing.JOptionPane;

public class Mercadoria {
	private int id;
	private String codigo;
	private String descricao;
	private String unidade;
	private double preco;
	private Date dataAtualizacao;
	private boolean ativo;
	private String codImagem;
	private boolean codigoExiste;

	public Mercadoria() {
		super();
	}

	public Mercadoria(String codigo, String descricao, String unidade, double preco, Date dataAtualizacao,
			boolean ativo, String codImagem, boolean codigoExiste) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
		this.unidade = unidade;
		this.preco = preco;
		this.dataAtualizacao = dataAtualizacao;
		this.ativo = ativo;
		this.codImagem = codImagem;
		this.codigoExiste = codigoExiste;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getCodImagem() {
		return codImagem;
	}

	public void setCodImagem(String codImagem) {
		this.codImagem = codImagem;
	}

	public boolean isCodigoExiste() {
		return codigoExiste;
	}

	public void setCodigoExiste(boolean codigoExiste) {
		this.codigoExiste = codigoExiste;
	}

	public void inserir(Connection conn) {

		String comando = "insert into mercadoria (codigo, descricao, unidade, preco, dataAtualizacao, ativo, codigoExiste) values(?,?,?,?,?,?,?)";
		try (PreparedStatement ps = conn.prepareStatement(comando, Statement.RETURN_GENERATED_KEYS)) {

			System.out.println(codigo);
			ps.setString(1, codigo);
			ps.setString(2, descricao);
			ps.setString(3, unidade);
			ps.setDouble(4, preco);
			ps.setDate(5, dataAtualizacao);
			ps.setBoolean(6, ativo);
			ps.setBoolean(7, codigoExiste);

			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void carregar(Connection conn) {
		String comando = "select * FROM mercadoria WHERE codigo = ?";
		try (PreparedStatement pst = conn.prepareStatement(comando);) {
			pst.setString(1, codigo);

			try (ResultSet rs = pst.executeQuery();) {
				if (rs.next()) {
					id = rs.getInt("idMercadoria");
					codigo = rs.getString("codigo");
					descricao = rs.getString("descricao");
					unidade = rs.getString("unidade");
					preco = rs.getDouble("preco");
					dataAtualizacao = rs.getDate("dataAtualizacao");
					ativo = rs.getBoolean("ativo");
					codImagem = rs.getString("codigoImagem");
					codigoExiste = rs.getBoolean("codigoExiste");
					descricao = rs.getString("descricao");
					preco = rs.getDouble("preco");

					pst.execute();
				} else {
					id = -1;
//					codigo = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Mercadoria> semCodigo(Connection conn) {
		String comando = "select * from mercadoria where codigoExiste=0 limit 12";
		Mercadoria mercadoria = null;
		List<Mercadoria> lista = new ArrayList<Mercadoria>();
		try (PreparedStatement pst = conn.prepareStatement(comando);) {
			try (ResultSet rs = pst.executeQuery();) {
				while (rs.next()) {
					mercadoria = new Mercadoria(rs.getString("codigo"), rs.getString("descricao"),
							rs.getString("unidade"), rs.getDouble("preco"), rs.getDate("dataAtualizacao"),
							rs.getBoolean("ativo"), rs.getString("codigoImagem"), rs.getBoolean("codigoExiste"));
					mercadoria.setId(rs.getInt("idMercadoria"));
					lista.add(mercadoria);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public void desativar(Connection conn) {
		String comando = "UPDATE mercadoria SET ativo=? WHERE codigo=?";
		try (PreparedStatement pst = conn.prepareStatement(comando);) {

			pst.setBoolean(1, ativo);
			pst.setString(2, codigo);

			pst.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void atualizar(Connection conn) {
		String comando = "UPDATE mercadoria SET preco=? WHERE codigo=?";
		try (PreparedStatement pst = conn.prepareStatement(comando);) {

			pst.setDouble(1, preco);
			pst.setString(2, codigo);

			pst.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	public void atualizar(Connection conn) {
//		String comando = "update mercadoria set codigo=?, descricao=?, unidade=?, preco=? where idMercadoria=?";
//		try (PreparedStatement pst = conn.prepareStatement(comando);) {
//
//			int var0 = Integer.parseInt(JOptionPane.showInputDialog("Digite o id"));
//			String var1 = JOptionPane.showInputDialog("Digite o codigo");
//			String var2 = JOptionPane.showInputDialog("Digite o descricao");
//			String var3 = JOptionPane.showInputDialog("Digite o unidade");
//			Double var4 = Double.parseDouble(JOptionPane.showInputDialog("Digite o preco"));
//
//			pst.setString(1, var1);
//			pst.setString(2, var2);
//			pst.setString(3, var3);
//			pst.setDouble(4, var4);
//			pst.setInt(5, var0);
//
//			pst.execute();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

}

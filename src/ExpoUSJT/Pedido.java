package ExpoUSJT;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
	private int id;
	private Date data;
	private int totalItens;
	private double totalPedido;
	private boolean cancelado;

	@Override
	public String toString() {
		return "Pedido [data=" + data + ", totalItens=" + totalItens + ", totalPedido=" + totalPedido + ", cancelado="
				+ cancelado + "]";
	}

	public Pedido() {
		super();
	}

	public Pedido(Date data, int totalItens, double totalPedido, boolean cancelado) {
		super();
		this.data = data;
		this.totalItens = totalItens;
		this.totalPedido = totalPedido;
		this.cancelado = cancelado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getTotalItens() {
		return totalItens;
	}

	public void setTotalItens(int totalItens) {
		this.totalItens = totalItens;
	}

	public Double getTotalPedido() {
		return totalPedido;
	}

	public void setTotalPedido(Double totalPedido) {
		this.totalPedido = totalPedido;
	}

	public boolean isCancelado() {
		return cancelado;
	}

	public void setCancelado(boolean cancelado) {
		this.cancelado = cancelado;
	}

	public void inserir(Connection conn) {

		String comando = "INSERT INTO pedido (data, totalItens, totalPedido, cancelado) VALUES(?,?,?,?)";
		try (PreparedStatement ps = conn.prepareStatement(comando, Statement.RETURN_GENERATED_KEYS)) {

			ps.setDate(1, data);
			ps.setInt(2, totalItens);
			ps.setDouble(3, totalPedido);
			ps.setBoolean(4, cancelado);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void carregar(Connection conn) {
		String comando = "select data, totalItens, totalPedido, cancelado from pedido where idPedido=?";
		try (PreparedStatement pst = conn.prepareStatement(comando);) {

			pst.setInt(1, getId());

			try (ResultSet rs = pst.executeQuery();) {
				if (rs.next()) {
					data = rs.getDate("data");
					totalItens = rs.getInt("totalItens");
					totalPedido = rs.getDouble("totalPedido");
					cancelado = rs.getBoolean("cancelado");
				} else {
					id = -1;
					// codigo = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void exclusaoLogica(Connection conn, int idPedido) {
		String comando = "update pedido set cancelado = ? where idPedido=?";
		try (PreparedStatement pst = conn.prepareStatement(comando);) {

			pst.setBoolean(1, true);
			pst.setInt(2, idPedido);
			pst.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void atualizar(Connection conn, int idPedido) {
		String comando = "UPDATE pedido SET totalItens = ?, totalPedido = ? WHERE idPedido = ?";
		try (PreparedStatement pst = conn.prepareStatement(comando);) {

			pst.setInt(1, totalItens);
			pst.setDouble(2, totalPedido);
			pst.setDouble(3, idPedido);

			pst.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deletarPedido(Connection conn, int idPedido) {
		String comando = "DELETE FROM pedido WHERE idpedido = ?";
		try (PreparedStatement pst = conn.prepareStatement(comando);) {
			pst.setInt(1, idPedido);
			pst.execute();
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Exportacao> gerarExportacao(Connection conn, java.sql.Date data) {

		Exportacao e;
		List<Exportacao> lista = new ArrayList<>();

		String comando = "select p.*,v.*,m.* from pedido p, venda v, mercadoria m where  p.idpedido = v.pedido_idpedido and v.mercadoria_idMercadoria = m.idMercadoria and p.data > ?";
		try (PreparedStatement pst = conn.prepareStatement(comando);) {

			pst.setDate(1, data);

			try (ResultSet rs = pst.executeQuery();) {
				while (rs.next()) {
					// data = rs.getDate("data");
					e = new Exportacao();
					e.setIdPedido(rs.getInt("idPedido"));
					e.setData(rs.getDate("data"));
					e.setTotalItens(rs.getInt("totalItens"));
					e.setTotalPedido(rs.getDouble("totalPedido"));
					e.setCancelado(rs.getBoolean("cancelado"));
					e.setQuantidade(rs.getInt("quantidade"));
					e.setPreco(rs.getDouble("preco"));
					e.setIdMercadoria(rs.getInt("idMercadoria"));
					e.setCodigo(rs.getString("codigo"));
					e.setDescricao(rs.getString("descricao"));
					e.setUnidade(rs.getString("unidade"));
					e.setDataAtualizacao(rs.getDate("dataAtualizacao"));
					e.setAtivo(rs.getBoolean("ativo"));
					e.setCodigoExiste(rs.getBoolean("codigoExiste"));
					lista.add(e);
				}
			} catch (SQLException c) {
				c.printStackTrace();
			}

		} catch (SQLException d) {
			d.printStackTrace();
		}
		return lista;
	}

}

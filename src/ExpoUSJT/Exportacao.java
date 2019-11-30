package ExpoUSJT;

public class Exportacao {

	private int idPedido;
	private java.sql.Date data;
	private int totalItens;
	private double totalPedido;
	private boolean cancelado;
	private int idvenda;
	private int quantidade;
	private double preco;
	private int idMercadoria;
	private String codigo;
	private String descricao;
	private String unidade;
	private java.sql.Date dataAtualizacao;
	private boolean ativo;
	private boolean codigoExiste;

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public java.sql.Date getData() {
		return data;
	}

	public void setData(java.sql.Date data) {
		this.data = data;
	}

	public int getTotalItens() {
		return totalItens;
	}

	public void setTotalItens(int totalItens) {
		this.totalItens = totalItens;
	}

	public double getTotalPedido() {
		return totalPedido;
	}

	public void setTotalPedido(double totalPedido) {
		this.totalPedido = totalPedido;
	}

	public boolean isCancelado() {
		return cancelado;
	}

	public void setCancelado(boolean cancelado) {
		this.cancelado = cancelado;
	}

	public int getIdvenda() {
		return idvenda;
	}

	public void setIdvenda(int idvenda) {
		this.idvenda = idvenda;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getIdMercadoria() {
		return idMercadoria;
	}

	public void setIdMercadoria(int idMercadoria) {
		this.idMercadoria = idMercadoria;
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

	public java.sql.Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(java.sql.Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public boolean isCodigoExiste() {
		return codigoExiste;
	}

	public void setCodigoExiste(boolean codigoExiste) {
		this.codigoExiste = codigoExiste;
	}

}

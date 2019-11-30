package ExpoUSJT;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExportacaoExcel extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private String data;
	private List<Exportacao> lista;
	private Connection conn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExportacaoExcel frame = new ExportacaoExcel();
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
	
	
	public ExportacaoExcel() {
		
		conn = Conexao.getConnection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblExportaoDeDados = new JLabel("Exporta\u00E7\u00E3o de Dados para Arquivo Excel");
		lblExportaoDeDados.setBounds(66, 12, 362, 15);
		contentPane.add(lblExportaoDeDados);

		JButton btnExportar = new JButton("Exportar");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Ao clicar no botão chama método para exportar a planilha
				data = textField_1.getText();
				Pedido pedido = new Pedido();
				lista = pedido.gerarExportacao(conn, java.sql.Date.valueOf( data ));
				System.out.println("Tamanho da lista: "+lista.size());	
				gerarArquivoExcel();
				dispose();
			}
		});
		btnExportar.setBounds(297, 56, 117, 25);
		contentPane.add(btnExportar);

		JLabel lblDestino = new JLabel("Destino:");
		lblDestino.setBounds(12, 151, 70, 15);
		contentPane.add(lblDestino);

		textField = new JTextField();
		textField.setBounds(83, 149, 331, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(161, 58, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblDataaaaammdd = new JLabel("Data: (AAAA-MM-DD):");
		lblDataaaaammdd.setBounds(12, 61, 124, 14);
		contentPane.add(lblDataaaaammdd);
	}

	private File getPastaParaSalvarArquivo() {

		// Exibe o file chooser e retorna um FILE correspondente a uma pasta de
		// diretório
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Selecione a pasta para salvar seu arquivo: ");
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnValue = jfc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION && jfc.getSelectedFile().isDirectory()) {

			return jfc.getSelectedFile();

		} else {
			return new File(".");
		}

	}

	private void gerarArquivoExcel() {

		// chama o File Chooser para poder escohar a pasta para guardar o arquivo
		File currDir = getPastaParaSalvarArquivo();
		String path = currDir.getAbsolutePath();
		// Adiciona ao nome da pasta o nome do arquivo que desejamos utilizar
		String fileLocation = path.substring(0, path.length()) + "/relatorio.xls";

		// mosta o caminho que exportamos na tela
		textField.setText(fileLocation);

		// Criação do arquivo excel
		try {

			// Diz pro excel que estamos usando portguês
			WorkbookSettings ws = new WorkbookSettings();
			ws.setLocale(new Locale("pt", "BR"));
			// Cria uma planilha
			WritableWorkbook workbook = Workbook.createWorkbook(new File(fileLocation), ws);
			// Cria uma pasta dentro da planilha
			WritableSheet sheet = workbook.createSheet("Pasta 1", 0);

			// Cria um cabeçario para a Planilha - Pedido
			WritableCellFormat headerFormat = new WritableCellFormat();
			WritableFont font = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
			headerFormat.setFont(font);
			headerFormat.setBackground(Colour.LIGHT_BLUE);
			headerFormat.setWrap(true);
			

//			private int idPedido;
//			private java.sql.Date data;
//			private int totalItens;
//			private double totalPedido;
//			private boolean cancelado;
//			private int idvenda;
//			private int quantidade;
//			private double preco;
//			private int idMercadoria;
//			private String codigo;
//			private String descricao;
//			private String unidade;
//			private java.sql.Date dataAtualizacao;
//			private boolean ativo;
//			private boolean codigoExiste;

			Label headerLabel = new Label(0, 0, "ID Pedido", headerFormat);
			sheet.setColumnView(0, 60);
			sheet.addCell(headerLabel);

			headerLabel = new Label(1, 0, "Data", headerFormat);
			sheet.setColumnView(0, 60);
			sheet.addCell(headerLabel);

			headerLabel = new Label(2, 0, "Total de Itens", headerFormat);
			sheet.setColumnView(0, 60);
			sheet.addCell(headerLabel);

			headerLabel = new Label(3, 0, "Total do Pedido", headerFormat);
			sheet.setColumnView(0, 60);
			sheet.addCell(headerLabel);

			headerLabel = new Label(4, 0, "Cancelado", headerFormat);
			sheet.setColumnView(0, 60);
			sheet.addCell(headerLabel);

			headerLabel = new Label(5, 0, "ID Venda", headerFormat);
			sheet.setColumnView(0, 60);
			sheet.addCell(headerLabel);

			headerLabel = new Label(6, 0, "Quantidade", headerFormat);
			sheet.setColumnView(0, 60);
			sheet.addCell(headerLabel);

			headerLabel = new Label(7, 0, "Preço", headerFormat);
			sheet.setColumnView(0, 60);
			sheet.addCell(headerLabel);

			headerLabel = new Label(8, 0, "ID Mercadoria", headerFormat);
			sheet.setColumnView(0, 60);
			sheet.addCell(headerLabel);

			headerLabel = new Label(9, 0, "Código", headerFormat);
			sheet.setColumnView(0, 60);
			sheet.addCell(headerLabel);

			headerLabel = new Label(10, 0, "Descrição", headerFormat);
			sheet.setColumnView(0, 60);
			sheet.addCell(headerLabel);

			headerLabel = new Label(11, 0, "Unidade", headerFormat);
			sheet.setColumnView(0, 60);
			sheet.addCell(headerLabel);

			headerLabel = new Label(12, 0, "Data de Atualização", headerFormat);
			sheet.setColumnView(0, 60);
			sheet.addCell(headerLabel);

			headerLabel = new Label(13, 0, "Ativo", headerFormat);
			sheet.setColumnView(0, 60);
			sheet.addCell(headerLabel);

			headerLabel = new Label(14, 0, "Código Existe", headerFormat);
			sheet.setColumnView(0, 60);
			sheet.addCell(headerLabel);

			// Cria as celulas com o conteudo
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat.setWrap(true);

			int linha = 1;
			for (Exportacao ex : lista) {

				jxl.write.Number cellNumber0 = new jxl.write.Number(0, linha, ex.getIdPedido(), cellFormat);
				sheet.addCell(cellNumber0);

				Label cellLabel1 = new Label(1, linha, ex.getData().toString(), cellFormat);
				sheet.addCell(cellLabel1);

				jxl.write.Number cellNumber2 = new jxl.write.Number(2, linha, ex.getTotalItens(), cellFormat);
				sheet.addCell(cellNumber2);

				jxl.write.Number cellNumber3 = new jxl.write.Number(3, linha, ex.getTotalPedido(), cellFormat);
				sheet.addCell(cellNumber3);

				Label cellLabel4 = new Label(4, linha, "" + ex.isCancelado(), cellFormat);
				sheet.addCell(cellLabel4);

				jxl.write.Number cellNumber5 = new jxl.write.Number(5, linha, ex.getIdvenda(), cellFormat);
				sheet.addCell(cellNumber5);

				jxl.write.Number cellNumber6 = new jxl.write.Number(6, linha, ex.getQuantidade(), cellFormat);
				sheet.addCell(cellNumber6);

				jxl.write.Number cellNumber7 = new jxl.write.Number(7, linha, ex.getPreco(), cellFormat);
				sheet.addCell(cellNumber7);

				jxl.write.Number cellNumber8 = new jxl.write.Number(8, linha, ex.getIdMercadoria(), cellFormat);
				sheet.addCell(cellNumber8);

				Label cellLabel9 = new Label(9, linha, ex.getCodigo(), cellFormat);
				sheet.addCell(cellLabel9);

				Label cellLabel10 = new Label(10, linha, ex.getDescricao(), cellFormat);
				sheet.addCell(cellLabel10);

				Label cellLabel11 = new Label(11, linha, ex.getUnidade(), cellFormat);
				sheet.addCell(cellLabel11);

				Label cellLabel12 = new Label(12, linha, "" + ex.getDataAtualizacao(), cellFormat);
				sheet.addCell(cellLabel12);

				Label cellLabel13 = new Label(13, linha, "" + ex.isAtivo(), cellFormat);
				sheet.addCell(cellLabel13);

				Label cellLabel14 = new Label(14, linha, "" + ex.isCodigoExiste(), cellFormat);
				sheet.addCell(cellLabel14);

				linha++;
			}

			// Não esquecer de escrever e fechar a planilha
			workbook.write();
			workbook.close();

		} catch (IOException e1) {
			// Imprime erro se não conseguir achar o arquivo ou pasta para gravar
			e1.printStackTrace();
		} catch (WriteException e) {
			// exibe erro se acontecer algum tipo de celula de planilha inválida
			e.printStackTrace();
		}

	}
}

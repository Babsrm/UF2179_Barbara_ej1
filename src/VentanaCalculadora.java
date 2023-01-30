import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaCalculadora extends JFrame {

	private JPanel contentPane;
	private JTextField txtCiuOr;
	private JTextField txtCiuDes;
	private final ButtonGroup buttonGroupCiuOr = new ButtonGroup();
	private final ButtonGroup buttonGroupCiuDes = new ButtonGroup();
	private JRadioButton rdbOrNacional;
	private JRadioButton rdbOrExtranjero;
	private JRadioButton rdbDesNacional;
	private JRadioButton rdbDesExtranjero;
	private JComboBox comboBoxPaq;
	private JSpinner spinnerKg;
	private JButton btnCalcular;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCalculadora frame = new VentanaCalculadora();
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
	public VentanaCalculadora() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setFont(new Font("Tahoma", Font.BOLD, 18));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][][71.00][][][grow][-1.00][36.00,trailing]", "[][][][][][][][]"));

		JLabel lblTitulo = new JLabel("Calculadora de Envios");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblTitulo.setOpaque(true);
		lblTitulo.setForeground(new Color(128, 255, 255));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitulo.setBackground(new Color(64, 0, 128));
		contentPane.add(lblTitulo, "cell 0 0 8 1,growx");

		JLabel lblCiuOr = new JLabel("Ciudad Origen:");
		contentPane.add(lblCiuOr, "cell 2 1,alignx left");

		txtCiuOr = new JTextField();
		contentPane.add(txtCiuOr, "cell 3 1 3 1,growx");
		txtCiuOr.setColumns(10);

		rdbOrNacional = new JRadioButton("Nacional");
		rdbOrNacional.setActionCommand("Nacional");
		rdbOrNacional.setSelected(true);
		buttonGroupCiuOr.add(rdbOrNacional);
		contentPane.add(rdbOrNacional, "cell 3 2 2 1");

		rdbOrExtranjero = new JRadioButton("Extranjero");
		rdbOrExtranjero.setActionCommand("Extranjero");
		buttonGroupCiuOr.add(rdbOrExtranjero);
		contentPane.add(rdbOrExtranjero, "cell 5 2");

		JLabel lblCiuDes = new JLabel("Ciudad Destino:");
		contentPane.add(lblCiuDes, "cell 2 3,alignx left");

		txtCiuDes = new JTextField();
		contentPane.add(txtCiuDes, "cell 3 3 3 1,growx");
		txtCiuDes.setColumns(10);

		rdbDesNacional = new JRadioButton("Nacional");
		rdbDesNacional.setActionCommand("Nacional");
		rdbDesNacional.setSelected(true);
		buttonGroupCiuDes.add(rdbDesNacional);
		contentPane.add(rdbDesNacional, "cell 3 4 2 1");

		rdbDesExtranjero = new JRadioButton("Extranjero");
		rdbDesExtranjero.setActionCommand("Extranjero");
		buttonGroupCiuDes.add(rdbDesExtranjero);
		contentPane.add(rdbDesExtranjero, "cell 5 4");

		JLabel lblTipoEnv = new JLabel("Tipo de envío:");
		contentPane.add(lblTipoEnv, "cell 2 5,alignx left");

		comboBoxPaq = new JComboBox();
		comboBoxPaq.setModel(new DefaultComboBoxModel(
				new String[] { "Paq10 - Antes de las 10h", "Paq14 - Antes de las 14h", "Paq24 - Al día siguiente" }));
		contentPane.add(comboBoxPaq, "cell 3 5 3 1,growx");

		JLabel lblPeso = new JLabel("Peso:");
		contentPane.add(lblPeso, "cell 2 6");

		spinnerKg = new JSpinner();
		spinnerKg.setModel(new SpinnerNumberModel(1, 1, 40, 1));
		contentPane.add(spinnerKg, "flowx,cell 3 6");

		JLabel lblKg = new JLabel("Kg");
		contentPane.add(lblKg, "cell 4 6");

		btnCalcular = new JButton("Calcular Precio");
		btnCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ciuOr = txtCiuOr.getText();
				String ciuDes = txtCiuDes.getText();
				if (ciuOr == null || ciuOr.isBlank() || ciuDes == null || ciuDes.isBlank()) {
					JOptionPane.showMessageDialog(null, "Introduzca las ciudades de origen y destino.", "Faltan datos",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				calculoImporte();
			}
		});
		contentPane.add(btnCalcular, "cell 1 7 6 1,alignx center");
	}

	protected void calculoImporte() {
		String ciuOrigen = txtCiuOr.getText();
		String tipoOrigen = buttonGroupCiuOr.getSelection().getActionCommand();
		String ciuDestino = txtCiuDes.getText();
		String tipoDestino = buttonGroupCiuDes.getSelection().getActionCommand();
		String tipoEnvio = (String) comboBoxPaq.getSelectedItem();
		int pesoPaq = (int) spinnerKg.getValue();
		double calcTipo = 0;
		double calcTipoPaq = 0;
		double calcPeso = Math.floor((pesoPaq / 10));
		double resultado = 0;

		switch (tipoOrigen) {
		case "Nacional": {
			switch (tipoDestino) {
			case "Nacional":
				calcTipo = 4;
				break;
			case "Extranjero":
				calcTipo = 7;
				break;
			}
		}
			break;
		case "Extranjero": {
			calcTipo = 7;
			break;
		}
		}

		if (tipoEnvio == "Paq10 - Antes de las 10h") {
			calcTipoPaq = 5;
		} else if (tipoEnvio == "Paq14 - Antes de las 14h") {
			calcTipoPaq = 2;
		} else {
			calcTipoPaq = 0;
		}

		resultado = calcTipo + calcTipoPaq + calcPeso * 3.5;
		JOptionPane
				.showMessageDialog(this,
						"Origen: " + ciuOrigen + "\nDestino: " + ciuDestino + "\nTipo: " + tipoEnvio + "\nPeso: "
								+ pesoPaq + "Kg \nImporte: " + resultado + " €",
						"Cálculo", JOptionPane.INFORMATION_MESSAGE);
	}
}

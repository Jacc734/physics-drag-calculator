package physics;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class PhysicsGUI extends JFrame {

	// WINDOWS CODING
	private static final long serialVersionUID = 1L;
	private static JPanel window = new JPanel();
	private static JPanel graphs = new JPanel();
	private static ChartPanel positionGraph = new ChartPanel(null);
	private static ChartPanel velocityGraph = new ChartPanel(null);
	private static ChartPanel accelerationGraph = new ChartPanel(null);

	private static JPanel option = new JPanel();
	private static JTabbedPane tabular = new JTabbedPane();

	// BOOLEAN VALUES to GENERATE GRAPHS
	JCheckBox gp = new JCheckBox("Graph Position", false);
	JCheckBox gv = new JCheckBox("Graph Velocity", true);
	JCheckBox ga = new JCheckBox("Graph Acceleration", false);

//	JCheckBox g1 = new JCheckBox("Graph 1", true);
//	JCheckBox g2 = new JCheckBox("Graph 2", false);
//	JCheckBox g3 = new JCheckBox("Graph 3", false);

	JRadioButton xy = new JRadioButton("Format: (x,y)", false);
	JRadioButton xyt = new JRadioButton("Format: (t,xy)", false);
	JRadioButton dt = new JRadioButton("Format: (t,y)", true);

	JRadioButton pos = new JRadioButton("Use: Position", false);
	JRadioButton tim = new JRadioButton("Use: Time", true);

	private static JSpinner position = new JSpinner(new SpinnerNumberModel(50, -10000, 10000, 1));
	private static JSpinner vY = new JSpinner(new SpinnerNumberModel(2, -1000, 1000, 1));
	private static JSpinner vX = new JSpinner(new SpinnerNumberModel(2, -1000, 1000, 1));
	private static JSpinner acceleration = new JSpinner(new SpinnerNumberModel(-10, -1000, 1000, 1));
	private static JSpinner dragCo = new JSpinner(new SpinnerNumberModel(3, 0, 1000, 1));
	private static JSpinner objectMass = new JSpinner(new SpinnerNumberModel(145, 1, 10000, 1));//145grams = baseball
	private static JSpinner objectCA = new JSpinner(new SpinnerNumberModel(7, 1, 10000, 1)); //TODO this was a radius
	private static JSpinner points = new JSpinner(new SpinnerNumberModel(30, 1, 200, 1));
	private static JSpinner time = new JSpinner(new SpinnerNumberModel(10, 1, 200, 1));


	// MAIN
	public static void main(String args[]) {
		new PhysicsGUI();
	}

	// THE GUI POWERHOUSE
	public PhysicsGUI() {

		// ------------Main JPanel------------------------------
		window.setBorder(BorderFactory.createEtchedBorder());
		window.setLayout(new BorderLayout());
		setSize(1000, 650);
		setTitle("PHY 2201 : Drag Project");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				System.exit(0);
			}
		});

		// -------------------Options JPanel------------------------
		ButtonGroup axis = new ButtonGroup();
		axis.add(xy);
		axis.add(xyt);
		axis.add(dt);
		optionPanel();
		ButtonGroup T = new ButtonGroup();
		T.add(pos);
		T.add(tim);

		// -------------------Buttons JPanel------------------------
		JPanel b = new JPanel();
		b.setBorder(BorderFactory.createBevelBorder(0));

		GridLayout Buttons = new GridLayout(1, 0);
		Buttons.setHgap(10);

		b.setLayout(Buttons);

		JButton gen, cl;
		b.add(gen = new JButton("Generate Graphs"));
		b.add(cl = new JButton("Clear Graphs"));
		ActionListener ClearListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		};
		cl.addActionListener(ClearListener);
		ActionListener GraphListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawGraph(false, true, false);
			}
		};
		gen.addActionListener(GraphListener);
		add(b, BorderLayout.SOUTH);

		// -------------------Graphs JPanel------------------------
		graphs.setLayout(new GridLayout(1, 3));

		// -------------------Tabs JTabbedPane------------------------
		tabular.add("Graphs", graphs);
		tabular.add("Options 1", option);
		add(tabular, BorderLayout.CENTER);

		setVisible(true);
	}

	/** POPULATOR CODE for FIELD PANEL 1 */
	private void optionPanel() {
		option.add(new JLabel("Position (m):"));
		option.add(position);
		option.add(new JLabel("Velocity Y (m/s):"));
		option.add(vY);
		option.add(new JLabel("Acceleration (m/s^2):"));
		option.add(acceleration);
		option.add(new JLabel("Drag Coefficient:"));
		option.add(dragCo);
		option.add(new JLabel("Object Mass (g):"));
		option.add(objectMass);
		option.add(new JLabel("Object Radius (cm):"));
		option.add(objectCA);
		option.add(new JLabel("Points to Plot (#):"));
		option.add(points);
		option.add(new JLabel("Time (#):"));
		option.add(time);
		option.add(new JLabel("Velocity X (m/s):"));
		option.add(vX);
		option.add(gp);
		option.add(gv);
		option.add(ga);
//		option.add(g1); 
//		option.add(g2);
//		option.add(g3);
		option.add(xy);
		option.add(dt);
		option.add(xyt);
		option.add(tim);
		option.add(pos);
		
//		position.setText("4");
//		vY.setText("2");
//		acceleration.setText("-9.8");
//		dragCo.setText("0.3");
//		objectMass.setText("145");
//		objectCA.setText("7");
//		points.setText("20");
//		time.setText("10");
//		vX.setText("0");
		
		GridLayout SpinSquares = new GridLayout(0, 6);
		SpinSquares.setHgap(50);
		SpinSquares.setVgap(100);
		option.setLayout(SpinSquares);
		option.setBorder(BorderFactory.createBevelBorder(0));
	}

	/** BUTTON CMD 'CLEAR' : Clears all graphs */
	private void clear() {
		graphs.removeAll();
		graphs.repaint();
		graphs.revalidate();
	}

	/**
	 * DOES PREP WORK FOR GRAPHS - SETTINGS - COLORS OF LINES ETC.
	 * 
	 */
	private ChartPanel Graph(JFreeChart xylineChart) {
		ChartPanel chartPanel = new ChartPanel(xylineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(350, 200));
		final XYPlot plot = xylineChart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesPaint(1, Color.GREEN);
		renderer.setSeriesPaint(2, Color.CYAN);
		renderer.setSeriesStroke(0, new BasicStroke(4.0f));
		renderer.setSeriesStroke(1, new BasicStroke(3.0f));
		renderer.setSeriesStroke(2, new BasicStroke(2.0f));
		plot.setRenderer(renderer);
		return chartPanel;
	}

	/**
	 * V.2.0<br>
	 * This code generates certain graphs based on boolean values.<br>
	 * Calls in code to create datasets.<br>
	 * 
	 * @param po
	 * @param ve
	 * @param ac
	 */
	private void drawGraph(boolean po, boolean ve, boolean ac) {
		double pos = (double) (int)position.getValue();		//Double.parseDouble(position.getText());
		double velY =  (double) (int)vY.getValue();		//Double.parseDouble(vY.getText());
		double velX =  	(double) (int)vX.getValue();	//Double.parseDouble(vX.getText());
		double acel  =  (double) (int)acceleration.getValue();	//Double.parseDouble(acceleration.getText());
		int pts =  	(int) points.getValue();	    //Integer.parseInt(points.getText());
		double t =  (double) (int)time.getValue();		//Double.parseDouble(time.getText());
		double dC =  ((double) (int)dragCo.getValue())/10;		//(Double.parseDouble(dragCo.getText()));
		double objCA = 	((double) (int)objectCA.getValue())/100;	// (Double.parseDouble(objectCA.getText()));
		double objM = 	((double) (int)objectMass.getValue())/1000;//(Double.parseDouble(objectMass.getText()))/1000;

		if (po) {
			JFreeChart chart = null;

			if(xy.isSelected()) {
				JFreeChart chart1 = ChartFactory.createXYLineChart("Velocity: Two Dimensions" , "Time", "X, Y", PhysicsMath.velocityXYDataSet(t, acel, pos, velX, velY, pts, dC, objCA, objM), PlotOrientation.VERTICAL , true , true , false);
				velocityGraph = Graph(chart1);
				graphs.add(velocityGraph);
			}
			positionGraph = Graph(chart);
			graphs.add(positionGraph);
		}
		if (ve) {
			if (xy.isSelected()) {
				JFreeChart chart1 = ChartFactory.createXYLineChart(
						"Velocity: Two Dimensions", "X", "Y", PhysicsMath.velocityXYT(t, acel, pos, velX, velY, pts, dC, objCA, objM), PlotOrientation.VERTICAL,
						true, true, false);
				velocityGraph = Graph(chart1);
				graphs.add(velocityGraph);


			} else if (dt.isSelected()) {
				
				JFreeChart chart1 = null;
				if(tim.isSelected()) {
					chart1 = ChartFactory.createXYLineChart("Velocity: One Dimensions" , "Time", "Velocity", PhysicsMath.velocityTimeDataSet(t, acel, pos, velY, pts, dC, objCA, objM), PlotOrientation.VERTICAL , true , true , false);
				} else {
					chart1 = ChartFactory.createXYLineChart("Velocity: One Dimensions" , "Time", "Velocity", PhysicsMath.velocityTimeDataSet(acel, pos, velY, pts, dC, objCA, objM), PlotOrientation.VERTICAL , true , true , false);
				}
			
			velocityGraph = Graph(chart1);
			graphs.add(velocityGraph);
			}
			else {
				JFreeChart chart1 = ChartFactory.createXYLineChart(
						"Velocity: Two Dimensions", "Time", "X, Y", 
						PhysicsMath.velocityXYDataSet(t, acel, pos, velX, velY, pts, dC, objCA, objM), 
						PlotOrientation.VERTICAL, true, true, false);
				velocityGraph = Graph(chart1);
				graphs.add(velocityGraph);

			}
		}
		if (ac) {
			JFreeChart chart2 = ChartFactory.createXYLineChart(
					"Acceleration-Time", "Time", "Acceleration",
					createAccelData(), PlotOrientation.VERTICAL, true, true,
					false);
			accelerationGraph = Graph(chart2);
			graphs.add(accelerationGraph);
		}
		graphs.repaint();
		graphs.revalidate();
	}


	/** Add-on
	 * @return
	 */
	@SuppressWarnings("unused")
	private XYDataset createPositionData() {
		final XYSeries trial = new XYSeries("y axis = position; x axis = time");
		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(trial);
		return dataset;
	}

	/**Add-on
	 * @return
	 */
	private XYDataset createAccelData() {
		return null;
	}

}

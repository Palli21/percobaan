///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package grafikanalisa;
//
///**
// *
// * @author Via
// */
///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//import fungsi.koneksiDB;
//import fungsi.sekuel;
//import fungsi.validasi;
//import java.awt.BasicStroke;
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.Font;
//import java.awt.Graphics2D;
//import java.awt.Stroke;
//import java.awt.Toolkit;
//import java.awt.event.ActionEvent;
//import java.awt.geom.Line2D;
//import java.awt.geom.Rectangle2D;
//import java.io.File;
//import java.io.IOException;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Properties;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JDialog;
//import javax.swing.JPanel;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.ChartUtilities;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.CategoryAxis;
//import org.jfree.chart.axis.CategoryLabelPositions;
//import org.jfree.chart.axis.NumberAxis;
//import org.jfree.chart.axis.ValueAxis;
//import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
//import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
//import org.jfree.chart.plot.CategoryMarker;
//import org.jfree.chart.plot.CategoryPlot;
//import org.jfree.chart.plot.CombinedDomainCategoryPlot;
//import org.jfree.chart.renderer.category.CategoryItemRendererState;
//import org.jfree.chart.renderer.category.LineAndShapeRenderer;
//import org.jfree.data.category.CategoryDataset;
//import org.jfree.data.category.DefaultCategoryDataset;
//
///**
// *
// * @author Via
// */
//public class grafiksqlttv extends JDialog {
//
//    sekuel Sequel = new sekuel();
//    validasi Valid = new validasi();
//
//    public grafiksqlttv(String title, String symbol, String namafile) {
//        // super(title);
//        setTitle(title);
//        JPanel chartPanel = createDemoPanel(symbol, namafile);
//        chartPanel.setSize(screen.width, screen.height);
//
//        setContentPane(chartPanel);
//
//        //setSize(screen.width,screen.height);
//        setModal(true);
//        //setUndecorated(true);
//        setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
//        pack();
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//    }
//
//    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//
//    public static CategoryDataset createDataset1(String symbol) { //data grafik nilai K dan D
//
//        DefaultCategoryDataset result = new DefaultCategoryDataset();
//        String series1 = "Suhu";
//        String series2 = "Sistole";
//        String series22 = "Diastole";
//        String series3 = "Heartrate";
//        String series4 = "Respirasi";
//        String series5 = "SpO2";
//        String series6 = "Nyeri";
//        try {
//            Statement stat = koneksiDB.condb().createStatement();
//            ResultSet rs = stat.executeQuery("select concat(catatan_observasi_ranap.tgl_perawatan,' ',catatan_observasi_ranap.jam_rawat) as waktu, catatan_observasi_ranap.suhu as suhu,catatan_observasi_ranap.td as tensi ,catatan_observasi_ranap.hr as heartrate,catatan_observasi_ranap.rr as respirasi,catatan_observasi_ranap.spo2 as spo2,catatan_observasi_ranap.nyeri as nyeri  from catatan_observasi_ranap "
//                    + symbol + "");
//            while (rs.next()) {
//                String tksbr = rs.getString(1);
//                // Safe parsing methods
//                double suhu = safeParseDouble(rs.getString(2));
//                double[] tensi = safeParseTensi(rs.getString(3));
//                double heartrate = safeParseDouble(rs.getString(4));
//                double respirasi = safeParseDouble(rs.getString(5));
//                double spo2 = safeParseDouble(rs.getString(6));
//                double nyeri = safeParseDouble(rs.getString(7));
//
//                // Add values to dataset
//                result.addValue(suhu, series1, tksbr);
//                result.addValue(tensi[0], series2, tksbr);
//                result.addValue(tensi[1], series22, tksbr);
//                result.addValue(heartrate, series3, tksbr);
//                result.addValue(respirasi, series4, tksbr);
//                result.addValue(spo2, series5, tksbr);
//                result.addValue(nyeri, series6, tksbr);
//            }
//        } catch (SQLException e) {
//            System.out.println("Notifikasi : " + e);
//        }
//        return result;
//    }
//
//    private static JFreeChart createChart(String symbol) {
//
//        CategoryDataset dataset1 = createDataset1(symbol);
//        NumberAxis rangeAxis1 = new NumberAxis("Ukuran");
//        rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//        LineAndShapeRenderer customRenderer = new CustomLineRenderer();
//        customRenderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
//        customRenderer.setSeriesPaint(0, Color.BLUE);
//        customRenderer.setSeriesPaint(1, Color.BLACK); // Sistole
//        customRenderer.setSeriesPaint(2, Color.BLACK); // Diastole
//        customRenderer.setSeriesPaint(3, Color.RED);
//        customRenderer.setSeriesPaint(4, Color.GREEN);
//        customRenderer.setSeriesPaint(5, Color.ORANGE);
//        customRenderer.setSeriesPaint(6, Color.MAGENTA);
//        customRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
//        customRenderer.setBaseItemLabelsVisible(true);
//
//        // Set the stroke (line width) for each series
//        Stroke stroke = new BasicStroke(2.0f); // Adjust the line width here (e.g., 2.0f for a wider line)
//        for (int i = 0; i < dataset1.getRowCount(); i++) {
//            customRenderer.setSeriesStroke(i, stroke);
//        }
//        CategoryPlot subplot1 = new CategoryPlot(dataset1, null, rangeAxis1, customRenderer);
//        subplot1.setDomainGridlinesVisible(true);
//        subplot1.setRangeGridlinesVisible(true);
//        subplot1.setRangeGridlinePaint(Color.BLACK);
//        subplot1.setDomainGridlinesVisible(true);
//        subplot1.setDomainGridlinePaint(Color.BLACK);
//        subplot1.setBackgroundPaint(Color.LIGHT_GRAY);
//
//        // Add vertical line marker
//        CategoryMarker marker = new CategoryMarker("Diastole"); // Category after which you want the line
//        marker.setPaint(Color.GRAY);
//        marker.setStroke(new BasicStroke(1.5f)); // Line thickness
//        marker.setAlpha(0.7f); // Transparency (optional)
//        subplot1.addDomainMarker(marker);
//
//        CategoryAxis domainAxis = new CategoryAxis("Tanda Tanda Vital Pasien(data dari form Observasi Rawat Inap)");
//        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
//        CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(domainAxis);
//        plot.add(subplot1, 1);
//        JFreeChart result = new JFreeChart("", new Font("SansSerif", Font.BOLD, 8), plot, true);
//        return result;
//
//    }
//
//    /**
//     * Creates a panel for the demo (used by SuperDemo.java).
//     *
//     * @return A panel.
//     */
//    private static void saveChartAsImage(JFreeChart chart, String filename, int width, int height) {
//        try {
//            ChartUtilities.saveChartAsJPEG(new File(filename), chart, width, height);
//        } catch (IOException e) {
//            System.err.println("Error saving chart: " + e.getMessage());
//        }
//    }
//
////    public static JPanel createDemoPanel(String symbol, String namafile) {
////        validasi validasi1 = new validasi();
////        validasi1.hapusFileDalamFolder("/gambargrafik/");
////        JFreeChart chart = createChart(symbol);
////        Properties systemProp = System.getProperties();
////        String currentDir = systemProp.getProperty("user.dir");
////        String outputPath = currentDir + "/gambargrafik/" + namafile + ".jpg"; // Replace this with the desired output path
////        saveChartAsImage(chart, outputPath, 1280, 720);
////        return new ChartPanel(chart);
////    }
//    public static JPanel createDemoPanel(String symbol, String namafile) {
//        validasi validasi1 = new validasi();
//        validasi1.hapusFileDalamFolder("/gambargrafik/");
//        JFreeChart chart = createChart(symbol);
//
//        Properties systemProp = System.getProperties();
//        String currentDir = systemProp.getProperty("user.dir");
//        String outputPath = currentDir + "/gambargrafik/" + namafile + ".jpg"; // Replace this with the desired output path
//
//        saveChartAsImage(chart, outputPath, 1280, 720);
//
//        // Create the ChartPanel
//        ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setMouseZoomable(true);  // Enable zooming with the mouse wheel
//        chartPanel.setDomainZoomable(true);    // Allow zooming along the x-axis
//        chartPanel.setRangeZoomable(true);     // Allow zooming along the y-axis
//
//        // Create a panel for zoom controls
//        JPanel zoomControlPanel = new JPanel();
//        zoomControlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//
//        // Add Zoom In button
//        JButton zoomInButton = new JButton("Zoom In");
//        zoomInButton.addActionListener((ActionEvent e) -> {
//            chartPanel.zoomInBoth(chartPanel.getWidth() / 2.0, chartPanel.getHeight() / 2.0);
//        });
//        zoomControlPanel.add(zoomInButton);
//
//        // Add Zoom Out button
//        JButton zoomOutButton = new JButton("Zoom Out");
//        zoomOutButton.addActionListener((ActionEvent e) -> {
//            chartPanel.zoomOutBoth(chartPanel.getWidth() / 2.0, chartPanel.getHeight() / 2.0);
//        });
//        zoomControlPanel.add(zoomOutButton);
//
//        // Add Reset Zoom button
//        JButton resetZoomButton = new JButton("Reset Zoom");
//        resetZoomButton.addActionListener((ActionEvent e) -> {
//            chartPanel.restoreAutoBounds();
//        });
//        zoomControlPanel.add(resetZoomButton);
//
//        // Combine chart and controls in a vertical layout
//        JPanel mainPanel = new JPanel();
//        mainPanel.setLayout(new BorderLayout());
//        mainPanel.add(chartPanel, BorderLayout.CENTER);       // Chart panel in the center
//        mainPanel.add(zoomControlPanel, BorderLayout.SOUTH);  // Control panel at the bottom
//
//        return mainPanel;
//    }
//
//    public static class CustomLineRenderer extends LineAndShapeRenderer {
//
//        @Override
//        public void drawItem(Graphics2D g2, CategoryItemRendererState state,
//                Rectangle2D dataArea, CategoryPlot plot,
//                CategoryAxis domainAxis, ValueAxis rangeAxis,
//                CategoryDataset dataset, int row, int column,
//                int pass) {
//            super.drawItem(g2, state, dataArea, plot, domainAxis, rangeAxis, dataset, row, column, pass);
//
//            // Draw vertical line between "Sistole" and "Diastole"
//            if (row == 1 || row == 2) { // Assuming row 1 = Sistole, row 2 = Diastole
//                double sistoleValue = dataset.getValue(1, column).doubleValue();
//                double diastoleValue = dataset.getValue(2, column).doubleValue();
//
//                // X coordinate for the current category
//                double x = domainAxis.getCategoryMiddle(column, dataset.getColumnCount(), dataArea, plot.getDomainAxisEdge());
//
//                // Y coordinates for Sistole and Diastole
//                double ySistole = rangeAxis.valueToJava2D(sistoleValue, dataArea, plot.getRangeAxisEdge());
//                double yDiastole = rangeAxis.valueToJava2D(diastoleValue, dataArea, plot.getRangeAxisEdge());
//
//                // Draw the vertical line
//                g2.setPaint(Color.DARK_GRAY);
//                g2.setStroke(new BasicStroke(1.5f)); // Adjust line thickness
//                g2.draw(new Line2D.Double(x, ySistole, x, yDiastole));
//
//                // Draw the up arrow at Sistole point
//                int[] xUpArrow = {(int) x - 5, (int) x + 5, (int) x}; // Triangle points
//                int[] yUpArrow = {(int) ySistole + 10, (int) ySistole + 10, (int) ySistole};
//                g2.setPaint(Color.DARK_GRAY); // Arrow color
//                g2.fillPolygon(xUpArrow, yUpArrow, 3);
//
//                // Draw the down arrow at Diastole point
//                int[] xDownArrow = {(int) x - 5, (int) x + 5, (int) x}; // Triangle points
//                int[] yDownArrow = {(int) yDiastole - 10, (int) yDiastole - 10, (int) yDiastole};
//                g2.setPaint(Color.DARK_GRAY); // Arrow color
//                g2.fillPolygon(xDownArrow, yDownArrow, 3);
//            }
//        }
//    }
//
//    // Safe double parsing method
//    private static double safeParseDouble(String value) {
//        if (value == null || value.trim().isEmpty()) {
//            return 0.0;
//        }
//
//        try {
//            // Replace comma with dot for decimal separator
//            value = value.replace(',', '.');
//
//            // Remove any non-numeric characters except dot and minus
//            value = value.replaceAll("[^0-9.-]", "");
//
//            return Double.parseDouble(value);
//        } catch (NumberFormatException e) {
//            return 0.0;
//        }
//    }
//
//// Safe parsing method for blood pressure (systolic/diastolic)
//    private static double[] safeParseTensi(String value) {
//        if (value == null || value.trim().isEmpty()) {
//            return new double[]{0.0, 0.0};
//        }
//
//        try {
//            // Replace comma with dot for decimal separator
//            value = value.replace(',', '.');
//
//            // Remove any non-numeric characters except dot, minus, and slash
//            value = value.replaceAll("[^0-9.-/]", "");
//
//            // Split systolic and diastolic
//            String[] parts = value.split("/");
//
//            if (parts.length == 2) {
//                double systolic = safeParseDouble(parts[0]);
//                double diastolic = safeParseDouble(parts[1]);
//                return new double[]{systolic, diastolic};
//            }
//        } catch (Exception e) {
//            // Log or handle the error if needed
//        }
//
//        return new double[]{0.0, 0.0};
//    }
//
//    /**
//     * Starting point for the demonstration application.
//     *
//     * @param args ignored.
//     */
//}
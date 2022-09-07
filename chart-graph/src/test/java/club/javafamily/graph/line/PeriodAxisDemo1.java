package club.javafamily.graph.line;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.PeriodAxisLabelInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.UIUtils;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimePeriodAnchor;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYDataset;

public class PeriodAxisDemo1 extends ApplicationFrame {
    public PeriodAxisDemo1(String title) {
        super(title);
        JPanel chartPanel = createDemoPanel();
        chartPanel.setPreferredSize(new Dimension(500, 270));
        this.setContentPane(chartPanel);
    }

    private static JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Legal & General Unit Trust Prices", "Date",
                "Price Per Unit",
                dataset, true, true, false);
        XYPlot plot = (XYPlot)chart.getPlot();
        chart.setBackgroundPaint(Color.WHITE);
//        plot.setDomainCrosshairVisible(true);
//        plot.setRangeCrosshairVisible(true);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeCrosshairPaint(Color.black);
        XYItemRenderer renderer = plot.getRenderer();
        if (renderer instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer rr = (XYLineAndShapeRenderer)renderer;
//            rr.setDefaultShapesVisible(true);
//            rr.setDefaultShapesFilled(true);
            rr.setDefaultItemLabelsVisible(true);
        }

        PeriodAxis domainAxis = new PeriodAxis("Date");
//        domainAxis.setTimeZone(TimeZone.getTimeZone("Pacific/Auckland"));
        domainAxis.setAutoRangeTimePeriodClass(Month.class);
        domainAxis.setMajorTickTimePeriodClass(Month.class);
        PeriodAxisLabelInfo[] info = new PeriodAxisLabelInfo[]{new PeriodAxisLabelInfo(Month.class, new SimpleDateFormat("MM"), new RectangleInsets(2.0D, 2.0D, 2.0D, 2.0D), new Font("SansSerif", 1, 10), Color.BLUE, false, new BasicStroke(0.0F), Color.LIGHT_GRAY), new PeriodAxisLabelInfo(Year.class, new SimpleDateFormat("yyyy"))};
        domainAxis.setLabelInfo(info);
        plot.setDomainAxis(domainAxis);
//        ChartUtils.applyCurrentTheme(chart);
        return chart;
    }

    private static XYDataset createDataset() {
        TimeSeries s1 = new TimeSeries("L&G European Index Trust");
        s1.add(new Month(2, 2001), 181.8D);
        s1.add(new Month(3, 2001), 167.3D);
        s1.add(new Month(4, 2001), 153.8D);
        s1.add(new Month(5, 2001), 167.6D);
        s1.add(new Month(6, 2001), 158.8D);
        s1.add(new Month(7, 2001), 148.3D);
        s1.add(new Month(8, 2001), 153.9D);
        s1.add(new Month(9, 2001), 142.7D);
        s1.add(new Month(10, 2001), 123.2D);
        s1.add(new Month(11, 2001), 131.8D);
        s1.add(new Month(12, 2001), 139.6D);
        s1.add(new Month(1, 2002), 142.9D);
        s1.add(new Month(2, 2002), 138.7D);
        s1.add(new Month(3, 2002), 137.3D);
        s1.add(new Month(4, 2002), 143.9D);
        s1.add(new Month(5, 2002), 139.8D);
        s1.add(new Month(6, 2002), 137.0D);
        s1.add(new Month(7, 2002), 132.8D);
        TimeSeries s2 = new TimeSeries("L&G UK Index Trust");
        s2.add(new Month(2, 2001), 129.6D);
        s2.add(new Month(3, 2001), 123.2D);
        s2.add(new Month(4, 2001), 117.2D);
        s2.add(new Month(5, 2001), 124.1D);
        s2.add(new Month(6, 2001), 122.6D);
        s2.add(new Month(7, 2001), 119.2D);
        s2.add(new Month(8, 2001), 116.5D);
        s2.add(new Month(9, 2001), 112.7D);
        s2.add(new Month(10, 2001), 101.5D);
        s2.add(new Month(11, 2001), 106.1D);
        s2.add(new Month(12, 2001), 110.3D);
        s2.add(new Month(1, 2002), 111.7D);
        s2.add(new Month(2, 2002), 111.0D);
        s2.add(new Month(3, 2002), 109.6D);
        s2.add(new Month(4, 2002), 113.2D);
        s2.add(new Month(5, 2002), 111.6D);
        s2.add(new Month(6, 2002), 108.8D);
        s2.add(new Month(7, 2002), 101.6D);
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s1);
        dataset.addSeries(s2);
        dataset.setXPosition(TimePeriodAnchor.MIDDLE);
        return dataset;
    }

    public static JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }

    public static void main(String[] args) {
        PeriodAxisDemo1 demo = new PeriodAxisDemo1("JFreeChart: PeriodAxisDemo1.java");
        demo.pack();
        UIUtils.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}
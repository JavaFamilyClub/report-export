package club.javafamily.graph.line;

import club.javafamily.utils.DateUtil;
import club.javafamily.utils.Tool;
import org.jfree.chart.*;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.PeriodAxisLabelInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.*;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PeriodAxisDemo11 extends ApplicationFrame {
    public PeriodAxisDemo11(String title) {
        super(title);
        JPanel chartPanel = createDemoPanel();
        chartPanel.setPreferredSize(new Dimension(1000, 400));
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
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
//        plot.setDomainCrosshairPaint(Color.red);

        XYItemRenderer renderer = plot.getRenderer();

        if (renderer instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer rr = (XYLineAndShapeRenderer)renderer;
//            rr.setDefaultShapesVisible(true);
//            rr.setDefaultShapesFilled(true);
//            rr.setDefaultItemLabelsVisible(true);
        }

        PeriodAxis domainAxis = new PeriodAxis("Date");
//        domainAxis.setTimeZone(TimeZone.getTimeZone("Pacific/Auckland"));
        domainAxis.setAutoRangeTimePeriodClass(Hour.class);
        domainAxis.setMajorTickTimePeriodClass(Hour.class);
        PeriodAxisLabelInfo[] info = new PeriodAxisLabelInfo[]{
           new PeriodAxisLabelInfo(Hour.class,
              new SimpleDateFormat("HH"),
              new RectangleInsets(2.0D, 2.0D, 2.0D, 2.0D),
              new Font("SansSerif", 1, 12), Color.BLUE, true, new BasicStroke(0.0F), Color.LIGHT_GRAY),
           new PeriodAxisLabelInfo(Day.class, new SimpleDateFormat("dd"))};
        domainAxis.setLabelInfo(info);
        plot.setDomainAxis(domainAxis);
//        ChartUtils.applyCurrentTheme(chart);
        return chart;
    }

    private static XYDataset createDataset() {
        TimeSeries s1 = new TimeSeries("L&G European Index Trust");
        Calendar calendar = DateUtil.getTodayStartCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 8);

        for (int i = 0; i < 24 * 3; i++) {
            s1.add(new Hour(calendar.getTime()), Tool.getSecureRandom().nextDouble() * 30);
            calendar.add(Calendar.HOUR, 1);
        }

        TimeSeries s2 = new TimeSeries("L&G UK Index Trust");
        calendar = DateUtil.getTodayStartCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 8);

        for (int i = 0; i < 24 * 3; i++) {
            s2.add(new Hour(calendar.getTime()), Tool.getSecureRandom().nextDouble() * 30);
            calendar.add(Calendar.HOUR, 1);
        }

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
        PeriodAxisDemo11 demo = new PeriodAxisDemo11("JFreeChart: PeriodAxisDemo1.java");
        demo.pack();
        UIUtils.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}

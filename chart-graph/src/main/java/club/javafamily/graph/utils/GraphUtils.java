package club.javafamily.graph.utils;

import club.javafamily.assembly.chart.ChartAssembly;
import club.javafamily.assembly.chart.axis.XAxis;
import club.javafamily.assembly.chart.impl.xy.axis.XyAxisInfo;
import club.javafamily.assembly.chart.impl.xy.binding.XyChartBindingInfo;
import club.javafamily.assembly.chart.impl.xy.ref.XyChartBindingRef;
import club.javafamily.enums.DataTypeSchema;
import club.javafamily.enums.DateLevel;
import club.javafamily.lens.TableLens;
import club.javafamily.utils.Tool;
import club.javafamily.utils.collections.CollectionUtil;
import club.javafamily.utils.common.MessageException;
import club.javafamily.utils.spring.ObjectUtils;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;

import java.util.*;

/**
 * @author Jack Li
 * @date 2022/9/7 下午4:35
 * @description
 */
public class GraphUtils {

   /**
    * 获取 x1 title
    * @param lens lens
    * @param domainSeries x1 series
    * @param x1PrimaryRef x1 data ref
    * @return 如果未自定义 title, 就使用数据第一行 header
    */
   public static String getPrimaryX1Title(TableLens lens,
                                          XAxis domainSeries,
                                          XyChartBindingRef x1PrimaryRef)
   {
      final String label = domainSeries.getTitle();

      if(!ObjectUtils.isEmpty(label)) {
         return label;
      }

      final int dataIndex = x1PrimaryRef.getColIndex();

      return Tool.toString(lens.getObject(0, dataIndex));
   }

   /**
    * 创建对应 Assembly 的 Dataset
    * @param assembly Assembly
    * @return dataset
    */
   public static XYDataset createXYDataset(ChartAssembly assembly) {
      final XyChartBindingInfo chartBinding
         = (XyChartBindingInfo) assembly.getChartBinding();

      if(CollectionUtil.isEmpty(chartBinding.getYBindingRefs())) {
         throw new MessageException("请先绑定 Y 数据域!");
      }

      final TableLens lens = assembly.getTableLens();
      final XyChartBindingRef x1PrimaryRef = chartBinding.primaryX1BindingRef();
      final int primaryX1DataIndex = x1PrimaryRef.getColIndex();

      if(DateUtil.isDatetime(lens.getColumnType(primaryX1DataIndex))
         || x1PrimaryRef.getType() == DataTypeSchema.TIME_SERIES)
      {
         return createTimeSeriesDs(assembly);
      }
      else if(x1PrimaryRef.getType() == DataTypeSchema.PERIOD)
      {
         return createPeriodDs(assembly);
      }

      return createComparableDs(assembly);
   }

   /**
    * 创建普通 dataset
    * @param assembly chart
    * @return dataset
    */
   private static XYDataset createComparableDs(ChartAssembly assembly) {
      throw new UnsupportedOperationException("暂不支持!");
   }

   /**
    * 创建 period dataset
    * @param assembly chart
    * @return dataset
    */
   private static XYDataset createPeriodDs(ChartAssembly assembly) {
      final TableLens lens = assembly.getTableLens();
      final XyChartBindingInfo chartBinding
         = (XyChartBindingInfo) assembly.getChartBinding();
      final XyAxisInfo axisInfo = (XyAxisInfo) assembly.getAxisInfo();
      final XAxis primaryX1 = axisInfo.primaryX1Axis();
      final XyChartBindingRef x1PrimaryRef = chartBinding.primaryX1BindingRef();
      final int primaryX1DataIndex = x1PrimaryRef.getColIndex();

      TimePeriodValuesCollection dataset = new TimePeriodValuesCollection();

      for (XyChartBindingRef ref : chartBinding.getYBindingRefs()) {
         final int valueIndex = ref.getColIndex();
         final String primaryX1Title = getPrimaryX1Title(lens, primaryX1, x1PrimaryRef);
         TimePeriodValues chartSeries = new TimePeriodValues(primaryX1Title);

         for (int r = lens.getHeaderRowCount(); r < lens.getRowCount(); r++) {
            chartSeries.add(createJFreePeriodDate(primaryX1,
               (Date) lens.getObject(r, primaryX1DataIndex)),
               getNumber(lens.getObject(r, valueIndex)));
         }

         dataset.addSeries(chartSeries);
      }

      return dataset;
   }

   /**
    * 创建 timeSeries dataset
    * @param assembly chart
    * @return XYDataset
    */
   private static XYDataset createTimeSeriesDs(ChartAssembly assembly) {
      final TableLens lens = assembly.getTableLens();
      final XyChartBindingInfo chartBinding
         = (XyChartBindingInfo) assembly.getChartBinding();
      final XyAxisInfo axisInfo = (XyAxisInfo) assembly.getAxisInfo();
      final XAxis primaryX1 = axisInfo.primaryX1Axis();
      final XyChartBindingRef x1PrimaryRef = chartBinding.primaryX1BindingRef();
      final int primaryX1DataIndex = x1PrimaryRef.getColIndex();

      TimeSeriesCollection dataset = new TimeSeriesCollection();

      for (XyChartBindingRef ref : chartBinding.getYBindingRefs()) {
         final int valueIndex = ref.getColIndex();
         final String primaryX1Title = getPrimaryX1Title(lens, primaryX1, x1PrimaryRef);
         TimeSeries chartSeries = new TimeSeries(primaryX1Title);

         for (int r = lens.getHeaderRowCount(); r < lens.getRowCount(); r++) {
            chartSeries.add(createJFreePeriodDate(primaryX1,
               (Date) lens.getObject(r, primaryX1DataIndex)),
               getNumber(lens.getObject(r, valueIndex)));
         }

         dataset.addSeries(chartSeries);
      }

      return dataset;
   }

   private static double getNumber(Object object) {
      return Double.parseDouble(Tool.toString(object));
   }

   public static RegularTimePeriod createJFreePeriodDate(XAxis xAxis,
                                                         Date date)
   {
      final Class<?> clazz = getDateJFreeType(xAxis.getDateLevel());

      return RegularTimePeriod.createInstance(clazz, date,
         TimeZone.getDefault(), Locale.getDefault());
   }

   public static Class<?> getDateJFreeType(DateLevel dateLevel) {
      switch (dateLevel) {
         case YEAR:
            return Year.class;
         case MONTH:
            return Month.class;
         case DAY:
            return Day.class;
         case HOUR:
            return Hour.class;
         case MINUTE:
            return Minute.class;
         case SECOND:
            return Second.class;
         case MILLISECOND:
            return Millisecond.class;
         default:
            throw new UnsupportedOperationException("不支持的 Date Level!");
      }
   }

}

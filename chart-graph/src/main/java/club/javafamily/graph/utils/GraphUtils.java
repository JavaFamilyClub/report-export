package club.javafamily.graph.utils;

import club.javafamily.assembly.chart.ChartAssembly;
import club.javafamily.assembly.chart.axis.XAxis;
import club.javafamily.assembly.chart.series.Series;
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
    * 获取 x1
    * @param lens
    * @param domainSeries
    * @return
    */
   public static String getPrimaryX1Title(TableLens lens,
                                          XAxis domainSeries)
   {
      final String label = domainSeries.getTitle();

      if(!ObjectUtils.isEmpty(label)) {
         return label;
      }

      final int dataIndex = domainSeries.getDataIndex();

      return Tool.toString(lens.getObject(0, dataIndex));
   }

   /**
    * 创建对应 Assembly 的 Dataset
    * @param assembly Assembly
    * @return dataset
    */
   public static XYDataset createXYDataset(ChartAssembly assembly) {
      final List<Series> valueSeries = assembly.getValueSeries();

      if(CollectionUtil.isEmpty(valueSeries)) {
         throw new MessageException("请先绑定 Series 数据域!");
      }

      final TableLens lens = assembly.getTableLens();
      final XAxis primaryX1 = assembly.primaryX1Axis();
      final int primaryX1DataIndex = primaryX1.getDataIndex();

      if(DateUtil.isDatetime(lens.getColumnType(primaryX1DataIndex))) {
         TimeSeriesCollection dataset = new TimeSeriesCollection();

         for (Series series : valueSeries) {
            final int valueIndex = series.getDataIndex();
            final String primaryX1Title = getPrimaryX1Title(lens, primaryX1);
            TimeSeries chartSeries = new TimeSeries(primaryX1Title);

            for (int r = lens.getHeaderRowCount(); r < lens.getRowCount(); r++) {
               chartSeries.add(createJFreeDate(primaryX1,
                  (Date) lens.getObject(r, primaryX1DataIndex)),
                  getNumber(lens.getObject(r, valueIndex)));
            }

            dataset.addSeries(chartSeries);
         }

         return dataset;
      }

      throw new UnsupportedOperationException("目前只支持 Date 类型的 x1!");
   }

   private static double getNumber(Object object) {
      return Double.parseDouble(Tool.toString(object));
   }

   public static RegularTimePeriod createJFreeDate(XAxis xAxis,
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

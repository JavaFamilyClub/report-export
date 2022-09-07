package club.javafamily.graph.utils;

import club.javafamily.assembly.chart.ChartAssembly;
import club.javafamily.assembly.chart.series.DomainSeries;
import club.javafamily.assembly.chart.series.Series;
import club.javafamily.lens.TableLens;
import club.javafamily.utils.Tool;
import club.javafamily.utils.collections.CollectionUtil;
import club.javafamily.utils.common.MessageException;
import club.javafamily.utils.spring.ObjectUtils;
import org.jfree.data.general.Dataset;
import org.jfree.data.time.*;

import java.util.Date;
import java.util.List;

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
   public static String getDomainLabel(TableLens lens,
                                       DomainSeries domainSeries)
   {
      final String label = domainSeries.getLabel();

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
   public static Dataset createDataset(ChartAssembly assembly) {
      final List<Series> valueSeries = assembly.getValueSeries();

      if(CollectionUtil.isEmpty(valueSeries)) {
         throw new MessageException("请先绑定 Series 数据域!");
      }

      final TableLens lens = assembly.getTableLens();
      final DomainSeries domainSeries = assembly.getDomainSeries();
      final int domainSeriesDataIndex = domainSeries.getDataIndex();

      TimeSeriesCollection dataset = new TimeSeriesCollection();

      for (Series series : valueSeries) {
         final int valueIndex = series.getDataIndex();
         final String domainLabel = getDomainLabel(lens, domainSeries);
         TimeSeries chartSeries = new TimeSeries(domainLabel);

         for (int r = lens.getHeaderRowCount(); r < lens.getRowCount(); r++) {
            chartSeries.add(createJFreeDate(domainSeries,
               (Date) lens.getObject(r, domainSeriesDataIndex)),
               getNumber(lens.getObject(r, valueIndex)));
         }

         dataset.addSeries(chartSeries);
      }

      return dataset;
   }

   private static double getNumber(Object object) {
      return Double.parseDouble(Tool.toString(object));
   }

   private static RegularTimePeriod createJFreeDate(DomainSeries domainSeries,
                                                    Date date)
   {
      return null;
   }

}

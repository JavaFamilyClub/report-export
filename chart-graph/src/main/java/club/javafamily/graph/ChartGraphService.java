package club.javafamily.graph;

import club.javafamily.assembly.chart.ChartAssembly;
import club.javafamily.assembly.chart.CustomChartAssembly;
import club.javafamily.utils.collections.CollectionUtil;

import java.awt.image.BufferedImage;
import java.util.*;

/**
 * @author Jack Li
 * @date 2022/9/7 下午1:57
 * @description
 */
public class ChartGraphService {

   private List<ChartGraph> chartGraphs = new ArrayList<>();

   public ChartGraphService() {
      initChartGraphs();
   }

   /**
    * 绘制 Chart 图
    * @param chart assembly
    * @return image
    */
   public BufferedImage drawChartImage(ChartAssembly chart) {
      if(CollectionUtil.isEmpty(chartGraphs)) {
         throw new UnsupportedOperationException("没有注册的 ChartGraph!");
      }

      // 自定义 Chart 直接获取 image
      if(chart instanceof CustomChartAssembly) {
         return ((CustomChartAssembly) chart).createImage();
      }

      for (ChartGraph chartGraph : chartGraphs) {
         if(chartGraph.supported(chart)) {
            return chartGraph.drawChart(chart);
         }
      }

      throw new UnsupportedOperationException("没有可用的 ChartGraph!");
   }

   private void initChartGraphs() {
      loadInternalGraph();
      loadSpiExtensionGraph();

      // sort by order
      chartGraphs.sort(Comparator.comparingInt(ChartGraph::getOrder));
   }

   /**
    * 加载组件内部支持的 {@link ChartGraph}
    */
   private void loadInternalGraph() {
   }

   /**
    * 通过 Java SPI 扩展收集所有的 {@link ChartGraph}
    */
   private void loadSpiExtensionGraph() {
      ServiceLoader<ChartGraph> actions = ServiceLoader.load(ChartGraph.class);

      for (ChartGraph graph : actions) {
         chartGraphs.add(graph);
      }
   }
}

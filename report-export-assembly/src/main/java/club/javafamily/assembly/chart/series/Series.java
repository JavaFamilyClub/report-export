package club.javafamily.assembly.chart.series;

/**
 * @author Jack Li
 * @date 2022/9/7 下午5:19
 * @description
 */
public class Series {

   /**
    * axis data index in {@link club.javafamily.lens.TableLens}
    */
   private int dataIndex;

   public Series() {
   }

   public Series(int dataIndex) {
      this.dataIndex = dataIndex;
   }

   public int getDataIndex() {
      return dataIndex;
   }

   public void setDataIndex(int dataIndex) {
      this.dataIndex = dataIndex;
   }

}

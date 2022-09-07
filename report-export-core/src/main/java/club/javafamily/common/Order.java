package club.javafamily.common;

/**
 * @author Jack Li
 * @date 2022/9/7 下午2:42
 * @description
 */
public interface Order {

   /**
    * 组件内部优先级
    */
   int INTERNAL_PRIORITY = 0;

   /**
    * 启用顺序, 越小优先级越高, 可以为负数
    * @return order
    */
   int getOrder();

}

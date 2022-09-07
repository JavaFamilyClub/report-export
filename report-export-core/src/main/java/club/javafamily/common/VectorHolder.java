package club.javafamily.common;

import club.javafamily.utils.Tool;

import java.io.Serializable;
import java.util.Vector;

/**
 * @author Jack Li
 * @date 2022/9/7 上午9:33
 * @description
 */
public class VectorHolder implements Serializable, Cloneable {

      public void insert(int idx, int n) {
         if(vector == null || idx >= vector.size()) {
            return;
         }

         for(int i = 0; i < n; i++) {
            vector.insertElementAt(null, idx);
         }
      }

      public void remove(int idx, int n) {
         if(vector == null || idx >= vector.size()) {
            return;
         }

         if(idx + n > vector.size()) {
            n = vector.size() - idx;
         }

         for(int i = 0; i < n; i++) {
            vector.removeElementAt(idx);
         }
      }

      @Override
      public Object clone() {
         VectorHolder holder = new VectorHolder();

         if(vector != null) {
            holder.vector = Tool.deepCloneCollection(vector);
         }

         return holder;
      }

      public void clear() {
         vector = null;
      }

      @Override
      public String toString() {
         return vector + "";
      }

      private Vector vector;
   }

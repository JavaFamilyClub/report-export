package club.javafamily.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;

/**
 * @author Jack Li
 * @date 2022/9/3 上午9:19
 * @description 稀疏矩阵实现 (https://www.bu.edu/pasi/files/2011/01/NathanBell1-10-1000.pdf)
 *
 * 在矩阵中，若数值为0的元素数目远远多于非0元素的数目，并且非0元素分布没有规律时，则称该矩阵为稀疏矩阵；与之相反，若非0元素数目占大多数时，则称该矩阵为稠密矩阵。定义非零元素的总数比上矩阵所有元素的总数为矩阵的稠密度。
 * 稀疏因子是用于描述稀疏矩阵的非零元素的比例情况。设一个n*m的稀疏矩阵A中有t个非零元素，则稀疏因子δδ的计算公式如下：δ=tn∗mδ=tn∗m(当这个值小于等于0.05时，可以认为是稀疏矩阵)
 *
 * 存储矩阵的一般方法是采用二维数组，其优点是可以随机地访问每一个元素，因而能够较容易地实现矩阵的各种运算，如转置运算、加法运算、乘法运算等。
 * 最常用的稀疏矩阵存储格式主要有：COO（Coordinate Format）和CSR（Compressed Sparse Row）。
 *
 * 1. COO 很简单，就是使用3个数组，分别存储全部非零元的行下标（row index）、列下标（column index）和值（value）；
 *    每个三元组（行号，列号，数值）自己可以定位，因此空间不是最优。
 * 2. CSR 稍复杂，对行下标进行了压缩，假设矩阵行数是m，则压缩后的数组长度为m+1，记作（row ptr），其中第i个元素（0-base）表示矩阵前i行的非零元个数。
 *    CSR是比较标准的一种，也需要三类数据来表达：数值，列号，以及行偏移。CSR不是三元组，而是整体的编码方式。数值和列号与COO一致，表示一个元素以及其列号，行偏移表示某一行的第一个元素在values里面的起始偏移位置
 * 3. CSC 是和CSR相对应的一种方式，即按列压缩的意思。
 *
 * 较复杂的稀疏矩阵还有:
 * 4. ELLPACK(ELL): 用两个和原始矩阵相同行数的矩阵来存：第一个矩阵存的是列号，第二个矩阵存的是数值，行号就不存了，用自身所在的行来表示；这两个矩阵每一行都是从头开始放，如果没有元素了就用个标志比如*结束.
 * 5. Diagonal(DIA): 对角线存储法，按对角线方式存，列代表对角线，行代表行。省略全零的对角线。
 * 6. Hybrid (HYB) ELL + COO: 为了解决（4）ELL中如果某一行特别多，造成其他行的浪费的问题，那么把这些多出来的元素用COO单独存储。
 *
 * 经验:
 * 1) DIA和ELL格式在进行稀疏矩阵-矢量乘积(sparse matrix-vector products)时效率最高，所以它们是应用迭代法(如共轭梯度法)解稀疏线性系统最快的格式；
 * 2) COO和CSR格式比起DIA和ELL来，更加灵活，易于操作；
 * 3) ELL的优点是快速，而COO优点是灵活，二者结合后的HYB格式是一种不错的稀疏矩阵表示格式；
 * 4) CSR格式在存储稀疏矩阵时非零元素平均使用的字节数(Bytes per Nonzero Entry)最为稳定（float类型约为8.5，double类型约为12.5），而DIA格式存储数据的非零元素平均使用的字节数与矩阵类型有较大关系，适合于StructuredMesh结构的稀疏矩阵（float类型约为4.05，double类型约为8.10），对于Unstructured Mesh以及Random Matrix,DIA格式使用的字节数是CSR格式的十几倍；
 * 5) COO格式常用于从文件中进行稀疏矩阵的读写，如matrix market即采用COO格式，而CSR格式常用于读入数据后进行稀疏矩阵计算。
 */
public class IndexedSparseMatrix implements Cloneable, Serializable {
   public IndexedSparseMatrix() {
      clear();
   }

   /**
    * Get a value from a matrix in cell, row, column order.
    */
   public Object getByLevel(int r, int c) {
      if(this.isEmpty()) {
         return null;
      }

      Object obj = this.get(r, c);

      if(obj != null) {
         return obj;
      }

      obj = this.getRow(r);

      if(obj != null) {
         return obj;
      }

      return this.getColumn(c);
   }

   /**
    * Remove all data in the matrix.
    */
   public void clear() {
      matrix = new int[0][0];
      colIdxs = new int[0];
      rowIdxs = new int[0];
      empty = true;
   }

   /**
    * Set the row attribute.
    */
   public void setRow(int row, Object val) {
      if(row < 0) {
         throw new RuntimeException(row + " is an invalid row index.");
      }

      if(val == null) {
         if(row < rowIdxs.length) {
            rowIdxs[row] = 0;
         }

         return;
      }

      if(row >= rowIdxs.length) {
         rowIdxs = expand(rowIdxs, row + 1);
      }

      rowIdxs[row] = find(val);
      empty = false;
   }

   /**
    * Get the row attribute.
    */
   public Object getRow(int row) {
      if(row < 0 || row >= rowIdxs.length) {
         return null;
      }

      return (rowIdxs[row] == 0) ? null : objects[rowIdxs[row] - 1];
   }

   /**
    * Set the column attribute.
    */
   public void setColumn(int column, Object val) {
      if(column < 0) {
         throw new RuntimeException(column + " is an invalid column index.");
      }

      if(val == null) {
         if(column < colIdxs.length) {
            colIdxs[column] = 0;
         }

         return;
      }

      if(column >= colIdxs.length) {
         colIdxs = expand(colIdxs, column + 1);
      }

      colIdxs[column] = find(val);
      empty = false;
   }

   /**
    * Get the column attribute.
    */
   public Object getColumn(int column) {
      if(column < 0 || column >= colIdxs.length) {
         return null;
      }

      return (colIdxs[column] == 0) ? null : objects[colIdxs[column] - 1];
   }

   /**
    * Set the value of a cell in the matrix.
    */
   public void set(int row, int col, Object val) {
      if(col >= matrix.length) {
         int[][] nmatrix = new int[col + 1][];

         System.arraycopy(matrix, 0, nmatrix, 0, matrix.length);
         matrix = nmatrix;

         for(int i = 0; i < matrix.length; i++) {
            if(matrix[i] == null) {
               matrix[i] = new int[0];
            }
         }
      }

      int[] rowIdxs = matrix[col];

      if(val == null) {
         if(row < rowIdxs.length) {
            rowIdxs[row] = 0;
         }

         return;
      }

      if(row >= rowIdxs.length) {
         matrix[col] = rowIdxs = expand(rowIdxs, row + 1);
      }

      rowIdxs[row] = find(val);
      empty = false;
   }

   /**
    * Get the value of a cell in the matrix.
    */
   public Object get(int row, int col) {
      if(row < 0 || col < 0 || col >= matrix.length) {
         return null;
      }

      int[] rowIdxs = matrix[col];

      if(row >= rowIdxs.length) {
         return null;
      }

      return (rowIdxs[row] == 0) ? null : objects[rowIdxs[row] - 1];
   }

   /**
    * Get all values set in the matrix. Some items may be null which means the
    * end of the array.
    */
   public Object[] getValues() {
      return objects;
   }

   /**
    * Check if the matrix is empty.
    */
   public boolean isEmpty() {
      return empty;
   }

   /**
    * Insert n rows at idx.
    */
   public void insertRow(int idx, int n) {
      rowIdxs = insert(rowIdxs, idx, n);

      for(int i = 0; i < matrix.length; i++) {
         matrix[i] = insert(matrix[i], idx, n);
      }
   }

   /**
    * Insert n columns at idx.
    */
   public void insertColumn(int idx, int n) {
      colIdxs = insert(colIdxs, idx, n);
      matrix = insert(matrix, idx, n);
   }

   /**
    * Remove n rows at idx.
    */
   public void removeRow(int idx, int n) {
      remove(rowIdxs, idx, n);

      for(int i = 0; i < matrix.length; i++) {
         remove(matrix[i], idx, n);
      }
   }

   /**
    * Remove n columns at idx.
    */
   public void removeColumn(int idx, int n) {
      remove(colIdxs, idx, n);
      remove(matrix, idx, n);
   }

   /**
    * Expand array to the new size.
    */
   private int[] expand(int[] arr, int n) {
      int[] narr = new int[n * 2];

      System.arraycopy(arr, 0, narr, 0, arr.length);

      return narr;
   }

   /**
    * Remove n items from the array at the idx.
    */
   private void remove(int[] arr, int idx, int n) {
      if(idx >= arr.length) {
         return;
      }

      if(idx + n >= arr.length) {
         n = arr.length - idx;
      }

      System.arraycopy(arr, idx + n, arr, idx, arr.length - idx - n);

      for(int i = 0; i < n; i++) {
         arr[arr.length - i - 1] = (int) 0;
      }
   }

   /**
    * Remove n items from the array at the idx.
    */
   private void remove(int[][] arr, int idx, int n) {
      if(idx >= arr.length) {
         return;
      }

      if(idx + n >= arr.length) {
         n = arr.length - idx;
      }

      System.arraycopy(arr, idx + n, arr, idx, arr.length - idx - n);

      for(int i = 0; i < n; i++) {
         arr[arr.length - i - 1] = new int[0];
      }
   }

   /**
    * Insert n items to the array at the idx.
    */
   private int[] insert(int[] arr, int idx, int n) {
      if(idx >= arr.length) {
         return arr;
      }

      int[] narr = new int[arr.length + n];
      System.arraycopy(arr, 0, narr, 0, idx);
      System.arraycopy(arr, idx, narr, idx + n, arr.length - idx);

      return narr;
   }

   /**
    * Insert n items to the array at the idx.
    */
   private int[][] insert(int[][] arr, int idx, int n) {
      if(idx >= arr.length) {
         return arr;
      }

      int[][] narr = new int[arr.length + n][0];
      System.arraycopy(arr, 0, narr, 0, idx);
      System.arraycopy(arr, idx, narr, idx + n, arr.length - idx);

      return narr;
   }

   /**
    * Find or insert an object into the index. Index is based on 1.
    */
   private int find(Object obj) {
      int i = 0;

      for(i = 0; i < objects.length && objects[i] != null; i++) {
         if(objects[i].equals(obj)) {
            return (i + 1);
         }
      }

      if(i >= 126) {
         if(compressObjects()) {
            return find(obj);
         }

         LOG.debug("Too many different types of " +
            obj.getClass());
      }

      if(i >= objects.length) {
         Object[] nobjects = new Object[objects.length + 2];

         System.arraycopy(objects, 0, nobjects, 0, objects.length);
         objects = nobjects;
      }

      objects[i] = obj;

      return i + 1;
   }

   /**
    * Compress and remove unused objects.
    */
   private boolean compressObjects() {
      BitSet used = new BitSet();

      setMask(used, rowIdxs);
      setMask(used, colIdxs);

      for(int i = 0; i < matrix.length; i++) {
         setMask(used, matrix[i]);
      }

      boolean compressed = false;

      for(int i = 0; i < objects.length; i++) {
         if(objects[i] != null && !used.get(i)) {
            objects[i] = null;
            compressed = true;
         }
      }

      // remove the null values from the array
      if(compressed) {
         Object[] narr = new Object[objects.length];
         Map<Integer, Integer> maskMap = new HashMap<>();

         for(int i = 0, n = 0; i < objects.length; i++) {
            if(objects[i] != null) {
               maskMap.put((int) i, (int) n);
               narr[n++] = objects[i];
            }
         }

         objects = narr;
         // @by davyc, when data compressed, the original index is wrong,
         // reset those mask index
         // fix bug1295861518462, bug1295851727740
         setMask(maskMap, rowIdxs);
         setMask(maskMap, colIdxs);

         for(int i = 0; i < matrix.length; i++) {
            setMask(maskMap, matrix[i]);
         }
      }

      return compressed;
   }

   /**
    * Set the bit of all used index.
    */
   private void setMask(BitSet mask, int[] arr) {
      for(int i = 0; i < arr.length; i++) {
         if(arr[i] > 0) {
            mask.set(arr[i] - 1);
         }
      }
   }

   /**
    * Reset mask after objects compressed.
    */
   private void setMask(Map<Integer, Integer> maskMap, int[] arr) {
      for(int i = 0; i < arr.length; i++) {
         Integer nmask = maskMap.get(arr[i] - 1);

         if(nmask != null) {
            arr[i] = nmask + 1;
         }
      }
   }

   @Override
   public Object clone() {
      try {
         IndexedSparseMatrix obj = (IndexedSparseMatrix) super.clone();

         if(objects != null) {
            obj.objects = objects.clone();
         }

         if(rowIdxs != null) {
            obj.rowIdxs = rowIdxs.clone();
         }

         if(colIdxs != null) {
            obj.colIdxs = colIdxs.clone();
         }

         if(matrix != null) {
            obj.matrix = matrix.clone();

            for(int i = 0; i < matrix.length; i++) {
               if(matrix[i] != null) {
                  obj.matrix[i] = matrix[i].clone();
               }
            }
         }

         return obj;
      }
      catch(Exception ex) {
         LOG.error("Failed to clone SparseIndexedMatrix", ex);
      }

      return null;
   }

   private Object[] objects = new Object[4];
   private int[] rowIdxs = {};
   private int[] colIdxs;
   private int[][] matrix;
   /**
    * no data in the matrix
    */
   private boolean empty = true;

   private static final Logger LOG =
      LoggerFactory.getLogger(IndexedSparseMatrix.class);
}

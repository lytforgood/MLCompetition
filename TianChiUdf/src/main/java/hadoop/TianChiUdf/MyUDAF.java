package hadoop.TianChiUdf;

import com.aliyun.odps.udf.UDAF;
import com.aliyun.odps.udf.UDAFEvaluator;

/***
 * BASE UDAF
 */
public class MyUDAF extends UDAF {
    /**
     * 保存中间结果的类
     * 
     * 中间结果必须能合并，并且空间复杂度为O(1)，不能随着数据量而膨胀
     */
    public static class PartialResult {
        // TODO: 按需定义中间结果

        public Long sum;
    }

    /**
     * 实际执行计算的内联类，必须实现UDAFEvaluator
     */
    public static class VarianceEvaluator implements UDAFEvaluator {
        private PartialResult partial;

        /**
         * 构造函数中生成PartialResult对象
         */
        public VarianceEvaluator() {
            partial = new PartialResult();
        }

        /**
         * UDAF Init接口
         * 
         * 对中间数据对象字段初始化
         */
        public void init() {
            // TODO: 初始化中间结果字段

            partial.sum = 0L;
        }

        /**
         * UDAF Iterate接口
         * 
         * 对每条记录调用一次，这里是讲新的实数合并到中间结果
         */
        public void iterate(Long x) {
            // TODO: 按需定义参数并实现自己的逻辑

            if (x == null) {
                return;
            }

            partial.sum += x;
        }

        /**
         * UDAF TerminatePartial接口
         * 
         * 当一个节点完成计算时，节点的最终结果
         * 
         * @return 节点最终结果
         */
        public PartialResult terminatePartial() {
            return partial;
        }

        /**
         * UDAF Merge接口
         * 
         * 讲另外一个节点的最终结果合并到本节点
         * 
         * @param pr
         *            另外节点的最终结果
         */
        public void merge(PartialResult pr) {
            // TODO: 实现自己的中间结果合并逻辑

            if (pr == null) {
                return;
            }

            partial.sum += pr.sum;
        }

        /**
         * UDAF Terminate接口
         * 
         * 全部任务完成后，输出最终结果。
         * 
         * @return 最终结果
         */
        public Long terminate() {
            // TODO: 实现自己的终结逻辑

            if (partial.sum == null) {
                return 0L;
            }

            return partial.sum;
        }

        /**
         * UDAF SetPartial接口
         * 
         * 对一个新的节点设置中间结果
         * 
         * @param pr
         *            中间结果
         */
        public void setPartial(PartialResult pr) {
            if (pr == null) {
                return;
            }

            partial = pr;
        }
    }
}

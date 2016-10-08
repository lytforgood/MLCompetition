package hadoop.TianChiUdf.examples;

import com.aliyun.odps.udf.UDAF;
import com.aliyun.odps.udf.UDAFEvaluator;

/***
 * 计算一组实数的方差
 */
public class VarianceUDAF extends UDAF {
	/**
	 * 保存中间结果的类
	 * 
	 * 中间结果必须能合并，并且空间复杂度为O(1)，不能随着数据量而膨胀
	 */
	public static class PartialResult {
		public Double sum; // 所有实数的和
		public Double squaredSum; // 所有实数的平方和
		public Long n; // 实数的数量
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
			partial.n = 0L;
			partial.squaredSum = 0D;
			partial.sum = 0D;
		}

		/**
		 * UDAF Iterate接口
		 * 
		 * 对每条记录调用一次，这里是讲新的实数合并到中间结果
		 * 
		 * @param x
		 *            实数
		 */
		public void iterate(Double x) {
			if (x == null) {
				return;
			}

			partial.n++;
			partial.squaredSum += x * x;
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
			if (pr == null) {
				return;
			}

			partial.n += pr.n;
			partial.squaredSum += pr.squaredSum;
			partial.sum += pr.sum;
		}

		/**
		 * UDAF Terminate接口
		 * 
		 * 全部任务完成后，输出最终结果。最终结果可以是中间结果的简单函数 这里采用Var(X)=E(X^2)-(E(X))^2得到最终结果
		 * 
		 * @return 最终结果
		 */
		public Double terminate() {
			if (partial.n == 0) {
				return 0D;
			}

			double avg = partial.sum / partial.n;
			return partial.squaredSum / partial.n - avg * avg;
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

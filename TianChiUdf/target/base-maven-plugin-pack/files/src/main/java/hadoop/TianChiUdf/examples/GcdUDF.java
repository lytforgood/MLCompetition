package hadoop.TianChiUdf.examples;

import com.aliyun.odps.udf.UDF;

/***
 * 计算两个整数的最大公约数
 * 
 * 采用Euclidean算法
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Euclidean_algorithm">Euclidean
 *      algorithm</a>
 */
public final class GcdUDF extends UDF {
	/**
	 * UDF Evaluate接口
	 * 
	 * UDF在记录层面上是一对一，字段上是一对一或多对一。 Evaluate方法在每条记录上被调用一次，输入为一个或多个字段，输出为一个字段
	 * 
	 * @param a
	 *            整数a
	 * @param b
	 *            整数b
	 * @return a和b的最大公约数
	 */
	public Long evaluate(Long a, Long b) {
		if (a == null || b == null) {
			return 0L;
		}

		Long t;
		while (b != 0) {
			t = b;
			b = a % b;
			a = t;
		}

		return a;
	}
}

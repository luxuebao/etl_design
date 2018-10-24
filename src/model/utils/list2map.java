package model.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class list2map {
	/**
	 * 将List<V>按照V的某个方法返回值（返回值必须为K类型）分组，合入到Map<K, List<V>>中<br>
	 * 要保证入参的method必须为V的某一个有返回值的方法，并且该返回值必须为K类型
	 * 
	 * @param list
	 *            待分组的列表
	 * @param map
	 *            存放分组后的map
	 * @param method
	 *            方法
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> void listGroup2Map(List<V> list, Map<K, List<V>> map, Method method) {
		// 入参非法行校验
		if (null == list || null == map || null == method) {
			System.out.print("CommonUtils.listGroup2Map 入参错误，list：" + list + " ；map：" + map + " ；method：" + method);
			return;
		}

		try {
			// 开始分组
			Object key;
			List<V> listTmp;
			for (V val : list) {
				key = method.invoke(val);
				listTmp = map.get(key);
				if (null == listTmp) {
					listTmp = new ArrayList<V>();
					map.put((K) key, listTmp);
				}
				listTmp.add(val);
			}
		} catch (Exception e) {
			System.out.print("分组失败！");
		}
	}

}

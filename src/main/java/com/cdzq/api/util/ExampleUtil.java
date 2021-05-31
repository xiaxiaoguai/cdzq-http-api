package com.cdzq.api.util;



import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.List;

public class ExampleUtil {

	public static Example getExample(Class c, HttpServletRequest request) {
		Example example = new Example(c);
		Criteria criteria = example.createCriteria();
		String _data = request.getParameter("data");
		if (StrUtil.isNotBlank(_data)) {
			try {
				_data = java.net.URLDecoder.decode(_data, "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
			String[] data = _data.split("&");
			for (int i = 0; i < data.length; i++) {
				if (data[i].split("=")[0].contains("_searchop"))
					continue;
				String[] _field = data[i].split("=");
				if (_field.length < 2)
					continue;
				String field = _field[0];
				String fieldvalue = _field[1];
				String[] _op = data[i + 1].split("=");
				String op = "eq";
				if (_op.length > 1)
					op = _op[1];
				switch (op) {
				case "eq":
					criteria.andEqualTo(field, fieldvalue);
					break;
				case "like":
					criteria.andLike(field, "%" + fieldvalue + "%");
					break;
				case "likegt":
					criteria.andLike(field, fieldvalue + "%");
					break;
				case "gt":
					criteria.andGreaterThan(field, fieldvalue);
					break;
				case "gteq":
					criteria.andGreaterThanOrEqualTo(field, fieldvalue);
					break;
				case "lt":
					criteria.andLessThan(field, fieldvalue);
					break;
				case "lteq":
					criteria.andLessThanOrEqualTo(field, fieldvalue);
					break;
				default:
					criteria.andEqualTo(field, fieldvalue);
				}
			}
		}
		return example;
	}

	public static int getRows(HttpServletRequest request) {
		int rows = 10;
		if (StrUtil.isNotBlank(request.getParameter("rows"))) {
			rows = Integer.parseInt(request.getParameter("rows"));
		}
		return rows;
	}

	public static int getPage(HttpServletRequest request) {
		int page = 1;
		if (StrUtil.isNotBlank(request.getParameter("page"))) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		return page;
	}

	public static <T> PageInfo<T> getPageInfo(List<T> tlist) {
		PageInfo<T> pageinfo = new PageInfo<T>(tlist);
		return pageinfo;
	}
}

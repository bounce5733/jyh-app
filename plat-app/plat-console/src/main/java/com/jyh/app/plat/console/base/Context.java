package com.jyh.app.plat.console.base;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.jyh.app.plat.console.dao.CodeItemMapper;
import com.jyh.app.plat.console.dao.CodeMapper;
import com.jyh.entity.plat.console.Code;
import com.jyh.entity.plat.console.CodeItem;

/**
 * 应用上下文<br>
 * 用作系统全局数据初始化，缓存及服务调用
 * 
 * @author jiangyonghua
 * @date 2017年6月15日 下午1:47:47
 */
@Component
public class Context {

	private static final Logger log = LoggerFactory.getLogger(Context.class);

	@Autowired
	private CodeMapper codeMapper;

	@Autowired
	private CodeItemMapper codeItemMapper;

	/**
	 * 加载系统码表缓存{type:[codes]}
	 */
	@PostConstruct
	@Cacheable("plat_console_codes")
	public Map<String, List<CodeItem>> loadCodes() {
		log.info("加载码表{type:[codes]}缓存...");
		Map<String, List<CodeItem>> codeCache = new HashMap<String, List<CodeItem>>();
		// 组装码表数结构
		List<Code> codes = codeMapper.selectAll();
		for (Code code : codes) {
			CodeItem param = new CodeItem();
			param.setType(code.getCode());
			List<CodeItem> items = codeItemMapper.select(param);

			List<CodeItem> topCodes = items.stream().filter(item -> "0".equals(item.getPid())).sorted()
					.collect(Collectors.toList());

			makeCodeTree(topCodes, items);

			// 为各条目添加主键路径
			for (CodeItem topCode : topCodes) {
				topCode.makePath(new LinkedList<String>());
			}
			codeCache.put(String.valueOf(code.getCode()), topCodes);
		}
		return codeCache;
	}

	/**
	 * 加载所有码表键值对缓存map（{id:{path:'',name:''}})
	 * 
	 */
	@PostConstruct
	@Cacheable("plat_console_code_path_map")
	public Map<String, Map<String, Object>> loadCodePathMap() {
		log.info("加载码表{id:{path:'',name:''}}映射缓存...");
		Map<String, List<CodeItem>> codeCache = new HashMap<String, List<CodeItem>>();
		// 组装码表数结构
		List<Code> codes = codeMapper.selectAll();
		for (Code code : codes) {
			CodeItem param = new CodeItem();
			param.setType(code.getCode());
			List<CodeItem> items = codeItemMapper.select(param);

			List<CodeItem> topCodes = items.stream().filter(item -> "0".equals(item.getPid())).sorted()
					.collect(Collectors.toList());

			makeCodeTree(topCodes, items);

			// 为各项目添加主键路径
			for (CodeItem topCode : topCodes) {
				topCode.makePath(new LinkedList<String>());
			}

			codeCache.put(code.getCode(), topCodes);
		}

		Map<String, Map<String, Object>> codemap = new HashMap<String, Map<String, Object>>();

		// 组装码表主键映射map
		for (String key : codeCache.keySet()) {
			for (CodeItem code : codeCache.get(key)) {
				makeCodeMap(codemap, code);
			}
		}
		return codemap;
	}

	/**
	 * 组装编码名称与路径
	 * 
	 * @param codemap
	 * @param code
	 */
	private void makeCodeMap(Map<String, Map<String, Object>> codemap, CodeItem code) {
		Map<String, Object> codeMapItem = new HashMap<String, Object>();
		codeMapItem.put("name", code.getName());
		codeMapItem.put("path", code.getPath());
		code.setPath(null);
		codemap.put(code.getId(), codeMapItem);
		if (code.getChildren() != null) {
			for (CodeItem item : code.getChildren()) {
				makeCodeMap(codemap, item);
			}
		}
	}

	/**
	 * 递归组装编码树
	 * 
	 * @param pItems
	 *            父编码列表
	 * @param allItems
	 *            总编码列表
	 */
	private void makeCodeTree(List<CodeItem> pItems, List<CodeItem> allItems) {
		for (CodeItem pItem : pItems) {
			// 收集下级子菜单
			List<CodeItem> child = allItems.stream().filter(item -> item.getPid().equals(pItem.getId())).sorted()
					.collect(Collectors.toList());
			if (child.size() > 0) {
				// 子菜单列表排序
				pItem.setChildren(child);
				makeCodeTree(child, allItems);
			}
		}
	}
}

package com.bestomb.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.DictException;
import com.bestomb.dao.IDictDao;
import com.bestomb.entity.Dict;
import com.bestomb.service.IDictService;

/***
 * 字典接口实现类
 * @author qfzhang
 *
 */
@Service
public class DictServiceImpl implements IDictService {
	
	private Logger logger = Logger.getLogger(this.getClass());
	// 定义静态字典集合
	private static Map<String, Map<Integer, Dict>> dictCollection = new HashMap<String, Map<Integer,Dict>>();
	
	@Autowired
	private IDictDao dictDao;
	
	public List<Dict> getDictList(String dictType) throws DictException {
		if (dictCollection.isEmpty()) { init(); }
		if (StringUtils.isEmpty(dictType)) {
			logger.warn("获取字典集合数据时，dictType为空");
			throw new DictException(ExceptionMsgConstant.DICTTYPE_IS_EMPTY);
		}
		List<Dict> dictList = new LinkedList<Dict>();
		if (!dictCollection.containsKey(dictType)) {
			throw new DictException(ExceptionMsgConstant.DICTDATA_IS_NOT_EXISTS);
		}
		for (Map.Entry<Integer, Dict> entry : getDictMap(dictType).entrySet()) {
			dictList.add(entry.getValue());
		}
		return dictList;
	}

	public Map<Integer, Dict> getDictMap(String dictType) throws DictException {
		if (dictCollection.isEmpty()) { init(); }
		if (StringUtils.isEmpty(dictType)) {
			logger.warn("获取字典集合数据时，dictType为空");
			throw new DictException(ExceptionMsgConstant.DICTTYPE_IS_EMPTY);
		}
		if (dictCollection.containsKey(dictType)) {
			return dictCollection.get(dictType);
		}else{
			logger.warn("获取字典集合数据时，字典集合中无此字典类型："+dictType);
			throw new DictException(ExceptionMsgConstant.DICTDATA_IS_NOT_EXISTS);
		}
	}

	public Map<Integer, String> getDictValueMap(String dictType) throws DictException {
		if (dictCollection.isEmpty()) { init(); }
		if (StringUtils.isEmpty(dictType)) {
			logger.warn("获取字典集合数据时，dictType为空");
			throw new DictException(ExceptionMsgConstant.DICTTYPE_IS_EMPTY);
		}
		if (dictCollection.containsKey(dictType)) {
			Map<Integer, Dict> map = dictCollection.get(dictType);
			Map<Integer, String> resultMap = new LinkedHashMap<Integer, String>();
			for (Map.Entry<Integer, Dict> entry : map.entrySet()) {
				resultMap.put(entry.getKey(), entry.getValue().getDictValue());
			}
			return resultMap;
		}else{
			logger.warn("获取字典集合数据时，字典集合中无此字典类型："+dictType);
			throw new DictException(ExceptionMsgConstant.DICTDATA_IS_NOT_EXISTS);
		}
	}

	public Dict getDict(String dictType, Integer dictCode) throws DictException {
		if (dictCollection.isEmpty()) { init(); }
		if (StringUtils.isEmpty(dictType)) {
			logger.warn("获取字典集合数据时，dictType为空");
			throw new DictException(ExceptionMsgConstant.DICTTYPE_IS_EMPTY);
		}
		if (ObjectUtils.isEmpty(dictCode)) {
			logger.warn("获取字典集合数据时，dictCode为空");
			throw new DictException(ExceptionMsgConstant.DICTCODE_IS_EMPTY);
		}
		if (!dictCollection.containsKey(dictType) || !dictCollection.get(dictType).containsKey(dictCode) ) {
			throw new DictException(ExceptionMsgConstant.DICTDATA_IS_NOT_EXISTS);
		}
		return dictCollection.get(dictType).get(dictCode);
	}

	public String getDictValue(String dictType, Integer dictCode) throws DictException {
		if (dictCollection.isEmpty()) { init(); }
		if (StringUtils.isEmpty(dictType)) {
			logger.warn("获取字典集合数据时，dictType为空");
			throw new DictException(ExceptionMsgConstant.DICTTYPE_IS_EMPTY);
		}
		if (ObjectUtils.isEmpty(dictCode)) {
			logger.warn("获取字典集合数据时，dictCode为空");
			throw new DictException(ExceptionMsgConstant.DICTCODE_IS_EMPTY);
		}
		if (!dictCollection.containsKey(dictType) || !dictCollection.get(dictType).containsKey(dictCode) ) {
			throw new DictException(ExceptionMsgConstant.DICTDATA_IS_NOT_EXISTS);
		}
		return dictCollection.get(dictType).get(dictCode).getDictValue();
	}
	
	// 初始化字典集合数据
	private void init(){
		List<Dict> dictList = null;
		try {
			dictList = dictDao.getAllDicts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Dict dict : dictList) {
			if (dictCollection.containsKey(dict.getDictType())) {
				dictCollection.get(dict.getDictType()).put(dict.getDictCode(), dict);
			}else{
				Map<Integer, Dict> map = new LinkedHashMap<Integer, Dict>();
				map.put(dict.getDictCode(), dict);
				dictCollection.put(dict.getDictType(), map);
			}
		}
	}
	
}

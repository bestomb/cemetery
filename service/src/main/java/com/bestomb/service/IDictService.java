package com.bestomb.service;

import java.util.List;
import java.util.Map;

import com.bestomb.common.exception.DictException;
import com.bestomb.entity.Dict;

/***
 * 字典接口
 * @author qfzhang
 *
 */
public interface IDictService {
	
	/***
	 * 根据字典类型获取字典集合
	 * @param dictType
	 * @return
	 * @throws DictException
	 */
	List<Dict> getDictList(String dictType) throws DictException;
	
	/***
	 * 根据字典类型获取字典Map
	 * @param dictType
	 * @return
	 * @throws DictException
	 */
	Map<Integer, Dict> getDictMap(String dictType) throws DictException;
	
	/***
	 * 根据字典类型获取字典值Map
	 * @param dictType
	 * @return
	 * @throws DictException
	 */
	Map<Integer, String> getDictValueMap(String dictType) throws DictException;
	
	/***
	 * 根据字典类型和字典代码获取该代码字典信息
	 * @param dictType
	 * @param dictCode
	 * @return
	 * @throws DictException
	 */
	Dict getDict(String dictType, String dictCode) throws DictException;
	
	/***
	 * 根据字典类型和字典代码获取该代码字典值
	 * @param dictType
	 * @param dictCode
	 * @return
	 * @throws DictException
	 */
	String getDictValue(String dictType, String dictCode) throws DictException;
}

package com.shimh.dao.user;

import com.shimh.common.entity.PageDto;
import com.shimh.entity.User;
import com.shimh.mapper.user.SysUserMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:  逻辑服务类<br/>
 * 不做业务校验，参数校验统一放到前置的Service中
 * @author zhounan
 * @version 1.0
 * @date: 2020-09-17 23:41:00
 * @since JDK 1.8
 */
@Repository(value = "sysUserDao")
public class SysUserDao  {

	@Resource
	private SysUserMapper sysUserMapper;

	/**可选插入返回主键*/
	public Long insertSysUser(User entity) {
		Long id = null;

			sysUserMapper.insertSelective(entity);
		id = entity.getId();

		return id;
	}

	/**批量插入*/
	public void insertSysUsers(List<User> records) {

			sysUserMapper.insertRecords(records);

	}

	/**条件查询返回匹配的第一条记录*/
	public User querySysUserLimitOne(User entity) {

		return sysUserMapper.queryLimitOne(entity);
	}

	/**条件查询*/
	public List<User> queryByCond(User entity) {
		return sysUserMapper.queryByCond(entity);
	}

	/**分页查询 若记录为空则对象中的记录数为空列表*/
	public PageDto<User> pageQueryByCond(User entity, int pageNum, int pageSize) {
		PageDto<User> result = null;
		entity.setPage(pageSize,pageNum);
		int count =sysUserMapper.countByCond(entity);
		List<User> list=sysUserMapper.queryByCond(entity);
				result = new PageDto<>(pageSize, pageNum, list,count);
		return result;
	}

	/**主键查询 可能返回Null*/
	public User querySysUserById (Long id) {
		return sysUserMapper.queryById(id);
	}

	/**主键批量查询 可能返回空列表*/
	public List<User> querySysUserByIds (List<Long> ids) {

		return sysUserMapper.queryByIds(ids);
	}

	/**主键更新*/
	public boolean updateSysUserById (User entity) {
		return sysUserMapper.updateSysUserById(entity)>0 ;
	}

	/**主键删除*/
	public boolean deleteSysUserById (Long id) {
		return sysUserMapper.deleteSysUserById(id) > 0;
	}

}

package com.shimh.mapper.user;

import com.shimh.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Description:  Dao<br/>
 *
 * @author zhounan
 * @version 1.0
 * @date: 2020-09-17 23:41:00
 * @since JDK 1.8
 */
@Mapper
public interface SysUserMapper {

	/**可选插入返回主键*/
	Long insertSelective(User entity);

	/**批量插入返回影响记录数*/
	int insertRecords(@Param("records") List<User> records);

	User queryLimitOne(User entity);

	/**批量主键查询*/
	List<User> queryByIds(@Param("keys") List<Long> ids);

	/**条件查询*/
	List<User> queryByCond(User entity);

	/**查询个数*/
	int countByCond (User entity);

	/**主键查询*/
	User queryById (@Param("id") Long id);

	/**主键更新*/
	int updateSysUserById (User entity);

	/**主键删除*/
	int deleteSysUserById (@Param("id") Long id);

	void updateUserCountAll(List<User> userList);

}

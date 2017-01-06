package com.maxrumo.shiro.realm;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.maxrumo.entity.Permission;
import com.maxrumo.entity.Role;
import com.maxrumo.entity.User;
import com.maxrumo.mapper.PermissionMapper;
import com.maxrumo.service.PermissionService;
import com.maxrumo.service.RoleService;
import com.maxrumo.service.UserService;

/**
 * 自定义realm，授权和认证时调用
 * 
 * @author max
 */
public class MyShiroRealm extends AuthorizingRealm {

	public static final String REALM_NAME = MyShiroRealm.class.getName()
			+ ".authorizationCache";
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	/**
	 * 授权方法，鉴别权限,从数据库中查询并加入ehcache缓存中
	 *
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// 当前需要校验权限的用户
		String username = (String) principals.getPrimaryPrincipal();

		// 用户属于的角色
		List<Role> roles = roleService.findRoleByUsername(username);
		// 用户拥有的权限
		List<Permission> permissions = permissionService.findByUsername(username);

		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

		// 将查询出来的角色和权限交给shiro管理
		addRoleAndPermission(simpleAuthorizationInfo, roles, permissions);
		
		return simpleAuthorizationInfo;
	}

	private void addRoleAndPermission(SimpleAuthorizationInfo simpleAuthorizationInfo, List<Role> roles,List<Permission> permissions) {

		if(CollectionUtils.isNotEmpty(roles)){
			for(Role role : roles){
				simpleAuthorizationInfo.addRole(role.getEnName());
			}
		}
		if(CollectionUtils.isNotEmpty(permissions)){
			for(Permission perm : permissions){
				simpleAuthorizationInfo.addStringPermission(perm.getCode());
			}
		}
	}

	/**
	 * 认证方法，登录
	 *
	 * @param authenticationToken
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		String username = token.getUsername();
		User user = userService.findByUsername(username);
		if (user == null) {
			return null;
		}
		return new SimpleAuthenticationInfo(user.getUsername(),
				user.getPassword(), getName());
	}
}

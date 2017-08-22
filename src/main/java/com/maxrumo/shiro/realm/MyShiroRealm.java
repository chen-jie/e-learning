package com.maxrumo.shiro.realm;

import com.maxrumo.entity.Permission;
import com.maxrumo.entity.Role;
import com.maxrumo.entity.User;
import com.maxrumo.service.PermissionService;
import com.maxrumo.service.RoleService;
import com.maxrumo.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
		return new SimpleAuthenticationInfo(user,
				user.getPassword(), getName());
	}
	
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

		User user = userService.findByUsername(username);
		int roleId = user.getRoleId();
		// 用户属于的角色
		Role role = roleService.findById(roleId);
		// 用户拥有的权限
//		List<Permission> permissions = permissionService.findByRoleId(roleId);

		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addRole(role.getEnName());
		// 将查询出来的角色和权限交给shiro管理
//		addRoleAndPermission(simpleAuthorizationInfo, Arrays.asList(role), null);
		
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
				if(StringUtils.isNotBlank(perm.getCode())){
					simpleAuthorizationInfo.addStringPermission(perm.getCode());
				}
			}
		}
	}

}

package com.maxrumo.shiro.realm;

import com.maxrumo.entity.User;
import com.maxrumo.entity.UserExample;
import com.maxrumo.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2016/12/31.
 */
public class MyShiroRealm extends AuthorizingRealm {

    public static final String REALM_NAME = MyShiroRealm.class.getName()+".authorizationCache";
    @Autowired
    private UserMapper userMapper;

    /**
     * 授权方法，鉴别权限,从数据库中查询并加入ehcache缓存中
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("in");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermission("test1");
        simpleAuthorizationInfo.addStringPermission("test/test2");
        return simpleAuthorizationInfo;
    }

    /**
     * 认证方法，登录
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();

        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(users)) {
            return null;
        }
        User user = users.get(0);
        return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
    }
}

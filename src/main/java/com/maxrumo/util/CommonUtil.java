package com.maxrumo.util;

import com.maxrumo.entity.CourseChapter;
import com.maxrumo.entity.Permission;
import com.maxrumo.entity.User;
import com.maxrumo.vo.TreeNode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/31.
 */
public class CommonUtil {
    private CommonUtil() {
    }

    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<TreeNode> convetToTree(List<Permission> permissionList) {
        List<TreeNode> treeList = new ArrayList<TreeNode>();
        for (Permission perm : permissionList) {
            TreeNode node = new TreeNode(perm.getName(), perm.getId(), perm.getParentId(), perm.getType(), perm.getUrl());
            List<Permission> permChildren = perm.getChildren();
            if (CollectionUtils.isNotEmpty(permChildren)) {
                node.setChildren(convetToTree(permChildren));
            }
            treeList.add(node);
        }
        return treeList;
    }
    public static List<TreeNode> convetChaptersToTree(List<CourseChapter> chapterList) {
        List<TreeNode> treeList = new ArrayList<TreeNode>();
        if(CollectionUtils.isNotEmpty(chapterList)){
            Map<Integer,TreeNode> map = new HashMap<Integer, TreeNode>();
            for(CourseChapter chapter : chapterList){
                Integer parentId = chapter.getParentId();
                TreeNode node = new TreeNode();
                node.setTitle(chapter.getTitle());
                node.setId(chapter.getId());
                node.setParentId(parentId);
                if(parentId == null || parentId == 0){
                    map.put(node.getId(),node);
                    treeList.add(node);
                }else{
                    TreeNode parentNode = map.get(parentId);
                    List<TreeNode> childrenList = parentNode.getChildren();
                    if(childrenList == null){
                        childrenList = new ArrayList<TreeNode>();
                    }
                    childrenList.add(node);
                    parentNode.setChildren(childrenList);
                }
            }
        }
        return treeList;

    }

    public static <T> T getFirst(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    public static User getCurrentUser() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user;
    }

    public static Integer getCurrentUserId() {
        User user = getCurrentUser();
        if (user != null) {
            return user.getId();
        }
        return null;
    }
}

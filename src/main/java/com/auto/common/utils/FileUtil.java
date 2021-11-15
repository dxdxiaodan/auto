package com.auto.common.utils;

/**
 * 路径获取
 * @author dxd
 */
public class FileUtil {
    /**
     * 获得资源路径
     * @param name 资源名称
     * @return
     */
    public static String getResourcePath(String name){
        String path = FileUtil.class.getClassLoader().getResource(name).getPath();
        return path.substring(1,path.length());
    }
}

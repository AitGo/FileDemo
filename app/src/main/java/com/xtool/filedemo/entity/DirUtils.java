package com.xtool.filedemo.entity;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by xtool on 2017/11/28.
 */

public class DirUtils {
    private static String ATTRIBUTE_NAME = "name";
    private static String ATTRIBUTE_PARENT = "parent";
    private static String encoding = "UTF-8";

    public static Map<String, List<Dir>> pullDirXml(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser();
        Map<String, List<Dir>> dirMap = new HashMap<>();
        parser.setInput(inputStream, encoding);
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String s = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    List<Dir> dirs = new ArrayList<>();
                    int depth = parser.getDepth();
                    Dir dir = new Dir();
                    dir.setDirName(parser.getAttributeValue(null, ATTRIBUTE_NAME));
                    dir.setLevel(depth);
                    dirs.add(dir);
                    if (dirMap.size() > 0
                            && dirMap.get(parser.getAttributeValue(null, ATTRIBUTE_PARENT)) != null
                            && dirMap.get(parser.getAttributeValue(1)).size() > 0) {
                        //判断parent属性，如果已经有该节点，添加到list中
                        dirMap.get(parser.getAttributeValue(null, ATTRIBUTE_PARENT)).addAll(dirs);
                    } else {
                        dirMap.put(parser.getAttributeValue(null, ATTRIBUTE_PARENT), dirs);
                    }

                    break;
                case XmlPullParser.END_TAG:
                    break;
                case XmlPullParser.TEXT:
                    break;
                case XmlPullParser.END_DOCUMENT:
                    break;
            }
            eventType = parser.next();
        }
        return getDirs(dirMap);
    }

    private static Map<String,List<Dir>> getDirs(Map<String,List<Dir>> map) {
        Set<String> set = map.keySet();
        if(set != null) {
            for(String key : set) {
                List<Dir> roots = map.get(key);
                if(roots != null) {
                    for(Dir dir : roots) {
                        if(map.get(dir.getDirName()) != null && map.get(dir.getDirName()).size() > 0) {
                            dir.setChildren(map.get(dir.getDirName()));
                            for(Dir dir1 : map.get(dir.getDirName())) {
                                dir1.setParent(dir);
                            }
                        }
                    }
                }
            }
        }
        return map;
    }

}

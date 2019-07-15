package com.cnbmtech.cdwpcore.aaa.module.account;  
  
import java.util.ArrayList;  
import java.util.Iterator;  
import java.util.LinkedHashMap;  
import java.util.List;  
import java.util.Map;  
import java.util.Map.Entry;  
import java.util.Set;  
  
public class MaininTree {  
  
    static class Node {  
        // 节点对象，没有按照规范封装，直接.属性即可  
        String code;  
  
        List<Node> children = new ArrayList<Node>();  
        Map<String, Node> childMap = new LinkedHashMap<String, Node>();  
  
        Node() {  
            this.code = "000";  
        }  
  
        Node(String code) {  
            this.code = code;  
        }  
  
        @Override  
        public String toString() {  
            StringBuilder str = new StringBuilder();  
            return printSelf(this, str);  
        }  
  
        public String printSelf(Node n, StringBuilder str) {  
            List<Node> list = n.children;  
            if (list != null && list.size() > 0) {  
                for (int i = 0; i < list.size(); i++) {  
                    Node node = list.get(i);  
                    int childeSize = node.children.size();  
                    String mark = "";  
                    if (childeSize != 0) {  
                        mark = ":";  
                    }  
                    str.append("{code:" + node.code + mark);  
                    printSelf(node, str);  
                    str.append("}");  
                    if (i + 1 != list.size()) {  
                        str.append(",");  
                    }  
                }  
            }  
            return str.toString();  
        }  
    }  
  
    public static void main(String[] args) {  
        Node node = new Node();//  
        initTreeData(node);// 构建树前的初始化平铺数据，模拟数据库查询出的数据  
  
        Map<String, Node> rMap = new LinkedHashMap<String, Node>();// 临时组织数据map  
  
        for (Node thisN : node.children) {  
            turnToMap(rMap, thisN);// 将平铺的数据，解析到map中，构建一颗逻辑树  
        }  
  
        Node root = new Node();// 结果树  
        turnToList(rMap, root);// 递归解析map树，并放入root这个根节点中  
        System.out.println(root);  
        // root既是结果树  
    }  
  
    static void turnToMap(Map<String, Node> rMap, Node n) {  
        String key = null;  
        List<String> keyList = new ArrayList<String>();  
        for (int i = 0; i < n.code.length() / 3; i++) {// 组装code的父级结构  
            key = n.code.substring(0, 3 + (i * 3));  
            keyList.add(key);  
        }  
  
        String thisKey = null;  
        Node tmpNode = null;  
        Map<String, Node> tmpMap = rMap;  
        for (int i = 0; i < keyList.size(); i++) {  
            thisKey = keyList.get(i);  
            tmpNode = tmpMap.get(thisKey);  
            if (i + 1 == keyList.size()) {  
                tmpMap.put(n.code, n);// 如果是末级节点，则放入该节点  
            } else {  
                tmpMap = tmpNode.childMap;// 如果不是末级节点，则将该节点赋值给临时变量  
            }  
        }  
    }  
  
    static void turnToList(Map<String, Node> rMap, Node rn) {  
        Set<Entry<String, Node>> eSet = rMap.entrySet();  
        Iterator<Entry<String, Node>> mIt = eSet.iterator();  
        while (mIt.hasNext()) {  
            Entry<String, Node> entry = mIt.next();  
            Node node = entry.getValue();  
            rn.children.add(node);  
            turnToList(node.childMap, node);  
        }  
    }  
  
    static void initTreeData(Node node) {  
        node.children.add(new Node("001"));  
        node.children.add(new Node("001001"));  
        node.children.add(new Node("001002"));  
        node.children.add(new Node("002"));  
        node.children.add(new Node("002001"));  
        node.children.add(new Node("002002"));  
        node.children.add(new Node("002002001"));  
        node.children.add(new Node("002002002"));  
        node.children.add(new Node("003"));  
        node.children.add(new Node("003001"));  
        node.children.add(new Node("003002"));  
        node.children.add(new Node("003002001"));  
        node.children.add(new Node("003002002"));  
        node.children.add(new Node("003002003"));  
        node.children.add(new Node("003002004"));  
        node.children.add(new Node("003002005"));  
        node.children.add(new Node("003002006"));  
  
        node.children.add(new Node("005"));  
        node.children.add(new Node("005001"));  
        node.children.add(new Node("005001002"));  
        node.children.add(new Node("005001002001"));  
        node.children.add(new Node("005001002002"));  
    }  
  
} 
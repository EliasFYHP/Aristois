package me.xiaoying.serverbuild.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Util Yaml
 */
public class YamlUtil {
    private static Map<String, Map<String, Object>> properties = new HashMap<>();

    public static Object getValueByKey(String path, String key) {
        Yaml yaml = new Yaml();
        try (FileInputStream in = new FileInputStream(path)) {
            properties = yaml.loadAs(in, HashMap.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String separator = ".";
        String[] separatorKeys = null;
        if (key.contains(separator))
            separatorKeys = key.split("\\.");
        else
            return properties.get(key);
        Map<String, Map<String, Object>> finalValue = new HashMap<>();
        for (int i = 0; i < separatorKeys.length - 1; i++) {
            if (i == 0) {
                finalValue = (Map) properties.get(separatorKeys[i]);
                continue;
            }
            if (finalValue == null)
                break;
            finalValue = (Map) finalValue.get(separatorKeys[i]);
        }
        return finalValue == null ? null : finalValue.get(separatorKeys[separatorKeys.length - 1]);
    }

    public static void changeContent(String path, String key, String vault) {
        try {
            if (key.contains(".")) {
                String[] strs = key.split("\\.");
                // 获取更改内容关键节点
                String changeNodeKey = strs[strs.length - 1];

                boolean isFindNode = false;
                FileInputStream fileInputStream = new FileInputStream(path);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder content = new StringBuilder();
                String text;
                int getNodeSize = 0;
                int nodesSpaceSize = 0;
                while ((text = bufferedReader.readLine()) != null) {
                    // 检测一级节点
                    if (text.equalsIgnoreCase(strs[getNodeSize] + ":") && !isFindNode) isFindNode = true;
                    if (isFindNode) {
                        String changeText = StringUtil.removeStartingAllSpace(text);
                        if (changeText.startsWith(strs[getNodeSize] + ":") && StringUtil.getStartingSpaceSize(strs[getNodeSize]) >= nodesSpaceSize) {
                            getNodeSize++;
                            nodesSpaceSize++;
                        } else if (text.equalsIgnoreCase(strs[getNodeSize] + ":") && StringUtil.getStartingSpaceSize(strs[getNodeSize]) < nodesSpaceSize)
                            isFindNode = false;

                        if (StringUtil.removeStartingSpace(text).startsWith(changeNodeKey + ":") && getNodeSize == strs.length - (strs.length - 1) && isFindNode) {
                            text = text.replace(StringUtil.removeStartingAllSpace(text), changeNodeKey + ": " + vault);
                            isFindNode = false;
                        }
                    }
                    content.append(text).append("\n");
                }

                FileOutputStream fileOutputStream = new FileOutputStream(path);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                bufferedWriter.write(content.toString());
                bufferedWriter.close();
                return;
            }
            // 更改单节点内容
            FileInputStream fileInputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder content = new StringBuilder();
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                if (text.startsWith(key)) {
                    text = text.replace(text, key + ": " + vault);
                }
                content.append(text).append("\n");
            }

            FileOutputStream fileOutputStream = new FileOutputStream(path);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(content.toString());
            bufferedWriter.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取子节点
     *
     * @param path 文件地址
     * @param key 需要获取子节点的节点
     * @return 子节点列表
     */
    public static List<Object> getNodes(String path, String key) {
        List<Object> allNodes;
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            HashMap<String, Map<String, Object>> map = new Yaml().loadAs(fileInputStream, HashMap.class);
            Map<String, Object> map1 = new HashMap<>();
            String[] strings = key.split("\\.");
            for (int i = 0; i < strings.length; i++) {
                if (i == 0) {
                    map1 = map.get(strings[i]);
                    continue;
                }

                map1 = (Map<String, Object>) map1.get(strings[i]);
            }
            allNodes = new ArrayList<>(map1.keySet());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return allNodes;
    }

    /**
     * 获取YAML文本的所有一级节点
     *
     * @param path 文件地址
     * @return 子节点列表
     */
    public static List<String> getChildrenNode(String path) {
        List<String> allNodes = new ArrayList<>();

        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            HashMap<String, Map<String, Object>> map = new Yaml().loadAs(fileInputStream, HashMap.class);
            allNodes.addAll(map.keySet());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return allNodes;
    }

    /**
     * 获取string为list
     *
     * @param configuration YamlConfiguration
     * @param path 键值
     * @return ArrayList
     */
    public static List<String> getStringList(YamlConfiguration configuration, String path) {
        List<String> function = configuration.getStringList(path);
        if (function.isEmpty())
            function.add(configuration.getString(path));
        return function;
    }
}
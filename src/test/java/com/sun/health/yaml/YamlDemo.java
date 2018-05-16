package com.sun.health.yaml;

import org.junit.Test;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.Properties;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 华硕 on 2018-05-16.
 */
public class YamlDemo {

    @Test
    public void test1() {
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        Properties properties = new Properties();
        try {
            fileInputStream = new FileInputStream("application.yml");
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            Pattern pattern = Pattern.compile("( *)(\\w*):(.*)");
            Matcher matcher = null;
            Stack<String> stack = new Stack<String>();
            int lineNo = 0;
            while((line = bufferedReader.readLine()) != null) {
                lineNo += 1;
                if(StringUtils.isEmpty(line) || line.startsWith("#")) {
                    continue;
                }
                matcher = pattern.matcher(line);
                if(matcher.matches()) {
                    int tabNum = matcher.group(1).length() / 2;
                    int size = stack.size();
                    for(int i = 0; i < (size -tabNum); i++) {
                        stack.pop();
                    }
                    stack.push(matcher.group(2));
                    String value = matcher.group(3).trim();
                    if(!StringUtils.isEmpty(value)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (String s : stack) {
                            stringBuilder.append(s + ".");
                        }
                        properties.put(stringBuilder.substring(0, stringBuilder.length() - 1), value);
                    }
                } else {
                    throw new RuntimeException("第" + lineNo + "行\"" + line + "\"格式错误...");
                }
            }
            System.out.println(properties);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

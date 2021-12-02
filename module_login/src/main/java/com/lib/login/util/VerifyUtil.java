package com.lib.login.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wengyiheng
 * @date 2021/8/24.
 * description：
 */
public class VerifyUtil {

    static final String secert="c4fc9067d07349da83c4536db33b90t5";


    //    uid=123456 timestamp=15962
    public static String md5Hex(Map<String,String> params){
        Set<String> keys = params.keySet();

        List<String > list = new ArrayList<String>();
        for(String key: keys){
            if(!"signature".equals(key)){
                list.add(key);
            }
        }
        Collections.sort(list);
        StringBuilder sb = new StringBuilder();
        for(String key:list){
            String value = String.valueOf(params.get(key));
            sb.append(key+"="+value+"&");
        }
        sb.deleteCharAt(sb.length()-1);
        String signature =new String(Hex.encodeHex(DigestUtils.md5(secert+sb.toString())));
        return signature;
    }




}
package com.xd.springboot.product.test;

import com.xd.springboot.common.util.AES;
import com.xd.springboot.common.util.Base64;
import com.xd.springboot.common.util.HttpUtil;
import com.xd.springboot.common.util.MD5;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class TestGateway {
    public static void main(String[] args)throws Exception {
        Map<String,String> params = new HashMap<>();
        String YKD_QueryApplyInfo = "{\"head\":{\"corp_code\":\"coopr_01\",\"timestamp\":\"1590025684846\",\"business_code\":\"YKD_QueryApplyInfo\"},\"body\":{\"openId\":\"coopr_01347067691573972992\",\"releaseNo\":\"YKD2020052009072483665\"}}";
        String YKD_QueryApplyInfo2 = "{\"head\":{\"corp_code\":\"coopr_01\",\"timestamp\":\"1590025684846\",\"business_code\":\"YKD_QueryApplyInfo\"},\"body\":{\"openId\":\"coopr_01347067691573972992\",\"releaseNo\":\"YKD2020052010385697470\"}}";
        String YKD_QueryApplyInfo1 = "{\"head\":{\"corp_code\":\"XYJK_001_TyFQ\",\"timestamp\":\"1504700539422\",\"business_code\":\"YKD_QueryApplyInfo\"},\"body\":{\"openId\":\"msyd07345X10000804\",\"releaseNo\":\"YKD2020041015530079552\"}}";
        String YKD_QueryApplyInfoList = "{\"head\":{\"corp_code\":\"coopr_01\",\"timestamp\":\"1504700539422\",\"business_code\":\"YKD_QueryApplyInfoList\"},\"body\":{\"openId\":\"coopr_01347067691573972992\"}}";
        String YKD_QueryApplyInfoList1 = "{\"head\":{\"corp_code\":\"XYJK_001_TyFQ\",\"timestamp\":\"1504700539422\",\"business_code\":\"YKD_QueryApplyInfoList\"},\"body\":{\"openId\":\"msyd07345X10000804\"}}";

        String YKD_QueryApplyInfo3 = "{\"head\":{\"corp_code\":\"coopr_01\",\"timestamp\":\"1590025684846\",\"business_code\":\"YKD_QueryApplyInfo\"},\"body\":{\"openId\":\"coopr_01356784955869106176\",\"releaseNo\":\"YKD2020052109411272583\"}}";
        String YKD_QueryApplyInfoList3 = "{\"head\":{\"corp_code\":\"coopr_01\",\"timestamp\":\"1504700539422\",\"business_code\":\"YKD_QueryApplyInfoList\"},\"body\":{\"openId\":\"coopr_01356784955869106176\"}}";

        String YKD_QueryApplyInfo4 = "{\"head\":{\"corp_code\":\"coopr_01\",\"timestamp\":\"1590025684846\",\"business_code\":\"YKD_QueryApplyInfo\"},\"body\":{\"openId\":\"coopr_01357226025400668160\",\"releaseNo\":\"YKD2020052509431168623\"}}";
        String YKD_QueryApplyInfoList4 = "{\"head\":{\"corp_code\":\"coopr_01\",\"timestamp\":\"1504700539422\",\"business_code\":\"YKD_QueryApplyInfoList\"},\"body\":{\"openId\":\"coopr_01347067691573972992\"}}";

        String YKD_QueryApplyInfo5 = "{\"head\":{\"corp_code\":\"coopr_01\",\"timestamp\":\"1590025684846\",\"business_code\":\"YKD_QueryApplyInfo\"},\"body\":{\"openId\":\"coopr_01347067691573972992\",\"releaseNo\":\"YKD2020052009072483665\"}}";
        String YKD_QueryApplyInfoList5 = "{\"head\":{\"corp_code\":\"coopr_01\",\"timestamp\":\"1504700539422\",\"business_code\":\"YKD_QueryApplyInfoList\"},\"body\":{\"openId\":\"coopr_01347067691573972992 \"}}";

        String YKD_QueryApplyInfo6 = "{\"head\":{\"corp_code\":\"coopr_01\",\"timestamp\":\"1590025684846\",\"business_code\":\"YKD_QueryApplyInfo\"},\"body\":{\"openId\":\"coopr_01357226025400668160\",\"releaseNo\":\"YKD2020060211134617967\"}}";

        String YKD_QueryApplyInfo7 = "{\"head\":{\"corp_code\":\"coopr_01\",\"timestamp\":\"1590025684846\",\"business_code\":\"YKD_QueryApplyInfo\"},\"body\":{\"openId\":\"coopr_01357226025400668160\",\"releaseNo\":\"YKD2020060213444142110\"}}";

        String YKD_QueryApplyInfo8 = "{\"head\":{\"corp_code\":\"corp_cz\",\"timestamp\":\"1590025684846\",\"business_code\":\"YKD_QueryApplyInfo\"},\"body\":{\"openId\":\"coopr_01357226025400668160\",\"releaseNo\":\"YKD2020060213444142110\"}}";

        //String param = String.format("{\"head\": {\"business_code\": \"XDGW_USER_SENDBANKCODE\",\"corp_code\": \"XYJK_001_TyFQ\",\"timestamp\":\"%d\"},\"body\": {\"bank_mobile\": \"17318600048\",\"id_no\": \"510107199403076175\",\"bankcard_no\": \"6226200105092010\",\"bank_name\": \"中国民生银行\",\"bank_code\": \"305\",\"cust_name\": \"李白四八\"}}", 1504966828748L);
        //String mac = MD5.encodeByMd5WithSalt(YKD_QueryApplyInfo,"de9d49b9a0feba383b5c9f37fd8f76b1");
        String mac = MD5.encodeByMd5WithSalt(YKD_QueryApplyInfo7,"8aa16b3b-fbbd-424e-badf-788ccce0d1b1");
        //String data = AES.encrypt(YKD_QueryApplyInfo, AES.generateKey(Base64.decode("UICKta78ymjw6+JduzkNCg==")));
        String data = AES.encrypt(YKD_QueryApplyInfo7, AES.generateKey(Base64.decode("B8OevT5wAL94xmRQGVm4Tw==")));
        data = URLEncoder.encode(data,"utf-8");
        System.out.println(data);
        //params.put("corpCode", "XYJK_001_TyFQ");
        params.put("corpCode", "coopr_01");
        params.put("mac", mac);
        params.put("data", data);

        String resp = HttpUtil.post("http://10.3.80.95:8083/gateway/czNotify", params);
        System.out.println(resp);
    }
}

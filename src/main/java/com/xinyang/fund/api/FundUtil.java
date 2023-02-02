package com.xinyang.fund.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.xinyang.fund.entity.Fund;
import com.xinyang.fund.entity.WorthItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FundUtil {
    private static final Logger logger = LoggerFactory.getLogger(FundUtil.class);
    public static final String API = "http://fund.eastmoney.com";
    public static String FUND_NAME_REG = "fS_name\\s*=\\s*\"(.+?)\";";
    public static String NET_WORTH_TREND_REG = "Data_netWorthTrend\\s*=\\s*(.+?);";
    public static String AC_WORTH_TREND_REG = "Data_ACWorthTrend\\s*=\\s*(.+?);";

    /**
     * @param code 基金代码
     * @return 基金详情
     */
    public static Fund loadByFundCode(String code) {
        String content = fetchTtjjFundData(code);
        String fundName = getMatchContent(FUND_NAME_REG, content);
        Fund fund = new Fund();
        fund.setChineseName(fundName);
        fund.setFundCode(code);
        String netWorthContent = getMatchContent(NET_WORTH_TREND_REG, content);
        String acWorthContent = getMatchContent(AC_WORTH_TREND_REG, content);
        fund.setWorthItems(generateWorthItems(netWorthContent, acWorthContent));
        return fund;
    }

    /**
     * 生成基金净值列表
     *
     * @param netWorthContent 净值数组
     * @param acWorthContent  累计净值二维数组
     * @return 净值列表
     */
    private static List<WorthItem> generateWorthItems(String netWorthContent, String acWorthContent) {
        JSONArray netWorthJa = JSONArray.parse(netWorthContent);
        logger.debug(netWorthContent);
        JSONArray acWorthJa = JSONArray.parse(acWorthContent);
        logger.debug(acWorthContent);
        List<WorthItem> worthItems = new ArrayList<>(netWorthJa.size());
        for (int i = 0; i < netWorthJa.size(); i++) {
            WorthItem worthItem = new WorthItem();
            JSONObject netJo = netWorthJa.getJSONObject(i);
            JSONArray acJa = acWorthJa.getJSONArray(i);
            worthItem.setNetWorth(netJo.getString("y"));
            worthItem.setLongDate(netJo.getLong("x"));
            worthItem.setAcWorth(acJa.getString(1));
            logger.debug(worthItem.toString());
            worthItems.add(worthItem);
        }
        return worthItems;
    }

    /**
     * 通过天天基金获取基金信息
     *
     * @param code
     * @return
     */
    private static String fetchTtjjFundData(String code) {
        String apiUrl = API + "/pingzhongdata/" + code + ".js";
        try {
            URL u = new URL(apiUrl);// sun.net.www.protocol.http.HttpURLConnection
            URLConnection uc = u.openConnection();
            InputStream is = uc.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s = null;
            StringBuilder sb = new StringBuilder();
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通知正则表达式匹配内容
     *
     * @param regExpression 正则表达式
     * @param fullContent   文本
     * @return
     */
    public static String getMatchContent(String regExpression, String fullContent) {
        Pattern pattern = Pattern.compile(regExpression);
        Matcher matcher = pattern.matcher(fullContent);
        if (matcher.find()) {
            String content = matcher.group(1);
            return content;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        System.getProperties().put("http.proxyHost", "10.2.0.10");
        System.getProperties().put("http.proxyPort", "808");
        Fund fund = FundUtil.loadByFundCode("161005");
        System.out.println(fund);
        System.getProperties().remove("http.proxyHost");
        System.getProperties().remove("http.proxyPort");
    }
}

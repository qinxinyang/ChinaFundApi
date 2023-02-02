## 基金净值获取
示例
```java
Fund fund = FundUtil.loadByFundCode("161005");
System.out.println(fund.getChineseName());
System.out.println(fund.getFundCode());
List<WorthItem> worthItems = fund.getWorthItems();
WorthItem worthItem = worthItems.get(worthItems.size() - 1);
System.out.println(worthItem);
```
输出
```java
富国天惠成长混合A/B(LOF)
161005
WorthItem{date=Wed Feb 01 00:00:00 CST 2023, longDate=1675180800000, netWorth='2.8794', acWorth='5.9274'}
```

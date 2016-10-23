package safe.fish;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class QueryfishnoError {

	public static Boolean isfish(String url, String html) {
		Document doc = Jsoup.parse(html);
		Elements links=doc.getElementsByTag("title");
		String titles="";
		for(Element link:links){
			titles=titles+","+link.text();
		}
		Double score = 0.0;
		for (String kEY : key_word.keySet()) {
			if (titles.contains(kEY)) {
				score += key_word.get(kEY);
			}

		}
		for (String urlx : KEY_Word.keySet()) {
			if (url.contains(urlx)) {
				score += KEY_Word.get(urlx);
			}
		}
		Double errorscore = iserror(url, titles);
		if (errorscore < 0.0) {
			score = 0.0;
		}

		if (score >= base_score) {
			return true;
		}

		return false;

	}
	
	static double base_score = 1.9;
	static Map<String, Double> key_word = new HashMap<String, Double>() {
		private static final long serialVersionUID = 1L;

		{
			put("﻿拾号贷", 5.51);
			put("节奏云免流", 3.69);
			put("移动商务平台-您身边的信息化专家", 2.30);
			put("西之源邮件群发", 4.29);
			put("用户登录－OA系统", 3.30);
			put("休闲娱乐≮360安全卫士信得过网站≯", 2.74);
			put("支付宝 - 网上支付 安全快速", 3.70);
			put("支付宝 - 网上支付 安全快速！", 2.23);
			put("借贷宝-人脉变钱脉", 6.65);
			put("恭喜您的帐号被新浪网微博八周年 “ 送大礼” 活动抽取为幸运用户", 3.50);
			put("快速申请信用卡的办法！一天下卡", 2.32);
			put("title=\"QQ安全中心首页\"", 4.72);
			put("云南映象门票预订", 5.05);
			put("AY短网址 提供网址缩短工具和批量短网址生成服务", 5.68);
			put("Free online web polls generator", 6.08);
			put("转出幸运范啦", 2.12);
			put("尊敬的幸运观众,您好!\n\n本栏目将通过以下资料为您送出奖", 3.38);
			put("title=\"淘宝购物\"", 2.49);
			put("腾讯电脑管家_关于管家", 6.16);
			put("TTXCPS,老联盟,信誉高,日付广告联盟,日付联盟,cpa广告联盟,cps广告联盟", 6.50);
			put("唯爱传奇3", 4.10);
			put("48发卡平台 - 48发卡平台 - _自动发卡平台_卡类兑换平台_卡类寄售平台_API接口", 2.66);
			put("_hmac_md5:function(key, data)", 6.60);
			put("一个民族，只有在不断创新中，凝聚力才能不断增强", 3.35);
			put("山西信用贷款_小额贷款_无抵押贷款-【山西信贷网", 3.61);
			put("haidao - 全球领先的企业级电子商务系统软件 - Powered by Haidao", 6.10);
			put("鄂尔多斯市天赐草原食品有限责任公司", 4.73);
			put("拍拍卡QB充值漏洞---刀哥發發原创", 6.73);
			put("中体是合法的吗 内蒙时时彩快三", 6.33);
			put("在线棋牌", 6.23);
			put("兑换中心-英雄联盟官方网站-腾讯游戏", 4.41);
			put("淘宝商家-在线申请中心-服务商支付-方便又可靠", 5.82);
			put("网赚_网络上赚钱项目方法-游金网赚官网", 5.97);
			put("蓝虹数卡- 电话点卡充值平台", 4.24);
			put("新浪微博-微博八周年专题", 3.21);
			put("rrurl.cn seqing小游戏", 2.73);
			put("国兴明星网，日本韩国，欧美明星大全，最新作品，提供明星大全，明星最新作品全集，明星动态资料，八卦新闻, 是一个明星资料库", 2.78);
			put("支持PC、安卓、苹果手机的直拨、回拨的网络电话", 4.61);
			put("2016云计划商学院官网,云计划创富系统平台微信源码程序免费下载", 4.69);
			put("IP Partner CISCO", 5.93);
			put("0898投注网 海南七星彩 时时彩投注 七星彩投注 足球投注 时时彩", 5.55);
			put("成人性生活视频_bb网", 2.66);
			put("常州美贝尔医疗美容医院有限公司", 3.84);
			put("ecshop小米2015旗舰版 人人科技http://php8.taobao.com", 2.66);
			put("青岛旅游指南_青岛海鲜美食_青岛住宿_青岛自驾游攻略_青岛自助游自由行攻略_青岛周边游_游玩网", 4.93);
			put("厦门石油|厦门石油交易中心_官方网站|厦门石油交易中心", 5.83);
			put("确认退款</button>", 4.92);
			put("大学生网络兼职刷单被骗5600元_新闻动态_卡淘商城|诚信游戏点卡充值商城", 3.61);
			put("拓宽手游交易网欢迎您", 3.29);
			put("账号安全验证", 4.71);
			put("有这些银行卡的注意了！十类钓鱼网站让你倾家荡产|社会热点", 6.61);
			put("疾电贷 - 贷款申请", 5.17);
//			put("淘宝优惠券", 5.75);
			put("国际域名网-专注中国网站建设", 4.80);
			put("Online Shopping for Cool Gadgets, RC helicopter", 2.12);
			put("Login.php?sslchannel=true&sessionid=coeTKWazZAwZzFXbcRldCW050V89CM8OamIViSvi", 6.13);
			put("src=\"pub_select.html\"", 3.50);
			put("提示：您确定要退出吗？", 3.80);
			put("GetDeviceIp(\"https://buc-office.alibaba-inc.com/ip.js", 5.91);
			put("aha.vcada.com", 2.45);
			put("icloud-account.com", 3.63);
			put("www.repian3.pw", 5.56);
			put("你拍一(NiPaiYi.Com)提醒您:您的域名尚未绑定", 4.25);
			put("Manufacturers, Retailers, Sellers", 5.12);
			put("宅男手机看片 - 从此不在寂寞", 6.37);
			put("chen-sa", 4.27);
			put("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKX74ixpzVyXbJprcLfbH4psP", 2.15);
			put("您的空间到期了！", 3.15);
		}
	};
	static Map<String, Double> KEY_Word = new HashMap<String, Double>() {
		private static final long serialVersionUID = 1L;

		{
			put("e10086.tk", 3.41);
			put("ali66.sz-kaavi", 2.77);
			put("m.mofajj", 5.63);
			put("mobile.365hl", 4.03);
			put("easy-world", 6.67);
			put("m.dsa114", 6.50);
			put("d.kj83", 6.58);
			put("qqxianling.com", 5.80);
			put("goldboo", 4.79);
			put("cckvbe", 3.37);
			put("365143", 6.42);
			put("996811421", 4.35);
			put("ta0ba0", 3.43);
			put("vgn.c", 3.42);
			put("rez068", 3.77);
			put("abchina-ch", 3.55);
			put("acouzeag", 2.18);
			put("icloud-sing", 5.44);
			put("centre5", 4.53);
			put("tnl8.cc", 3.61);
			put("c2.weea.top", 3.97);
			put("san.budon.top", 2.91);
			put("golden365", 5.87);
			put("se.vdfyv.top", 5.23);
			put("icloud-lc", 4.71);
			put("iapple.china-szxd", 2.77);
			put("hds6s.com", 5.82);
			put("gxqrkj.com", 5.36);
			put("2taobno", 5.82);
			put("gzcnk.cn", 5.15);
			put("panhualinjutiao.com", 5.29);
			put("zddyr.com", 3.67);
			put("zddyr.com", 3.53);
			put("88gzr", 4.08);
			put("99susher", 2.63);
			put("llhgg.cn", 6.12);
			put("kk1991.com", 3.69);
			put("ag138", 6.77);
			put("lifengcnc.com", 5.45);
			put("uk.refunding.manager", 2.87);
			put("applied-location", 6.08);
			put("asdfaawesdvd", 4.61);
			put("gzcnk.cn", 5.29);
			put("bfaby", 6.18);
			put("cshgk.cn", 2.27);
			put("dnf990", 2.32);
			put("ioczs.com", 3.92);
			put("vchri.cn", 3.20);
			put("kit6s", 3.37);
			put("com.ua", 3.89);
			put("f3322.net", 5.89);
			put("hykis.com", 5.34);
			put("lps6s.com", 5.75);
			put("cfsag508t.com", 5.04);
			put("com-find-appleid.com", 3.55);
			put("city960.cn", 3.71);
			put("appleid-idi.com", 6.52);
			put("huangjing88.com", 4.09);
			put("ic1oud.id-i0s.cn", 2.31);
			put("icloud-address.com", 2.65);
			put("icloud-thert.top", 4.67);
			put("icloud.myfold.cn", 3.54);
			put("iphone-client.com", 5.09);
			put("iphone.ntdios.com", 4.74);
			put("liuguoping.mom", 5.71);
			put("popwebdomains.com", 5.07);
			put("suiqifz.com", 4.62);
			put("xpywq.cn", 4.05);
			put("zfsaf.com", 5.82);
			put("xtegz.cc", 4.33);
			put("xvd6s", 6.15);
			put("yei6s", 6.09);
			put("a.ndns.work", 2.16);
			put("jhtfk.com", 2.16);
			put("odata.cc", 2.68);
			put("ayzys.cn", 5.34);
			put("sh-huizhang.com", 4.23);
			put("ymsqn.com", 5.36);
			put("alaibdvab", 6.21);
			put("apco.top", 3.30);
			put("myhaolinju.com", 3.81);
			put("bkfgh", 2.50);
			put("bpsygzs", 4.66);
			put("chenwangshidai", 6.13);
			put("findmyiphone-alert-lost", 6.88);
			put("fshtrhfdgsd", 5.91);
			put("gah6s", 6.32);
			put("gsyhme", 5.14);
			put("icbcai", 4.24);
			put("ioczs.com", 5.25);
			put("iphone-ther", 2.60);
			put("haisou520.com", 5.91);
			put("ippartner", 3.71);
			put("lcgbc", 3.76);
			put("lkn6s", 5.36);
			put("euyoa.cn", 3.47);
			put("mfpgsj", 3.66);
			put("mqfkm", 3.33);
			put("ndgjs", 4.19);
			put("sam6s", 2.36);
			put("024552.com", 6.04);
			put("city960.cn", 4.17);
			put("10.kk30host.net", 3.16);
			put("95588rfv.pw", 5.62);
			put("app1e.myfonid.cn", 4.40);
			put("app1eld.ld-i0s.com", 3.93);
			put("apple-icloud-servce.com", 5.38);
			put("apple-icloud-serve.com", 6.57);
			put("apple-icloud-sevire.com", 6.75);
			put("apple-sio-icloud.com", 6.11);
			put("cbunbao.cc", 2.56);
			put("dehesteel.com", 4.33);
			put("icloud-3ios.com", 3.00);
			put("icloud-6ios.com", 3.63);
			put("icloud-find.cx", 5.11);
			put("icloud.dcgzcy.com", 2.69);
			put("ios.applcloud-appleid.com", 4.34);
			put("iphone-ios-chinese.net", 4.82);
			put("iphone-serveios.com", 2.62);
			put("iphone-ther.com", 3.72);
			put("paipaiqb.top", 2.17);
			put("tratortecpa.com.br", 2.72);
			put("0898123", 2.97);
			put("hryiyao.com", 2.55);
			put("odata.cc", 2.35);
			put("gzcnk.cn", 6.28);
			put("95588vx", 4.26);
			put("bybjc.com", 2.67);
			put("coveriac.com", 3.45);
			put("cbnpf.com", 2.22);
			put("drtqq.com", 2.69);
			put("apple-icloud-ioscc-louds", 4.55);
			put("china-cyb.com", 6.32);
			put("icloud.kleyes.com", 4.72);
			put("dbcwg", 3.17);
			put("upmgs.com", 4.79);
			put("hapsdfpghysky", 6.14);
			put("je800.com", 6.55);
			put("icbc-ahs", 6.61);
			put("icloud-7ios", 3.52);
			put("jntongfu.cn", 5.84);
			put("jis6s", 6.04);
			put("org.ua", 6.65);
			put("oeqtkb", 2.28);
			put("hzhdami.cn", 3.25);
			put("tas6s.com", 4.70);
			put("uiplb.com", 6.51);
			put("wyc6s.com", 4.61);
			put("yam6s.com", 6.84);
			put("024552.com", 2.28);
			put("tmaose", 2.37);
			put("ccbcb.cc", 4.98);
			put("kfsicbc.com", 5.60);
			put("wesisd", 5.06);
			put("weyans", 4.57);
			put("woyasuz", 3.37);
			put("88kbq.click", 2.61);
			put("app1eld.foemldos.cn", 4.71);
			put("apple.icloud.dzlemiao", 6.36);
			put("china-elite.org", 4.67);
			put("chinesequeen.cn", 2.59);
			put("fdshgjyfsgdd.gq", 5.20);
			put("icloud-ap.cc", 2.82);
			put("icloud.com.iosldk", 5.03);
			put("icloudcargin.com", 3.90);
			put("iphone-chinese.net", 5.54);
			put("iphones-china.net", 4.43);
			put("iphones-id.cc", 6.15);
			put("lcloud-appleid.com", 2.25);
			put("qq-aqcn.win", 4.93);
			put("tuikuan.auctionbuyxn1.top", 2.27);
			put("icloud.fytr.me", 5.52);
			put("tuikuan001.pw", 3.75);
			put("zxlfc", 6.06);
			//异常会多删除
			put("3g.panhualinjutiao.com", 5.03);
			put("icloudcargin.com", 3.90);
			

		}
	};
	static Map<String, Double> id_error = new HashMap<String, Double>() {
		private static final long serialVersionUID = 1L;

		{
			put("jifenqiang.net", -14.39);
			put("ta0ba0.ren", -15.08);
			put("6930", -12.81);
			put("zy7766.com", -16.46);
			put("htbaza", -13.87);
			put("houcangzhou", -12.33);
			put("f3322-qsav.f3322.net", -14.75);
			//异常会多删除
			put("panhualinjutiao.com", -18.81);
			put("ytmaosen.com", -18.44);
			//异常会多删除
			put("jntongfu.cn", -15.91);
			put("katana.com.ua", -14.63);
			put("icloudactivation", -15.50);
			put("zuoxuanroujian.cc", -12.11);

		}
	};


	private static Double iserror(String id, String postdata) {
		Double iserror = 0.0;
		for (String urler : id_error.keySet()) {
			if (id.contains(urler)) {
				iserror = id_error.get(urler);
			}

		}
		return iserror;
	}

}

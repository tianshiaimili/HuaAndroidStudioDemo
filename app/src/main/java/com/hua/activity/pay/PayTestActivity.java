package com.hua.activity.pay;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.hua.R;

public class PayTestActivity extends FragmentActivity {

	// 商户PID
	public static final String PARTNER = "2088811035450481";
	// 商户收款账号
	public static final String SELLER = "sc-smt@mama.cn";
	// 商户私钥，pkcs8格式
//	public static final String RSA_PRIVATE = "MIICWwIBAAKBgQDfXa0j44+iAApRWIGTIZh84fNXLOqdsbZbbb5ynQfryyrpHnjH2alYDCzdv7V3fX/JpOBS66JZK6pgc3cIkoYovKSORLBE2HSeVbthOFJXFe4+Qt9OjlRBjAwK4VQ6V/H88fgAkLZqmnHiduNAFfQ552u4IPa8AtD0BGeyh6QW7wIDAQABAoGAZZNpCDgU+lvAVWvFSQ5vFON/0LX5X4sQXZWTlPV6Q61ZrzePP3rpOsQWpyTmNheZRC65YJwNcTWdPk1j5T8UyJopRG+Qcn5wHdNZMUsZw2jHmrinm4I1nxIbFJVB9yhvqQsHURcFbFexqJIwAp0EIgCZlUtykv9+7HjIDS1a+xECQQD1UhFgBXjiWgDOPY8u/VNMLlHAJVR2DG6dcvnwDVM39liNDpxHdK7Mr1qRCZn5tiNznvLFd0+GUEkvB0tftld3AkEA6RbwTD5GAV5TdLkGJduuK2OfcPf57LQlkVIVT8+Yq+CFLobmmSMOeGu6sLe4mpLyEYlto4PnXtwuxA4fSDmKSQJAfPEZTFA9Kvsv4dpUbFg07l3A4R2iEKVwSNxgEzSWJ7BdlSnVykbRKAo/FBNRym5LS/2uI4gKhpupSjxq1QpnLQJAPTSpyst7ZHaZYYgIRj6mQHx7bJWhCffP+ZXdRMTVaSSetDX2LXYYIaTgM/E6R1SYveQhZxOG2NDmctLmgfeiCQJAThCRbo9UzmGXfTJMfQ34WxjpV9fcgdH31fGvY195yTqMfmvDCfKSGS51cwitZmDBYuZAI3noXD+U6IknM6vRCQ==";
		public static final String RSA_PRIVATE ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL2wYQ2gaE5/dAlE1yqaoeBlumFI/5VgNZS6vv03ZB6aYOXzuJqPeSorgFHxfleoJyMC8N79DokH+As1CXUYVM4GjQkW716m+APaIh4vDmTjLIhJlkFsO7MoXCkQJ5AvBeLCeXShQhfJZvLzodjp/msesu5+N1f7gPk9JwPd48GHAgMBAAECgYEAjiPeBgz+PFwrQkrQhP9Mb88q432E75UhcWqidTKNlpUOKSIBYehC3gOs6zpv9xVtBkJM5ntv6eULzLIYfSMXX+LtlAFGCxezdnoxh4cNLdiSw3h0YMc/A4F5vZ30SR6FoEcVjytfRAUA/2RE56fMIfMKLFZx+WuSJ84nhJxwM8ECQQD0OvXpEuiuhS3NPqqvU/JTIqvJgB45xBx1b/ILLUU67yHO2Fzy2gDoyJqmN0tlmr1SHBwLIGCaaqAIcJmSwmghAkEAxtSMfTorzYMldyHnMN0tJNJerkivimY5zszL3A1BxPSJjkacaY7BroMZrF4W04mv+4PVsaiQKpQKL++UvgdUpwJAFYKL3fc4XwaDvivixIZUNVY8YwbRoqzPBNjTLIDUNBiTlbAgn5LruZTRago8rbovKmgnYCN93pu9dIDWRcKxgQJAaxIyxYz54aXqGwlq0mmI0fdnfTsvLLx4DOFENq/85u841CE6Qa3apvt6a9QPPsM6vZr1DMLVP9iQ09eUpfCUqQJBANesZz15pZ9Fztpxh1j412n/wPmTMIitarK/LZpwdUpBZfFxnqewUO3ZF3iGtSoIbFhpnGIu6lSd4J0AUvIV3xw=";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "";
	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;
	private PayUtils payUtils;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
		payUtils = PayUtils.getInstance(this);
	}

	public void pay(View v){

		payUtils.pay(v,this);

	}

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check(View v) {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask(PayTestActivity.this);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
//				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

}

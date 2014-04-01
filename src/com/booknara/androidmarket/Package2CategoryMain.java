package com.booknara.androidmarket;

import java.util.Locale;

import com.gc.android.market.api.MarketSession;
import com.gc.android.market.api.MarketSession.Callback;
import com.gc.android.market.api.model.Market.App;
import com.gc.android.market.api.model.Market.App.ExtendedInfo;
import com.gc.android.market.api.model.Market.AppsRequest;
import com.gc.android.market.api.model.Market.AppsResponse;
import com.gc.android.market.api.model.Market.ResponseContext;


public class Package2CategoryMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length < 2) {
			System.out.println("Usage :\n" + "market email password query");
			return;
		}

		String email = args[0];
		String password = args[1];
		
		MarketSession session = new MarketSession();
//		String androidId = "f127896ab51d63ba";	// Nexus 4 Secure,ANDROID_ID
//		String androidId = "356489053681974";	// Nexus 4 TelephonyManager.getDeviceId()
		String androidId = "3fdb9f8a04f6f2be";	// 
		
		session.login(email,password);
		session.getContext().setAndroidId(androidId);
		
		session.setOperator("KT", "45008");
//		session.setOperator("T-Mobile", "310260");
//		session.setOperator("Dontelecom", "25010");
		
//		session.setLocale(Locale.CHINA);
//		session.setLocale(Locale.KOREAN);
//		System.out.println(Locale.KOREA.toString());
		
		// Query for extracting category information from package name(Package Name - Category) 
//		final String packageName = "com.google.android.apps.maps";
//		final String packageName = "com.imbc.mini";
//		final String packageName = "com.facebook.katana";
		
		final String packageName = "com.iloen.melon";		// Only Korea
//		final String packageName = "com.jiran.skt.self";	// Only Korea
//		final String packageName = "com.skt.prod.zeropc.mobile";	// Only Korea
//		final String packageName = "com.zeropc.tablet";
		String query = "pname:" + packageName;
//		String query = "melon";
		AppsRequest appsRequest = AppsRequest.newBuilder()
		                                .setQuery(query)
		                                .setStartIndex(0).setEntriesCount(10)
		                                .setWithExtendedInfo(true)
		                                .build();

		Callback<AppsResponse> callback = new Callback<AppsResponse> () {
            @Override
                public void onResult(ResponseContext context, AppsResponse response) {
            	if (response == null) {
            		System.out.println("response object is null");
            		return;
            	}
            	
            	for (App app : response.getAppList()) {
            		if (app == null) {
            			System.out.println("app object is null");
            			continue;
            		}
            		
            		System.out.print(app.toString());
            		if (app.hasExtendedInfo()) {
            			ExtendedInfo info = app.getExtendedInfo();
//            			System.out.print(info.toString());
//            			System.out.println("Title : " + app.getTitle() + " - " + "Category : " + info.getCategory() + " - " + "Package Name : " + info.getPackageName());
//            			System.out.println("Package Name : " + packageName + " - " + "Category : " + info.getCategory());
            		}
            	}
            }
        };
		
		session.append(appsRequest, callback);
		session.flush();
		
		System.out.println("End of info");
	}
}
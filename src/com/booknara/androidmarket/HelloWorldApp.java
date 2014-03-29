package com.booknara.androidmarket;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import com.gc.android.market.api.MarketSession;
import com.gc.android.market.api.MarketSession.Callback;
import com.gc.android.market.api.model.Market.App;
import com.gc.android.market.api.model.Market.App.ExtendedInfo;
import com.gc.android.market.api.model.Market.AppType;
import com.gc.android.market.api.model.Market.AppsRequest;
import com.gc.android.market.api.model.Market.AppsRequest.OrderType;
import com.gc.android.market.api.model.Market.AppsRequest.ViewType;
import com.gc.android.market.api.model.Market.AppsResponse;
import com.gc.android.market.api.model.Market.ResponseContext;

public class HelloWorldApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MarketSession session = new MarketSession();
		String email = "";
		String password = "";
//		String androidId = "f127896ab51d63ba";	// Nexus 4
		String androidId = "3fdb9f8a04f6f2be";	// 
		
		session.login(email,password);
		session.getContext().setAndroidId(androidId);
//		session.setLocale(Locale.KOREAN);
//		System.out.println("Locale : " + Locale.getDefault());
		
//		String query = "maps";
//		String query = "devoma.gmailmagictricks";
//		String packageName = "com.google.android.apps.maps";
//		String query = "pname:" + packageName;
//		AppsRequest appsRequest = AppsRequest.newBuilder()
//		                                .setQuery(query)
//		                                .setStartIndex(0).setEntriesCount(10)
//		                                .setWithExtendedInfo(true)
//		                                .build();
		
		// OrderType : NONE, POPULAR, NEWEST, FEATURED
		// Category Id : 
		// APPLICATION(AppType.APPLICATION) : TRAVEL, BOOKS_AND_REFERENCE, BUSINESS, COMICS, COMMUNICATION, EDUCATION, ENTERTAINMENT, FINANCE, HEALTH_AND_FITNESS, LIFESTYLE, APP_WALLPAPER
		// MEDIA_AND_VIDEO, MEDICAL, MUSIC_AND_AUDIO, NEWS_AND_MAGAZINES, PERSONALIZATION, PHOTOGRAPHY, PRODUCTIVITY, SHOPPING, SOCIAL, SPORTS, TRANSPORTATION
		// TRAVEL_AND_LOCAL, TOOLS, WEATHER, APP_WIDGETS, LIBRARIES_AND_DEMO

		// GAME(AppType.GAME) : ARCADE, BRAIN, CARDS, CASUAL, GAME_WALLPAPER, SPORTS_GAMES, GAME_WIDGETS
		
		final Map<App, String> appinfo = new HashMap<App, String>();
		for (int i = 0; i < 20; i++) {
			int startIndex = i * 10;
			AppsRequest appsRequest = AppsRequest.newBuilder()
					.setOrderType(OrderType.POPULAR)
					.setAppType(AppType.GAME)
					.setCategoryId("ARCADE")
					.setViewType(ViewType.ALL)
					.setStartIndex(startIndex).setEntriesCount(10)
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
	            		
	            		if (app.hasExtendedInfo()) {
	            			ExtendedInfo info = app.getExtendedInfo();
	            			appinfo.put(app, info.getCategory());
	            		}
	            	}
	                
	            	System.out.print(".");
	            }
	        };

			session.append(appsRequest, callback);
			session.flush();
		}
		
		System.out.println();
		System.out.println("---- Printing ----");
		Iterator<Entry<App, String>> it = appinfo.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Map.Entry<App, String> pairs = (Map.Entry<App, String>) it.next();
			System.out.println(++i + " Title : " + pairs.getKey().getTitle());	
			System.out.println("PackageName : " + pairs.getKey().getPackageName());
			System.out.println("Category : " + pairs.getValue());
			System.out.println("");
		}
		
		System.out.println("End of info");
	}
}
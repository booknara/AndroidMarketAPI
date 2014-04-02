package com.booknara.androidmarket;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.gc.android.market.api.MarketSession;
import com.gc.android.market.api.MarketSession.Callback;
import com.gc.android.market.api.model.Market.App;
import com.gc.android.market.api.model.Market.App.ExtendedInfo;
import com.gc.android.market.api.model.Market.AppsRequest;
import com.gc.android.market.api.model.Market.AppsResponse;
import com.gc.android.market.api.model.Market.ResponseContext;


public class Package2CategoryMain {
	static final String baseUrl = "https://play.google.com/store/apps/details?id=";

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		if(args.length < 3) {
			System.out.println("Usage :\n" + "market email password android_id query");
			return;
		}

		String email = args[0];
		String password = args[1];
		String androidId = args[2];
		
		MarketSession session = new MarketSession();
		session.login(email,password);
		session.getContext().setAndroidId(androidId);
		
		// Query for extracting category information from package name(Package Name - Category) 
		final String packageName = "com.facebook.katana";
		// final String packageName = "com.iloen.melon";				// Only South Korea distributed App

		String query = "pname:" + packageName;
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
            	
            	// No app info
            	if (response.getAppList().size() == 0) {
                	String fullUrl = baseUrl + packageName;

                    URL url;
					try {
						url = new URL(fullUrl);
	                    InputStream input = url.openStream();
	                    
	                    Document doc = Jsoup.parse(getStringFromInputStream(input));
	                    
	                    /*
	                     *  HTML Element 
	                     *  <a class="document-subtitle category" href="/store/apps/category/[Category]"> 
	                     *  	<span itemprop="genre">[Category]</span> 
	                     *  </a>
	                     */
	                    String category = doc.select("a.category span[itemprop=genre]").text();
	                    System.out.println("Package Name : " + packageName + " - " + "Category : " + category);

					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
            		
            	} else {
                	for (App app : response.getAppList()) {
                		if (app == null) {
                			System.out.println("app object is null");
                			continue;
                		}
                		
                		if (app.hasExtendedInfo()) {
                			ExtendedInfo info = app.getExtendedInfo();
                			System.out.println("Package Name : " + packageName + " - " + "Category : " + info.getCategory());
                		}
                	}            		
            	}

            }
        };
		
		session.append(appsRequest, callback);
		session.flush();
		
		System.out.println("End of info");
	}
	
 	private static String getStringFromInputStream(InputStream is) throws IOException {
        String encoding = "UTF-8";
        String output = IOUtils.toString(is, encoding);
        
        return output;
 	}
}
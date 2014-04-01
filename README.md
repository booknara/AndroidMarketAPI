# AndroidMarketAPI - Simple project to get Google Play Application category information 

AndroidMarketAPI is an Java project that uses [android-market-api project](https://code.google.com/p/android-market-api) to get Google Play Application category information. 
If the API doesn't return correct data, you can get category information by HTML Parsing directly.

Reference : [android-market-api project discussion groups in Google](https://groups.google.com/forum/#!forum/android-market-api)

### Features:
	* Application Package Name - Category(
	* Applications belong to a specific category ordered by popular, newest, featured

## Prerequisite:

	* Google ID/PW
	* Android Device Id(TelephonyManager - getDeviceId())

## Credits (Open Source Libaray)
	* [android-market-api](https://code.google.com/p/android-market-api)
	* [Google protocol buffer](https://developers.google.com/protocol-buffers/)
	* [Apache Common IO](http://commons.apache.org/proper/commons-io)
	* [jsoup - Java HTML Parser](http://jsoup.org)

## Licenses

    Copyright (C) 2014 booknara

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
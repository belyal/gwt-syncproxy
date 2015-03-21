# Changes #

While there are no specifically breaking changes in this version's release, a number of faulty methods which could not be properly tested were updated
for testability, readability, and accuracy. As such, introduction of some of the exception handling in these methods may cause previously functional service
calls to fail. Please submit issue reports for any unexpected failures. You may need to support better exception handling as a result of these changes.
Cookies have been tested as part of the library, so cookies and multiple session support are again available.

## GWT#create style Service Creation ##

Starting in 0.5, the old `SyncProxy#newProxyInstance` methods have been deprecated in favor of the more GWT-like `SyncProxy#create` methods. The old
newProxyInstance methods have been appropriately upgraded with JavaGenerics and should continue to function, but it is highly recommended
that you begin switching over to the newer methods as future versions of this library may stop supporting those old methods. Additionally, the old
methods were very hard to test, so full-testing on the old methods is not available as of the 0.5 library (was very little testing to begin with
for those methods anyway). Synchronous services are of course still supported in SyncProxy through the `SyncProxy#createSync` method. See the
[QuickStart](QuickStart.md) guide for a review of the new methods.

## ServiceDefTarget support ##

SyncProxy 0.5 also adds support for the common GWT style:

```
	GreetingServiceAsync greetService = GWT.create(GreetingService.class);
	((ServiceDefTarget) greetService).setServiceEntryPoint(getModuleBaseURL()
		+ "greetingEntryPoint");
```

which must be implemented with flag setting to ignore a service missing the expected annotation:

```
	// Suppress exception that the service interface doesn't have the RemoteServiceRelativePath annotation
	SyncProxy.suppressRelativePathWarning(true);
	GreetingServiceAsync greetService = SyncProxy.create(GreetingService.class);
	((ServiceDefTarget) greetService).setServiceEntryPoint(getModuleBaseURL()
		+ "greetingEntryPoint");
	// You may also use the ProxySettings() object to manually set the Entry Point as shown in the QuickStart wiki
```

## Cookie Issues and Support ##

Cookies have been repaired in the 0.5 release. The key problem was that manually created HttpCookie's needed to have their `#path` set for the
HttpUrlConnection to automatically pass them along. We have resolved this by automatically populating Cookie's in the specified CookieManager with
the proper path (removeServicePath) if it is missing. With this fix, multiple sessions are again supported. Please see the [QuickStart](QuickStart.md) guide for a
review of these methods. If you are using Sessions on App-Engine, make sure to add the ` <sessions-enabled>true</sessions-enabled> ` flag in the
`WEB-INF/appengine-web.xml` file. Additionally, the SyncProxy generated services also implement a new interface which allows access to the stored
CookieManager for the service in question. To access the CookieManager for a given service, do the following:

```
	GreetingServiceAsync greetService = SyncProxy.create(GreetingService.class);
	CookieManager cm = ((HasProxySettings) greetService).getCookieManager();
	String domain = URI.create(getModuleBaseURL()).getHost();
	HttpCookie cookie = new HttpCookie("myCookie", "myCookieValue");
	// You should set these values, but if don't, SyncProxy will use the current target's Domain and Path
	cookie.setDomain(domain);
	cookie.setPath("path/to/servlet");
	cm.getCookieStore().add(URI.create("http://" + domain), cookie);
	// If you need to change the CookieManager on the fly, that is also possible in between service Calls
	CookieManager cm2 = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
	((HasProxySettings) greetService).setCookieManager(cm2);
```

## XSRF Protection ##

XSRF Protected Scripts are not fully support in 0.5, but we have made steps towards proper support of these scripts. The RpcToken system is now implemented and sufficiently tested
within the library. However, a lack of experience with XSRF has forced the decision to push off this implementation in favor of providing the upgraded library features
sooner. Anyone with more experience with XSRF, please feel free to join this project and contribute to the support of XSRF in SyncProxy.
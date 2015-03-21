# Release Notes #

## 0.5.0 - 2015-01-10 ##

Compiled Library against GWT 2.7.0 to get Android Library compatible with GWT 2.7.0 server code. Also made service creation more GWT-Style and
enhanced the policy finder to work with the XSI-Frame linker. Cookie issues fixed for both libraries. General library cleanup.

  * Updated Android and POJ tests for modifiable module base path
  * Updated all tests to reflect source code tests in GWT 2.7.0
  * Added Support for Java Generics to Service Creation (IE No more casting the service)
  * GWT Style Service creation methods SyncProxy.create and SyncProxy.createSync
    1. old newProxyInstance methods have been deprecated
    1. old non-ProxySetting constructors in RemoteServiceInvocationHandler have been deprecated
    1. provided HasProxySettings interface to specify details for a created service and a concrete implementation option
  * Fixed [Issue 27](https://code.google.com/p/gwt-syncproxy/issues/detail?id=27) and [Issue 29](https://code.google.com/p/gwt-syncproxy/issues/detail?id=29) - POTENTIALLY BREAKING - Many of the old newProxyInstance methods had printStackTrace for exceptions. These have been converted to RuntimeExceptions for handling to remove exception output to an uncontrolled console. It is recommended to change over to the new #create methods.
  * Fixed [Issue 38](https://code.google.com/p/gwt-syncproxy/issues/detail?id=38) with updates to RpcPolicyFinder
  * Fixed [Issue 35](https://code.google.com/p/gwt-syncproxy/issues/detail?id=35) (merged with [Issue 42](https://code.google.com/p/gwt-syncproxy/issues/detail?id=42) and [Issue 43](https://code.google.com/p/gwt-syncproxy/issues/detail?id=43)) Support for XS-IFrame linker, which is default as of GWT 2.7
  * Fixed [Issue 41](https://code.google.com/p/gwt-syncproxy/issues/detail?id=41) SafeHtml, SafeHtmlString, SafeHtmlBuilder, SafeHtmlUtils available on Android client-side. See [CommonIssues](CommonIssues.md) wiki for details on a small modification that had to be made
  * Added in Gwt RegExp classes (Java Pattern emulation style, not the JS Super-source)
  * Fixed [Issue 36](https://code.google.com/p/gwt-syncproxy/issues/detail?id=36)/[Issue 15](https://code.google.com/p/gwt-syncproxy/issues/detail?id=15) - Patch applied for handling server-only fields serialized into the response stream
  * Resolved stale [Issue 16](https://code.google.com/p/gwt-syncproxy/issues/detail?id=16)
  * Fixed [Issue 21](https://code.google.com/p/gwt-syncproxy/issues/detail?id=21)/[Issue 30](https://code.google.com/p/gwt-syncproxy/issues/detail?id=30) - Cookies and Multiple sessions are now functional and tested in both the POJ and Android libraries.

## 0.4.3 - 2014-06-23 ##

Compiled Library against GWT 2.6.1 to get Android Library compatible with GWT 2.6.1 server code

  * Fixed [Issue 37](https://code.google.com/p/gwt-syncproxy/issues/detail?id=37) - GWT 2.6.1 Compatible

## 0.4.2 - 2014-03-11 ##

Compiled Library against GWT 2.6.0 to get Android Library compatible with GWT 2.6.0 server code

  * Fixed [Issue 34](https://code.google.com/p/gwt-syncproxy/issues/detail?id=34) - GWT 2.6.0 Compatible

## 0.4.1 - 2013-10-28 ##

Compiled Library against GWT 2.5.1 to get Android Library compatible with GWT 2.5.1 server code

  * Fixed [Issue 31](https://code.google.com/p/gwt-syncproxy/issues/detail?id=31) - GWT 2.5.1 Compatible

## 0.4 - 2013-06-03 ##

Restructured entire syncproxy source into a slightly more compilable, maintainable, testable system.
Created Junit testing systems (See SourceAndTesting). Implemented Android client library for SyncProxy.

  * Fixed [Issue 3](https://code.google.com/p/gwt-syncproxy/issues/detail?id=3) - New Android Library jar available
  * Fixed [Issue 23](https://code.google.com/p/gwt-syncproxy/issues/detail?id=23) - Patch applied
  * Closed [Issue 24](https://code.google.com/p/gwt-syncproxy/issues/detail?id=24) - Unverified
  * Fixed [Issue 26](https://code.google.com/p/gwt-syncproxy/issues/detail?id=26) - Wiki includes link
  * Fixed [Issue 28](https://code.google.com/p/gwt-syncproxy/issues/detail?id=28) - Patch applied

## 0.3 - 2011-08-20 ##
  * Use standard Java CookieManager
  * Fixed [issue 17](https://code.google.com/p/gwt-syncproxy/issues/detail?id=17)
  * Auto fetch Serialization Policy Files from server base on the patch described in [issue 13](https://code.google.com/p/gwt-syncproxy/issues/detail?id=13).

## 0.2 - 2010-11-13 ##
  * Fixed [issue 2](https://code.google.com/p/gwt-syncproxy/issues/detail?id=2), [issue 4](https://code.google.com/p/gwt-syncproxy/issues/detail?id=4) and [issue 9](https://code.google.com/p/gwt-syncproxy/issues/detail?id=9)
  * Multiple sessions as described in [issue 6](https://code.google.com/p/gwt-syncproxy/issues/detail?id=6).
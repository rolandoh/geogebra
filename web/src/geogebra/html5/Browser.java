package geogebra.html5;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window.Location;

public class Browser {
	public static boolean webWorkerSupported = false;

	public static native boolean isFirefox() /*-{
		// copying checking code from the checkWorkerSupport method
		// however, this is not necessarily the best method to decide
		if (navigator.userAgent.toLowerCase().indexOf("firefox") != -1) {
			return true;
		}
		return false;
	}-*/;

	public static native boolean isIE() /*-{
		// copying checking code from isFirefox() and checked from web
		// however, this is not necessarily the best method to decide
		if (navigator.userAgent.toLowerCase().indexOf("msie") > -1) {
			return true;
		}
		return false;
	}-*/;
	
	public native static boolean externalCAS() /*-{
		return typeof $wnd.evalGeoGebraCASExternal == 'function';
	}-*/;

	public static  boolean checkWorkerSupport(String workerpath){
		if("touch".equals(GWT.getModuleName())){
			return false;
		}
		return nativeCheckWorkerSupport(workerpath);
	}

	public static native boolean nativeCheckWorkerSupport(String workerpath) /*-{
		// Worker support in Firefox is incompatible at the moment for zip.js,
		// see http://gildas-lormeau.github.com/zip.js/ for details:
		if (navigator.userAgent.toLowerCase().indexOf("firefox") != -1) {
			@geogebra.common.main.App::debug(Ljava/lang/String;)("INIT: worker not supported in Firefox, fallback for simple js");
			return false;
		}
		if (navigator.userAgent.toLowerCase().indexOf("safari") != -1
			&& navigator.userAgent.toLowerCase().indexOf("chrome") == -1) {
			@geogebra.common.main.App::debug(Ljava/lang/String;)("INIT: worker not supported in Safari, fallback for simple js");
			return false;
		}
		
	    try {
	    	var worker = new $wnd.Worker(workerpath+"js/workercheck.js");
	    } catch (e) {
	    	@geogebra.common.main.App::debug(Ljava/lang/String;)("INIT: worker not supported (no worker at " + workerpath + "), fallback for simple js");
	    	
	    	return false;
	    }
	    @geogebra.common.main.App::debug(Ljava/lang/String;)("INIT: workers are supported");
	    	
	    worker.terminate();
	    return true;
	}-*/;
	
	public static native boolean checkIfFallbackSetExplicitlyInArrayBufferJs() /*-{
		if ($wnd.zip.useWebWorkers === false) {
			//we set this explicitly in arraybuffer.js
			@geogebra.common.main.App::debug(Ljava/lang/String;)("INIT: workers maybe supported, but fallback set explicitly in arraybuffer.js");
			return true;;
		}
		return false;
	}-*/;
	private static boolean float64supported = true;
	
	/**
	 * Checks whether browser supports float64. Must be called before a polyfill kicks in.
	 */
	public static void checkFloat64() {
	    float64supported = doCheckFloat64();
    }
	
	public static boolean isFloat64supported(){
		return float64supported;
	}
	
	private static native boolean doCheckFloat64()/*-{
		var floatSupport = 'undefined' !== typeof Float64Array;
		return 'undefined' !== typeof Float64Array;
	}-*/;

	public static native boolean supportsPointerEvents()/*-{
	    return window.navigator.msPointerEnabled ? true : false;
    }-*/;

	private static native boolean isHTTP() /*-{
	    return $wnd.location.protocol != 'file:';
    }-*/;

	public static boolean supportsSessionStorage() {
	    return (!Browser.isFirefox() && !Browser.isIE()) || Browser.isHTTP();
    }

	public static String normalizeURL(String thumb) {
		String url;
		
		if(thumb.startsWith("http://") || thumb.startsWith("file://")){
			url = thumb.substring("http://".length());
		}
		else if(thumb.startsWith("https://")){
			url = thumb.substring("https://".length());
		}else if(thumb.startsWith("//")){
			url = thumb.substring("//".length());
		}else{
			url = thumb;
		}
		if("https:".equals(Location.getProtocol())){
			return "https://" + url;
		}
		return "http://" + url;
    }
	
	/*
	 * http://stackoverflow.com/questions/11871077/proper-way-to-detect-webgl-support
	 */
	public static native boolean supportsWebGL()/*-{		
		try{
    		var canvas = $wnd.document.createElement( 'canvas' ); 
    		var ret = !! $wnd.WebGLRenderingContext && ( 
         		canvas.getContext( 'webgl' ) || canvas.getContext( 'experimental-webgl' ) );
         	return !!ret;
   		}
   		catch( e ) { return false; } 
	}-*/;
}

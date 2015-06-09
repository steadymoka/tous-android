

package com.moka.framework.util;


import android.content.Context;

import com.kakao.KakaoLink;
import com.kakao.KakaoParameterException;
import com.kakao.KakaoTalkLinkMessageBuilder;


public class KakaoLinkUtil {
	
	private static KakaoLink kakaoLink;
	
	private KakaoLinkUtil() {
	
	}
	
	public static KakaoLink getKakaoLink( Context context ) throws KakaoParameterException {
	
		if ( null == kakaoLink )
			kakaoLink = KakaoLink.getKakaoLink( context );
		
		return kakaoLink;
	}
	
	public static KakaoTalkLinkMessageBuilder createKakaoTalkLinkMessageBuilder( Context context ) throws KakaoParameterException {
	
		return getKakaoLink( context ).createKakaoTalkLinkMessageBuilder();
	}
	
	public static void sendMessage( String linkMessage, Context context ) throws KakaoParameterException {
	
		getKakaoLink( context ).sendMessage( linkMessage, context );
	}
	
}

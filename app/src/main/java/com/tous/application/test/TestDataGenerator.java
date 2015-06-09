package com.tous.application.test;


import android.content.Context;

import com.tous.application.database.table.plan.PlanTable;
import com.tous.application.mvc.model.plan.Plan;

import java.io.IOException;
import java.io.InputStream;


public class TestDataGenerator {

	public static void generateTestData( Context context ) {

		Plan plan = new Plan();
		plan.setName( "후쿠오카 여행" );
		plan.setContent( "짧은 후쿠오카 여행을 하고 와야겠다. 재미있겠다. 고고씽" );
		plan.setStartDate( 20150601 );
		plan.setEndDate( 20150605 );
		PlanTable.from( context ).insert( plan );

		Plan plan2 = new Plan();
		plan2.setName( "홍콩홍콩 여행" );
		plan2.setContent( "홍콩여행 간다. 쇼핑만 졸라 해야겠다 ㅋㅋㅋㅋ 굳굳 잼곘넹" );
		plan2.setStartDate( 20150607 );
		plan2.setEndDate( 20150611 );
		PlanTable.from( context ).insert( plan2 );

//		String jsonString = getJsonString( context );
//		List<TestData> testDatas = parseJson( jsonString );
//		createTestDataToDB( testDatas, context );
	}

	private static String getJsonString( Context context ) {

		try {

			InputStream inputStream = context.getAssets().open( "sample-data.json" );
			int size = inputStream.available();
			byte[] buffer = new byte[size];

			inputStream.read( buffer );
			inputStream.close();

			return new String( buffer, "UTF-8" );
		}
		catch ( IOException e ) {

			e.printStackTrace();
			return "";
		}
	}

}

package scripts;

import static io.restassured.RestAssured.given;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utils.ExtentManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class CowinApi1 {
	
	int hits = 0;
	ExtentManager ExtentManagerObj;
	ExtentReports reports;
	ExtentTest test;
	
	@BeforeClass
	public void setup()
	{
		ExtentManagerObj = new ExtentManager();
		reports = ExtentManagerObj.GetExtent("Reports Vax");
	}
	
	@AfterClass
	public void teardown()
	{
		reports.flush();
	}

	@Test
	public void search_kolkata() {
		System.out.println(System.getProperty("check"));
		System.out.println("Search by District-Kolkata API call >>>>>>");
		test = reports.createTest("Search by District-Kolkata");
		RestAssured.baseURI = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public";

		Response res = SearchByDistrictAPIcall("19-05-2021","725");

		ResponseBody body = res.getBody();
		System.out.println(res.getStatusLine());
		System.out.println("Response time "+res.getTime());
		System.out.println("Response Body is: " + body.asString());
		
		
		hits =0 ;
		for(int i=0;i<180;i++)
		{
			try{checkAvailibilty(res,i,"COVAXIN",0,"18");}catch(Exception e){}
			try{checkAvailibilty(res,i,"COVAXIN",1,"18");}catch(Exception e){}
			try{checkAvailibilty(res,i,"COVAXIN",2,"18");}catch(Exception e){}
			try{checkAvailibilty(res,i,"COVAXIN",3,"18");}catch(Exception e){}
			try{checkAvailibilty(res,i,"COVAXIN",4,"18");}catch(Exception e){}
			try{checkAvailibilty(res,i,"COVAXIN",5,"18");}catch(Exception e){}
		}
		if(hits==0)
		{
			System.out.println("No matches found");
		}
		

	}
	@Test
	public void search_n24() {
		System.out.println("Search by District-N 24 parganas API call >>>>>>");
		test = reports.createTest("Search by District-N 24 parganas");
		RestAssured.baseURI = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public";

		Response res = SearchByDistrictAPIcall("19-05-2021","730");

		ResponseBody body = res.getBody();
		System.out.println(res.getStatusLine());
		System.out.println("Response time "+res.getTime());
		System.out.println("Response Body is: " + body.asString());
		
		
		hits =0 ;
		for(int i=0;i<180;i++)
		{
			try{checkAvailibilty(res,i,"COVAXIN",0,"18");}catch(Exception e){}
			try{checkAvailibilty(res,i,"COVAXIN",1,"18");}catch(Exception e){}
			try{checkAvailibilty(res,i,"COVAXIN",2,"18");}catch(Exception e){}
			try{checkAvailibilty(res,i,"COVAXIN",3,"18");}catch(Exception e){}
			try{checkAvailibilty(res,i,"COVAXIN",4,"18");}catch(Exception e){}
			try{checkAvailibilty(res,i,"COVAXIN",5,"18");}catch(Exception e){}
		}
		if(hits==0)
		{
			System.out.println("No matches found");
			test.info("No matches found");
		}

	}
	
	public void checkAvailibilty(Response res,int centerindex,String vaccinename,int day,String age)
	{
		
		String vax = res.body().path("centers["+centerindex+"].sessions["+day+"].vaccine").toString();
		//System.out.println(vax);
		if(vax.contains(vaccinename))
		{	
			
			String agelimit = res.body().path("centers["+centerindex+"].sessions["+day+"].min_age_limit").toString();
			
			if(agelimit.contains(age))
			{   hits ++;
				System.out.println(vaccinename+" Found for Age limit: "+agelimit);
				System.out.println(hits+") Center Name: "+res.body().path("centers["+centerindex+"].name").toString());
				System.out.println("	Address: "+res.body().path("centers["+centerindex+"].address").toString());
				System.out.println("	Date: "+res.body().path("centers["+centerindex+"].sessions["+day+"].date").toString());
				System.out.println("	Available: "+res.body().path("centers["+centerindex+"].sessions["+day+"].available_capacity").toString());
			
				test.info(vaccinename+" Found for Age limit: "+agelimit);
				test.info(hits+") Center Name: "+res.body().path("centers["+centerindex+"].name").toString());
				test.info("	Address: "+res.body().path("centers["+centerindex+"].address").toString());
				test.info("	Date: "+res.body().path("centers["+centerindex+"].sessions["+day+"].date").toString());
				test.info("	Available: "+res.body().path("centers["+centerindex+"].sessions["+day+"].available_capacity").toString());
	
			}
		}
	}
	
	public Response SearchByDistrictAPIcall(String date,String districtcode)
	{
		Response res=null;
		try {
			 res = given()
					.header("User-Agent", "PostmanRuntime/7.6.0")//Apache HTTP client is blocked by cowin
					.queryParam("district_id", districtcode)//730 n24
					.queryParam("date", date).log().all()
					.when()
					.get("/calendarByDistrict")
					.then()//.log().all()
					// .contentType(ContentType.JSON)
					.extract().response();
			
		} catch (Exception e1) {

			e1.printStackTrace();

		}
		return res;
	}

}

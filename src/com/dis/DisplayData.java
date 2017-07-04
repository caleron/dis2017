package com.dis;

import java.util.*;

/**
 * Created by Kasimir on 04.07.2017.
 */
public class DisplayData {
    public List<CityData> allData;

    public DisplayData(){
        allData = new ArrayList<CityData>();
    }

    public void add(String city, Date date, String article, int amount) {
        boolean cityavailable = false;
        for (CityData cd: allData)
        {
            if(cd.cityName.equals(city))
            {
                cd.add(date,article,amount);
                cityavailable = true;
                break;
            }
        }
        if(!cityavailable && city != null)
        {
            CityData newData = new CityData(city);
            newData.add(date,article,amount);
            allData.add(newData);
        }
    }

    public  List<List<String>> makeOutput(List<String> headers)
    {
        List<List<String>> result = new ArrayList<List<String>>();

        for(CityData cd: allData)
        {
            List<List<String>> cityresults = cd.makeOutput(headers);
            result.addAll(cityresults);
        }
        return result;
    }


}
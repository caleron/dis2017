package com.dis;

import java.util.*;

/**
 * Created by Kasimir on 04.07.2017.
 */
public class DisplayData {
    public List<LocationData> allData;

    public DisplayData(){
        allData = new ArrayList<LocationData>();
    }

    public void add(String city, Date date, String article, int amount) {
        boolean cityavailable = false;
        for (LocationData cd: allData)
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
            LocationData newData = new LocationData(city);
            newData.add(date,article,amount);
            allData.add(newData);
        }
    }

    public  List<List<String>> makeOutput(List<String> headers)
    {
        List<List<String>> result = new ArrayList<List<String>>();

        for(LocationData cd: allData)
        {
            List<List<String>> cityresults = cd.makeOutput(headers);
            result.addAll(cityresults);
        }
        return result;
    }


}
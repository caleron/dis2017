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
        List<String> total = new ArrayList<>();
        total.add("Insgesamt");
        total.add("total");
        for (List<String> list: result) {
            if(list.get(1).equals("total"))
            {
                for(int i = 2; i < list.size(); i++)
                {
                    if(total.size() < list.size())
                    {
                        total.add("0");
                    }
                    int oldvalue = Integer.parseInt(total.get(i));
                    int newvalue = Integer.parseInt(list.get(i));
                    total.set(i,Integer.toString(oldvalue+newvalue));
                    total.set(i,Integer.toString(oldvalue+newvalue));
                }
            }
        }
        result.add(total);
        return result;
    }


}
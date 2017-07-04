package com.dis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kasimir on 04.07.2017.
 */
public class LocationData {
    public String cityName;
    public List<DayValues> tagesDaten;

    public LocationData(String name)
    {
        this.cityName = name;
        tagesDaten = new ArrayList<DayValues>();
    }

    public void add(Date date, String article, int amount) {
        boolean dayavailable = false;
        for(DayValues dv: tagesDaten)
        {
            if(dv.date.equals(date))
            {
                dv.add(article,amount);
                dayavailable = true;
                break;
            }
        }
        if(!dayavailable)
        {
            DayValues newValues = new DayValues(date);
            newValues.add(article,amount);
            tagesDaten.add(newValues);
        }
    }

    public List<List<String>> makeOutput(List<String> headers) {
        List<List<String>> result = new ArrayList<List<String>>();

        List<String> q1 = new ArrayList<String>();
        q1.add(cityName);
        q1.add("Q1");

        List<String> q2 = new ArrayList<String>();
        q2.add(cityName);
        q2.add("Q2");

        List<String> q3 = new ArrayList<String>();
        q3.add(cityName);
        q3.add("Q3");

        List<String> q4 = new ArrayList<String>();
        q4.add(cityName);
        q4.add("Q4");

        for(DayValues dv: tagesDaten)
        {
            List<String> dvlist = dv.makeOutput(headers);
            //dvlist enthält nicht den cityname. der muss vorner eingefügt werden.
            if(dvlist.size() == headers.size()-2)
            {
                switch (dv.getQuartal())
                {
                    case 1: addto(dvlist, q1); break;
                    case 2: addto(dvlist, q2); break;
                    case 3: addto(dvlist, q3); break;
                    case 4: addto(dvlist, q4); break;
                }
            }
        }
        if(q1.size()== headers.size())
        result.add(q1);

        if(q2.size() == headers.size())
        result.add(q2);

        //Die sind leer.
        //result.add(q3);
        //result.add(q4);
        return result;
    }

    public void addto(List<String> toadd, List<String> result)
    {
        for(int i = 0; i < toadd.size(); i++)
        {
            int newvalue = Integer.parseInt(toadd.get(i));
            int oldvalue = 0;
            try {
                 oldvalue = Integer.parseInt(result.get(i + 2));
                 result.set(i+2,Integer.toString(oldvalue+newvalue));
            }
            catch (IndexOutOfBoundsException e)
            {
                result.add(Integer.toString(newvalue));
            }

        }
    }
}

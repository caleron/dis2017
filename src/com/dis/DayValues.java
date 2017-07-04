package com.dis;

import java.util.*;

/**
 * Created by Kasimir on 04.07.2017.
 */
public class DayValues {
    public Map<String, Integer> map;
    public Date date;

    public DayValues(Date date) {
        map = new HashMap();
        this.date = date;
    }

    public void add(String article, int amount)
    {
        map.put(article, new Integer(amount));
    }

    public int getQuartal(){
        return date.getMonth() / 3 + 1;
    }

    public List<String> makeOutput(List<String> headers)
    {
        List<String> result = new ArrayList<String>();
        int total = 0;

        for(int i = 2; i < headers.size(); i++)
        {
            String header = headers.get(i);

            if(map.containsKey(header))
            {
                int value = map.get(header);
                result.add(Integer.toString(value));
                total += value;
            }
            else if(!header.equals("total"))
            {
                result.add("0");
            }
        }
        result.add(Integer.toString(total));
        return result;
    }
}

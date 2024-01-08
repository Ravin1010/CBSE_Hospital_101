/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.springRest.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.persistence.AttributeConverter;

/**
 *
 * @author RyderGan
 */
public class IntegerListAttributeConverter implements AttributeConverter<List<Integer>, String> {

    @Override
    public String convertToDatabaseColumn(List<Integer> attribute) {
       if (attribute == null)
        {
            return null;
        }
        StringBuilder str = new StringBuilder();
        for (Integer val : attribute)
        {
            if (str.length() > 0)
            {
                str.append(",");
            }
            str.append(val);
        }
        return str.toString();
    }

    @Override
    public List<Integer> convertToEntityAttribute(String dbData) {
        if(dbData == null){
            return null;
        }
        List<Integer> list = new ArrayList<>();
        StringTokenizer tokeniser = new StringTokenizer(dbData, ",");
        while (tokeniser.hasMoreElements()) {
            list.add(Integer.valueOf(tokeniser.nextToken()));
        }
        return list;
    }

}

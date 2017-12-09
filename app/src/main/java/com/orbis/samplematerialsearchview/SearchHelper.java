package com.orbis.samplematerialsearchview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos Leonardo Camilo Vargas Huamán on 12/7/17.
 */

public class SearchHelper {

    private List<Object> objectList = new ArrayList<>();

    public List<Object> getAlarmEntities() {
        return objectList;
    }

    public void setAlarmEntities(List<Object> objectList) {
        this.objectList = objectList;
    }

    public List<AlarmEntity> findAlarmhByName(String searchQuery) {
        List<AlarmEntity> results = new ArrayList<>();
        for (Object object : objectList) {
            if (object instanceof AlarmEntity) {
                AlarmEntity alarmEntity = (AlarmEntity) object;
                String normalizedLastSearch = alarmEntity.getMessage().toLowerCase();
                String normalizedSearchQuery = searchQuery.toLowerCase();

                if (normalizedLastSearch.contains(normalizedSearchQuery)) {
                    results.add(alarmEntity);
                }
            }

        }
        return results;
    }
}

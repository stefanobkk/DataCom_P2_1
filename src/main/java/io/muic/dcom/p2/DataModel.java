package io.muic.dcom.p2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DataModel {
    public static class ParcelObserved {
        private String parcelId;
        private String stationId;
        private long timeStamp;

        ParcelObserved(String parcelId_, String stationId_, long ts_) {
            this.parcelId = parcelId_;
            this.stationId = stationId_;
            this.timeStamp = ts_;
        }

        public String getParcelId() {
            return parcelId;
        }

        public String getStationId() {
            return stationId;
        }

        public long getTimeStamp() {
            return timeStamp;
        }
    }

    private List<ParcelObserved> transactions;
    private ConcurrentHashMap<String, List<ParcelObserved>> trail_hash;
    private ConcurrentHashMap<String, AtomicInteger> station_hash;


    DataModel() {
        transactions = new ArrayList<>();
        trail_hash = new ConcurrentHashMap<>();
        station_hash = new ConcurrentHashMap<>();
    }

    public synchronized void postObserve(String parcelId, String stationId, long timestamp) {
        ParcelObserved parcelObserved = new ParcelObserved(parcelId, stationId, timestamp);
        //transactions.add(parcelObserved);

        if (trail_hash.containsKey(parcelId) == false) {
            ArrayList<ParcelObserved> list1 = new ArrayList<ParcelObserved>();
            list1.add(parcelObserved);
            trail_hash.put(parcelId, list1);
        } else {
            List<ParcelObserved> a = trail_hash.get(parcelId);
            a.add(parcelObserved);
            trail_hash.put(parcelId, a);
        }
        if (station_hash.containsKey(stationId) == false) {
            AtomicInteger counter = new AtomicInteger(1);
            station_hash.put(stationId, counter);
        } else {
            station_hash.get(stationId).getAndAdd(1);
        }


    }

    public List<ParcelObserved> getParcelTrail(String parcelId) {

        if (trail_hash.containsKey(parcelId)) {
            return trail_hash.get(parcelId).stream()
                    .collect(Collectors.toList());
        } else{
            return Collections.EMPTY_LIST;

        }


    }




    public long getStopCount(String stationId) {
//        return transactions.stream()
//                .filter(observeEvent -> observeEvent.stationId.equals(stationId))
//                .count();
        return station_hash.get(stationId).longValue();
    }


}

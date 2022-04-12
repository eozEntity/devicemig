package com.notification.devicemig.repository;

import com.notification.devicemig.date.Date;
import com.notification.devicemig.model.entity.CarIdBrand;
import com.notification.devicemig.model.entity.DeviceMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface DevicemigRepository extends JpaRepository<DeviceMapping, String> {

//    insert into DEVICE_MAPPING (DEVICE_ID, CAR_ID, CCID, RGST_DTM, NAD_ID, MDFY_DTM, VIN)
//    values('d_2', 'c_2','cc', now(), 'n_2', now(), 'v_2');

    @Query("select min(d.createdAt) from deviceMapping d")
    LocalDateTime getOldestDate();


    @Query(nativeQuery = true,
            value = "SELECT DISTINCT a.carId, a.brand from " +
                    "        (SELECT distinct d.car_id as carId," +
                    "               substring( d.ccid,  position('_', d.ccid)+1 ,3) as brand," +
                    "          FROM device_mapping d " +
                    "         WHERE rgst_dtm between to_date(:#{#Date.begin}, 'yyyymmddhh24miss') AND to_date(:#{#Date.end}, 'yyyymmddhh24miss') " +
                    "           AND length(d.ccid) > 10 " +
                    "           ORDER BY d.car_id ) a ")
    List<CarIdBrand> getDeviceMappings(@Param("Date") Date date);



    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "DELETE FROM DEVICE_MAPPING WHERE car_id in :carIds")
    void delete(@Param("carIds") List<String> carIds);




//



}

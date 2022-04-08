package com.notification.devicemig.repository;

import com.notification.devicemig.entity.DeviceMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DevicemigRepository extends JpaRepository<DeviceMapping, String> {

//    insert into DEVICE_MAPPING (DEVICE_ID, CAR_ID, CCID, RGST_DTM, NAD_ID, MDFY_DTM, VIN)
//    values('d_2', 'c_2','cc', now(), 'n_2', now(), 'v_2');

    @Query("select min(d.createdAt) from deviceMapping d")
    LocalDateTime getOldestDate();


    @Query(nativeQuery = true,
            value = "SELECT distinct d.car_id as carId," +
                    "               d.ccid as ccId," +
                    "               d.device_id as deviceId," +
                    "               d.nad_id as nadId," +
                    "               d.vin as vin," +
                    "               d.rgst_dtm as createdAt," +
                    "               d.mdfy_dtm as updatedAt " +
                    "FROM device_mapping d " +
                    "WHERE rgst_dtm between to_date(:begin, 'yyyymmddhh24miss') and to_date(:end, 'yyyymmddhh24miss')")
    List<DeviceMapping> getDeviceMappings(@Param("begin") String begin, @Param("end") String end);



}

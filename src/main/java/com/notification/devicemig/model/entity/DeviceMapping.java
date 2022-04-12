package com.notification.devicemig.model.entity;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Builder
@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "deviceMapping")
@Table(name="DEVICE_MAPPING")
public class DeviceMapping {

    @Id
    @Column(nullable = false)
    private String deviceId;

    @Column(name="ccid")
    private String ccId;

    @Column
    private String carId;

    @Column
    private String vin;

    @Column
    private String nadId;

    @Column(name="rgst_dtm", updatable = false)
    private LocalDateTime createdAt;

    @Column(name="mdfy_dtm")
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @Transient
    private String brand;

    public String getBrand(){
        return ccId != null ? ccId.substring( ccId.length() -3 ) : "";
    }

}

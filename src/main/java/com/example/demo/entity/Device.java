package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xioka
 * @since 2022-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String deviceName;

    private String mechanism;

    private String deviceGroup;

    private String mac;

    private String snCode;

    private String ip;

    private String plan;

    private String resolution;

    private Integer state;

    private LocalDateTime startDate;

    private LocalDateTime deadDate;


}

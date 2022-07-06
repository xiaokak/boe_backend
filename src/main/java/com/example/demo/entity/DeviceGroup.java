package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author anonymous
 * @since 2022-07-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeviceGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String groupName;

    private Integer deviceCount;

    private String mechanism;

    private String description;


}

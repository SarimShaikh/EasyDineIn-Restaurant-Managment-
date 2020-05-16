package com.easydinein.easy_dinein.supplementarydtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Shahzeb on 05/08/2018.
 */
@Getter
@Setter
@NoArgsConstructor
public class AddOrderResponseDto {
    private String minsToPrepare;
    private String orderStatus;
    private Long foodOrderId;
    private String status;
    private String message;
    private String responseCode;
}

package com.easydinein.easy_dinein.supplementarydtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginResponseDto {
   private String authkey;
   private String status;
   private String message;
   private String responsecode;
}

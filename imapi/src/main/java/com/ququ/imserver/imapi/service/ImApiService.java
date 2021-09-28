package com.ququ.imserver.imapi.service;

import java.util.Map;

public interface ImApiService {

    Map<String,Object> registerUser(String imUid,
                                    String name,
                                    String iconUrl,
                                    Integer sex,
                                    String mobile,
                                    String extend);

    Map<String,Object> updateUser(String imUid,
                                  String name,
                                  String iconUrl,
                                  Integer sex,
                                  String mobile,
                                  String email,
                                  String birth,
                                  String personSign,
                                  String extend);

}

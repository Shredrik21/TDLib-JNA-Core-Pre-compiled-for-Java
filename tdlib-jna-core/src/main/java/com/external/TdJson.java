package com.external;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface TdJson extends Library {

    TdJson INSTANCE = Native.load("tdjson", TdJson.class);

    Pointer td_json_client_create();
    void td_json_client_send(Pointer client, String query);
    String td_json_client_receive(Pointer client, double timeout);
    String td_json_client_execute(Pointer client, String query);
    void td_json_client_destroy(Pointer client);
}
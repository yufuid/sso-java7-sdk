package com.yufu.idaas.sdk.bouncycastle.asn1;

/**
 * Created by mac on 2017/1/17.
 */

public interface BERTags {
    int BOOLEAN = 1;
    int INTEGER = 2;
    int BIT_STRING = 3;
    int OCTET_STRING = 4;
    int NULL = 5;
    int OBJECT_IDENTIFIER = 6;
    int EXTERNAL = 8;
    int ENUMERATED = 10;
    int SEQUENCE = 16;
    int SEQUENCE_OF = 16;
    int SET = 17;
    int SET_OF = 17;
    int NUMERIC_STRING = 18;
    int PRINTABLE_STRING = 19;
    int T61_STRING = 20;
    int VIDEOTEX_STRING = 21;
    int IA5_STRING = 22;
    int UTC_TIME = 23;
    int GENERALIZED_TIME = 24;
    int GRAPHIC_STRING = 25;
    int VISIBLE_STRING = 26;
    int GENERAL_STRING = 27;
    int UNIVERSAL_STRING = 28;
    int BMP_STRING = 30;
    int UTF8_STRING = 12;
    int CONSTRUCTED = 32;
    int APPLICATION = 64;
    int TAGGED = 128;
}
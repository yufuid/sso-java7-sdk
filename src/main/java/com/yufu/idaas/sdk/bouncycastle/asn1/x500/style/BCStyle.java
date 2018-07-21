package com.yufu.idaas.sdk.bouncycastle.asn1.x500.style;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;
import com.yufu.idaas.sdk.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.yufu.idaas.sdk.bouncycastle.asn1.x500.RDN;
import com.yufu.idaas.sdk.bouncycastle.asn1.x500.X500Name;
import com.yufu.idaas.sdk.bouncycastle.asn1.x500.X500NameStyle;
import com.yufu.idaas.sdk.bouncycastle.asn1.x509.X509ObjectIdentifiers;

import java.util.Hashtable;

/**
 * Created by mac on 2017/1/18.
 */
public class BCStyle extends AbstractX500NameStyle {
    public static final ASN1ObjectIdentifier C = (new ASN1ObjectIdentifier("2.5.4.6")).intern();
    public static final ASN1ObjectIdentifier O = (new ASN1ObjectIdentifier("2.5.4.10")).intern();
    public static final ASN1ObjectIdentifier OU = (new ASN1ObjectIdentifier("2.5.4.11")).intern();
    public static final ASN1ObjectIdentifier T = (new ASN1ObjectIdentifier("2.5.4.12")).intern();
    public static final ASN1ObjectIdentifier CN = (new ASN1ObjectIdentifier("2.5.4.3")).intern();
    public static final ASN1ObjectIdentifier SN = (new ASN1ObjectIdentifier("2.5.4.5")).intern();
    public static final ASN1ObjectIdentifier STREET = (new ASN1ObjectIdentifier("2.5.4.9")).intern();
    public static final ASN1ObjectIdentifier SERIALNUMBER;
    public static final ASN1ObjectIdentifier L;
    public static final ASN1ObjectIdentifier ST;
    public static final ASN1ObjectIdentifier SURNAME;
    public static final ASN1ObjectIdentifier GIVENNAME;
    public static final ASN1ObjectIdentifier INITIALS;
    public static final ASN1ObjectIdentifier GENERATION;
    public static final ASN1ObjectIdentifier UNIQUE_IDENTIFIER;
    public static final ASN1ObjectIdentifier BUSINESS_CATEGORY;
    public static final ASN1ObjectIdentifier POSTAL_CODE;
    public static final ASN1ObjectIdentifier DN_QUALIFIER;
    public static final ASN1ObjectIdentifier PSEUDONYM;
    public static final ASN1ObjectIdentifier DATE_OF_BIRTH;
    public static final ASN1ObjectIdentifier PLACE_OF_BIRTH;
    public static final ASN1ObjectIdentifier GENDER;
    public static final ASN1ObjectIdentifier COUNTRY_OF_CITIZENSHIP;
    public static final ASN1ObjectIdentifier COUNTRY_OF_RESIDENCE;
    public static final ASN1ObjectIdentifier NAME_AT_BIRTH;
    public static final ASN1ObjectIdentifier POSTAL_ADDRESS;
    public static final ASN1ObjectIdentifier DMD_NAME;
    public static final ASN1ObjectIdentifier TELEPHONE_NUMBER;
    public static final ASN1ObjectIdentifier NAME;
    public static final ASN1ObjectIdentifier EmailAddress;
    public static final ASN1ObjectIdentifier UnstructuredName;
    public static final ASN1ObjectIdentifier UnstructuredAddress;
    public static final ASN1ObjectIdentifier E;
    public static final ASN1ObjectIdentifier DC;
    public static final ASN1ObjectIdentifier UID;
    private static final Hashtable DefaultSymbols;
    private static final Hashtable DefaultLookUp;
    public static final X500NameStyle INSTANCE;
    protected final Hashtable defaultLookUp;
    protected final Hashtable defaultSymbols;

    protected BCStyle() {
        this.defaultSymbols = copyHashTable(DefaultSymbols);
        this.defaultLookUp = copyHashTable(DefaultLookUp);
    }

    protected ASN1Encodable encodeStringValue(ASN1ObjectIdentifier var1, String var2) {
        return (ASN1Encodable)(!var1.equals(EmailAddress) && !var1.equals(DC)?(var1.equals(DATE_OF_BIRTH)?new ASN1GeneralizedTime(var2):(!var1.equals(C) && !var1.equals(SN) && !var1.equals(DN_QUALIFIER) && !var1.equals(TELEPHONE_NUMBER)?super.encodeStringValue(var1, var2):new DERPrintableString(var2))):new DERIA5String(var2));
    }

    public String oidToDisplayName(ASN1ObjectIdentifier var1) {
        return (String)DefaultSymbols.get(var1);
    }

    public String[] oidToAttrNames(ASN1ObjectIdentifier var1) {
        return IETFUtils.findAttrNamesForOID(var1, this.defaultLookUp);
    }

    public ASN1ObjectIdentifier attrNameToOID(String var1) {
        return IETFUtils.decodeAttrName(var1, this.defaultLookUp);
    }

    public RDN[] fromString(String var1) {
        return IETFUtils.rDNsFromString(var1, this);
    }

    public String toString(X500Name var1) {
        StringBuffer var2 = new StringBuffer();
        boolean var3 = true;
        RDN[] var4 = var1.getRDNs();

        for(int var5 = 0; var5 < var4.length; ++var5) {
            if(var3) {
                var3 = false;
            } else {
                var2.append(',');
            }

            IETFUtils.appendRDN(var2, var4[var5], this.defaultSymbols);
        }

        return var2.toString();
    }

    static {
        SERIALNUMBER = SN;
        L = (new ASN1ObjectIdentifier("2.5.4.7")).intern();
        ST = (new ASN1ObjectIdentifier("2.5.4.8")).intern();
        SURNAME = (new ASN1ObjectIdentifier("2.5.4.4")).intern();
        GIVENNAME = (new ASN1ObjectIdentifier("2.5.4.42")).intern();
        INITIALS = (new ASN1ObjectIdentifier("2.5.4.43")).intern();
        GENERATION = (new ASN1ObjectIdentifier("2.5.4.44")).intern();
        UNIQUE_IDENTIFIER = (new ASN1ObjectIdentifier("2.5.4.45")).intern();
        BUSINESS_CATEGORY = (new ASN1ObjectIdentifier("2.5.4.15")).intern();
        POSTAL_CODE = (new ASN1ObjectIdentifier("2.5.4.17")).intern();
        DN_QUALIFIER = (new ASN1ObjectIdentifier("2.5.4.46")).intern();
        PSEUDONYM = (new ASN1ObjectIdentifier("2.5.4.65")).intern();
        DATE_OF_BIRTH = (new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.1")).intern();
        PLACE_OF_BIRTH = (new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.2")).intern();
        GENDER = (new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.3")).intern();
        COUNTRY_OF_CITIZENSHIP = (new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.4")).intern();
        COUNTRY_OF_RESIDENCE = (new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.5")).intern();
        NAME_AT_BIRTH = (new ASN1ObjectIdentifier("1.3.36.8.3.14")).intern();
        POSTAL_ADDRESS = (new ASN1ObjectIdentifier("2.5.4.16")).intern();
        DMD_NAME = (new ASN1ObjectIdentifier("2.5.4.54")).intern();
        TELEPHONE_NUMBER = X509ObjectIdentifiers.id_at_telephoneNumber;
        NAME = X509ObjectIdentifiers.id_at_name;
        EmailAddress = PKCSObjectIdentifiers.pkcs_9_at_emailAddress;
        UnstructuredName = PKCSObjectIdentifiers.pkcs_9_at_unstructuredName;
        UnstructuredAddress = PKCSObjectIdentifiers.pkcs_9_at_unstructuredAddress;
        E = EmailAddress;
        DC = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25");
        UID = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1");
        DefaultSymbols = new Hashtable();
        DefaultLookUp = new Hashtable();
        DefaultSymbols.put(C, "C");
        DefaultSymbols.put(O, "O");
        DefaultSymbols.put(T, "T");
        DefaultSymbols.put(OU, "OU");
        DefaultSymbols.put(CN, "CN");
        DefaultSymbols.put(L, "L");
        DefaultSymbols.put(ST, "ST");
        DefaultSymbols.put(SN, "SERIALNUMBER");
        DefaultSymbols.put(EmailAddress, "E");
        DefaultSymbols.put(DC, "DC");
        DefaultSymbols.put(UID, "UID");
        DefaultSymbols.put(STREET, "STREET");
        DefaultSymbols.put(SURNAME, "SURNAME");
        DefaultSymbols.put(GIVENNAME, "GIVENNAME");
        DefaultSymbols.put(INITIALS, "INITIALS");
        DefaultSymbols.put(GENERATION, "GENERATION");
        DefaultSymbols.put(UnstructuredAddress, "unstructuredAddress");
        DefaultSymbols.put(UnstructuredName, "unstructuredName");
        DefaultSymbols.put(UNIQUE_IDENTIFIER, "UniqueIdentifier");
        DefaultSymbols.put(DN_QUALIFIER, "DN");
        DefaultSymbols.put(PSEUDONYM, "Pseudonym");
        DefaultSymbols.put(POSTAL_ADDRESS, "PostalAddress");
        DefaultSymbols.put(NAME_AT_BIRTH, "NameAtBirth");
        DefaultSymbols.put(COUNTRY_OF_CITIZENSHIP, "CountryOfCitizenship");
        DefaultSymbols.put(COUNTRY_OF_RESIDENCE, "CountryOfResidence");
        DefaultSymbols.put(GENDER, "Gender");
        DefaultSymbols.put(PLACE_OF_BIRTH, "PlaceOfBirth");
        DefaultSymbols.put(DATE_OF_BIRTH, "DateOfBirth");
        DefaultSymbols.put(POSTAL_CODE, "PostalCode");
        DefaultSymbols.put(BUSINESS_CATEGORY, "BusinessCategory");
        DefaultSymbols.put(TELEPHONE_NUMBER, "TelephoneNumber");
        DefaultSymbols.put(NAME, "Name");
        DefaultLookUp.put("c", C);
        DefaultLookUp.put("o", O);
        DefaultLookUp.put("t", T);
        DefaultLookUp.put("ou", OU);
        DefaultLookUp.put("cn", CN);
        DefaultLookUp.put("l", L);
        DefaultLookUp.put("st", ST);
        DefaultLookUp.put("sn", SN);
        DefaultLookUp.put("serialnumber", SN);
        DefaultLookUp.put("street", STREET);
        DefaultLookUp.put("emailaddress", E);
        DefaultLookUp.put("dc", DC);
        DefaultLookUp.put("e", E);
        DefaultLookUp.put("uid", UID);
        DefaultLookUp.put("surname", SURNAME);
        DefaultLookUp.put("givenname", GIVENNAME);
        DefaultLookUp.put("initials", INITIALS);
        DefaultLookUp.put("generation", GENERATION);
        DefaultLookUp.put("unstructuredaddress", UnstructuredAddress);
        DefaultLookUp.put("unstructuredname", UnstructuredName);
        DefaultLookUp.put("uniqueidentifier", UNIQUE_IDENTIFIER);
        DefaultLookUp.put("dn", DN_QUALIFIER);
        DefaultLookUp.put("pseudonym", PSEUDONYM);
        DefaultLookUp.put("postaladdress", POSTAL_ADDRESS);
        DefaultLookUp.put("nameofbirth", NAME_AT_BIRTH);
        DefaultLookUp.put("countryofcitizenship", COUNTRY_OF_CITIZENSHIP);
        DefaultLookUp.put("countryofresidence", COUNTRY_OF_RESIDENCE);
        DefaultLookUp.put("gender", GENDER);
        DefaultLookUp.put("placeofbirth", PLACE_OF_BIRTH);
        DefaultLookUp.put("dateofbirth", DATE_OF_BIRTH);
        DefaultLookUp.put("postalcode", POSTAL_CODE);
        DefaultLookUp.put("businesscategory", BUSINESS_CATEGORY);
        DefaultLookUp.put("telephonenumber", TELEPHONE_NUMBER);
        DefaultLookUp.put("name", NAME);
        INSTANCE = new BCStyle();
    }
}

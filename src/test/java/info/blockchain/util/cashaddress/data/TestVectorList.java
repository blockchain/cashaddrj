package info.blockchain.util.cashaddress.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE)
public class TestVectorList {

    @JsonProperty("cases")
    public ArrayList<TestVector> cases;

    public static TestVectorList getValidBech32() throws IOException {
        return toList(validBech32);
    }

    public static TestVectorList getInvalidAddress() throws IOException {
        return toList(invalidAddress);
    }

    public static TestVectorList getInvalidAddress2() throws IOException {
        return toList(invalidAddress2);
    }

    public static TestVectorList toList(String vectors) throws IOException {
        return new ObjectMapper().readValue(vectors, TestVectorList.class);
    }

    public static final String validBech32 = "{\n"
        + "\t\"cases\": [{\n"
        + "\t\t\t\"string\": \"bitcoincash:qpm2qsznhks23z7629mms6s4cwef74vcwvy22gdx6a\",\n"
        + "\t\t\t\"prefix\": \"bitcoincash\",\n"
        + "\t\t\t\"type\": \"pubkeyhash\",\n"
        + "\t\t\t\"hex\": \"0076a04053bda0a88bda5177b86a15c3b29f559873\",\n"
        + "\t\t\t\"words\": [0, 1, 27, 10, 0, 16, 2, 19, 23, 22, 16, 10, 17, 2, 30, 26, 10, 5, 27, 27, 16, 26, 16, 21, 24, 14, 25, 9, 30, 21, 12, 24, 14, 12]\n"
        + "\t\t},\n"
        + "\t\t{\n"
        + "\t\t\t\"string\": \"bchtest:ppm2qsznhks23z7629mms6s4cwef74vcwvhanqgjxu\",\n"
        + "\t\t\t\"prefix\": \"bchtest\",\n"
        + "\t\t\t\"type\": \"scripthash\",\n"
        + "\t\t\t\"hex\": \"0876a04053bda0a88bda5177b86a15c3b29f559873\",\n"
        + "\t\t\t\"words\": [1, 1, 27, 10, 0, 16, 2, 19, 23, 22, 16, 10, 17, 2, 30, 26, 10, 5, 27, 27, 16, 26, 16, 21, 24, 14, 25, 9, 30, 21, 12, 24, 14, 12]\n"
        + "\t\t},\n"
        + "\t\t{\n"
        + "\t\t\t\"string\": \"bchreg:pqzg22ty3m437frzk4y0gvvyqj02jpfv7udqugqkne\",\n"
        + "\t\t\t\"prefix\": \"bchreg\",\n"
        + "\t\t\t\"type\": \"scripthash\",\n"
        + "\t\t\t\"hex\": \"08048529648eeb1f2462b548f43184049ea9052cf7\",\n"
        + "\t\t\t\"words\": [1, 0, 2, 8, 10, 10, 11, 4, 17, 27, 21, 17, 30, 9, 3, 2, 22, 21, 4, 15, 8, 12, 12, 4, 0, 18, 15, 10, 18, 1, 9, 12, 30, 28]\n"
        + "\t\t},\n"
        + "\t\t{\n"
        + "\t\t\t\"string\": \"bitcoincash:qr95sy3j9xwd2ap32xkykttr4cvcu7as4y0qverfuy\",\n"
        + "\t\t\t\"prefix\": \"bitcoincash\",\n"
        + "\t\t\t\"type\": \"pubkeyhash\",\n"
        + "\t\t\t\"hex\": \"00cb481232299cd5743151ac4b2d63ae198e7bb0a9\",\n"
        + "\t\t\t\"words\": [0, 3, 5, 20, 16, 4, 17, 18, 5, 6, 14, 13, 10, 29, 1, 17, 10, 6, 22, 4, 22, 11, 11, 3, 21, 24, 12, 24, 28, 30, 29, 16, 21, 4]\n"
        + "\t\t},\n"
        + "\t\t{\n"
        + "\t\t\t\"string\": \"bitcoincash:qqq3728yw0y47sqn6l2na30mcw6zm78dzqre909m2r\",\n"
        + "\t\t\t\"prefix\": \"bitcoincash\",\n"
        + "\t\t\t\"type\": \"pubkeyhash\",\n"
        + "\t\t\t\"hex\": \"00011f28e473c95f4013d7d53ec5fbc3b42df8ed10\",\n"
        + "\t\t\t\"words\": [0, 0, 0, 17, 30, 10, 7, 4, 14, 15, 4, 21, 30, 16, 0, 19, 26, 31, 10, 19, 29, 17, 15, 27, 24, 14, 26, 2, 27, 30, 7, 13, 2, 0]\n"
        + "\t\t},\n"
        + "\t\t{\n"
        + "\t\t\t\"string\": \"bitcoincash:ppm2qsznhks23z7629mms6s4cwef74vcwvn0h829pq\",\n"
        + "\t\t\t\"prefix\": \"bitcoincash\",\n"
        + "\t\t\t\"type\": \"scripthash\",\n"
        + "\t\t\t\"hex\": \"0876a04053bda0a88bda5177b86a15c3b29f559873\",\n"
        + "\t\t\t\"words\": [1, 1, 27, 10, 0, 16, 2, 19, 23, 22, 16, 10, 17, 2, 30, 26, 10, 5, 27, 27, 16, 26, 16, 21, 24, 14, 25, 9, 30, 21, 12, 24, 14, 12]\n"
        + "\t\t},\n"
        + "\t\t{\n"
        + "\t\t\t\"string\": \"bitcoincash:pr95sy3j9xwd2ap32xkykttr4cvcu7as4yc93ky28e\",\n"
        + "\t\t\t\"prefix\": \"bitcoincash\",\n"
        + "\t\t\t\"type\": \"scripthash\",\n"
        + "\t\t\t\"hex\": \"08cb481232299cd5743151ac4b2d63ae198e7bb0a9\",\n"
        + "\t\t\t\"words\": [1, 3, 5, 20, 16, 4, 17, 18, 5, 6, 14, 13, 10, 29, 1, 17, 10, 6, 22, 4, 22, 11, 11, 3, 21, 24, 12, 24, 28, 30, 29, 16, 21, 4]\n"
        + "\t\t},\n"
        + "\t\t{\n"
        + "\t\t\t\"string\": \"bitcoincash:pqq3728yw0y47sqn6l2na30mcw6zm78dzq5ucqzc37\",\n"
        + "\t\t\t\"prefix\": \"bitcoincash\",\n"
        + "\t\t\t\"type\": \"scripthash\",\n"
        + "\t\t\t\"hex\": \"08011f28e473c95f4013d7d53ec5fbc3b42df8ed10\",\n"
        + "\t\t\t\"words\": [1, 0, 0, 17, 30, 10, 7, 4, 14, 15, 4, 21, 30, 16, 0, 19, 26, 31, 10, 19, 29, 17, 15, 27, 24, 14, 26, 2, 27, 30, 7, 13, 2, 0]\n"
        + "\t\t}\n"
        + "\t]\n"
        + "}";


    public static final String invalidAddress = "{\n"
        + "\t\"cases\": [{\n"
        + "\t\t\t\"string\": \"bitcoincash:qqyq78nf2w\",\n"
        + "\t\t\t\"exception\": \"Hash length does not match version\"\n"
        + "\t\t},\n"
        + "\t\t{\n"
        + "\t\t\t\"string\": \"bitcoincash:a5a8yrhz\",\n"
        + "\t\t\t\"exception\": \"Empty payload in address\"\n"
        + "\t\t},\n"
        + "\t\t{\n"
        + "\t\t\t\"string\": \"bitcoincash:qdpyysjzgfpyysjzgfpyysjzgfpyysjzgg8zlhfxky\",\n"
        + "\t\t\t\"exception\": \"Mismatch between script type and hash length\"\n"
        + "\t\t},\n"
        + "\t\t{\n"
        + "\t\t\t\"string\": \"bitcoincash:yppyysjzgfpyysjzgfpyysjzgfpyysjzggc5ldue0t\",\n"
        + "\t\t\t\"exception\": \"Invalid script type\"\n"
        + "\t\t},\n"
        + "\t\t{\n"
        + "\t\t\t\"string\": \"bitcoincash:lapyysjzgfpyysjzgfpyysjzgfpyysjzggp9pz6yrw\",\n"
        + "\t\t\t\"exception\": \"Invalid version, most significant bit is reserved\"\n"
        + "\t\t}\n"
        + "\t]\n"
        + "}";

    public static final String invalidAddress2 = "{\n"
        + "\t\"cases\": [\n"
        + "      {\n"
        + "        \"string\": \"bitcoincashqpm2qsznhks23z7629mms6s4cwef74vcwvy22gdx6a\",\n"
        + "        \"exception\": \"No separator character for bitcoincashqpm2qsznhks23z7629mms6s4cwef74vcwvy22gdx6a\"\n"
        + "      },\n"
        + "      {\n"
        + "        \"string\": \":qpm2qsznhks23z7629mms6s4cwef74vcwvy22gdx6a\",\n"
        + "        \"exception\": \"Missing prefix for :qpm2qsznhks23z7629mms6s4cwef74vcwvy22gdx6a\"\n"
        + "      },\n"
        + "      {\n"
        + "        \"string\": \"bitcoincash:qpm2qsznhks23z7629mms6s4cwef74vcwvy22gdx6z\",\n"
        + "        \"exception\": \"Invalid checksum for bitcoincash:qpm2qsznhks23z7629mms6s4cwef74vcwvy22gdx6z\"\n"
        + "      },\n"
        + "      {\n"
        + "        \"string\": \"bitcoincash:qpm2qsznhks23z7629mms6s4cwef74vcw1y22gdx6z\",\n"
        + "        \"exception\": \"Unknown character 1\"\n"
        + "      },\n"
        + "      {\n"
        + "        \"string\": \"bitcoincash:gdx6z\",\n"
        + "        \"exception\": \"Data too short\"\n"
        + "      },\n"
        + "      {\n"
        + "        \"string\": \"abxca\",\n"
        + "        \"exception\": \"too short\"\n"
        + "      },\n"
        + "      {\n"
        + "        \"string\": \"abxcaabxcaabxcaabxcaabxcaabxcaabxcaabxcaabxcaabxcaabxcaabxcaabxcaabxcaabxcaabxcaabxcaabxcaabxcaabxcaabxcaabxcaabxcaabxcaabxcaabxca\",\n"
        + "        \"exception\": \"too long\"\n"
        + "      },\n"
        + "      {\n"
        + "        \"string\": \"bitcoincash:pqQ3728yW0y47sqN6l2na30mcW6zm78dZq5ucqzc37\",\n"
        + "        \"exception\": \"Mixed-case string bitcoincash:pqQ3728yW0y47sqN6l2na30mcW6zm78dZq5ucqzc37\"\n"
        + "      }\n"
        + "    ]\n"
        + "}";
}

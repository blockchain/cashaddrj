package info.blockchain.util.cashaddress.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE)
public class TestVector {

    @JsonProperty("string")
    public String str;
    @JsonProperty("prefix")
    public String prefix;
    @JsonProperty("type")
    public String type;
    @JsonProperty("hex")
    public String hex;
    @JsonProperty("words")
    public byte[] words;
    @JsonProperty("exception")
    public String exception;
}

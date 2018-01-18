package info.blockchain.util.cashaddress;

import static org.junit.Assert.*;

import info.blockchain.util.cashaddress.data.TestVector;
import info.blockchain.util.cashaddress.data.TestVectorList;
import org.junit.Test;
import org.spongycastle.util.encoders.Hex;

public class Bech32Test {

    String str = "bitcoincash:qpm2qsznhks23z7629mms6s4cwef74vcwvy22gdx6a";
    String prefix = "bitcoincash";
    String hex = "0076a04053bda0a88bda5177b86a15c3b29f559873";
    byte[] words = {0, 1, 27, 10, 0, 16, 2, 19, 23, 22, 16, 10, 17, 2, 30, 26, 10, 5, 27, 27, 16, 26, 16, 21, 24, 14, 25, 9, 30, 21, 12, 24, 14, 12};

    @Test
    public void fromToWords() throws Exception {

        TestVectorList list = TestVectorList.getValidBech32();
        for (TestVector item : list.cases) {

            byte[] versionAndData = Hex.decode(item.hex);

            // toWords
            byte[] words = Bech32.toWords(versionAndData);
            assertEquals(Hex.toHexString(item.words), Hex.toHexString(words));

            // fromWords
            assertEquals(item.hex, Hex.toHexString(Bech32.fromWords(words)));
        }
    }

    @Test
    public void encodeTest() throws Exception {
        assertEquals(Bech32.encode(prefix, words), str.toLowerCase());
    }

    @Test(expected = AddressFormatException.class)
    public void encodeExceedsBase32LengthTest() throws Exception {

        byte[] words = {14,20,15,7,13,26,0,25,18,6,11,13,8,21,4,20,3,17,2,29,3,14,20,15,7,13,26,0,25,18,6,11,13,8,21,4,20,3,17,2,29,14,20,15,7,13,26,0,25,18,6,11,13,8,21,4,20,3,17,2,29,14,20,15,7,13,26,0,25,18,6,11,13,8,21,4,20,3,17,2,29};
        Bech32.encode(prefix, words);
        fail("Exceeds Base32 maximum length");
    }

    @Test(expected = AddressFormatException.class)
    public void encodeNon5bitWordTest() throws Exception {

        byte[] words = {0, 1, 32, 10, 0, 16, 2, 19, 23, 22, 16, 10, 17, 2, 30, 26, 10, 5, 27, 27, 16, 26, 16, 21, 24, 14, 25, 9, 30, 21, 12, 24, 14, 12};
        Bech32.encode(prefix, words);
        fail("Non 5-bit word");
    }

    @Test
    public void testDecode() throws Exception {

        Bech32 result = Bech32.decode(str);
        assertEquals(result.prefix, prefix);
        assertEquals(Hex.toHexString(result.words), Hex.toHexString(words));
    }

    @Test
    public void testToWords() throws Exception {
        byte[] versionAndData = Hex.decode(hex);
        byte[] toWords = Bech32.toWords(versionAndData);
        assertEquals(Hex.toHexString(toWords), Hex.toHexString(words));
    }

    @Test
    public void testFromWords() throws Exception {

        byte[] fromWords = Bech32.fromWords(words);
        assertEquals(hex, Hex.toHexString(fromWords));
    }

    @Test(expected = AddressFormatException.class)
    public void testExcessPaddingFromWords() throws Exception {

        byte[] words = {14,20,15,7,13,26,0,25,18,6,11,13,8,21,4,20,3,17,2,29,3,0};
        Bech32.fromWords(words);
        fail("Excess padding");
    }

    @Test(expected = AddressFormatException.class)
    public void testNonZeroPaddingFromWords() throws Exception {

        byte[] words = {3,1,17,17,8,15,0,20,24,20,11,6,16,1,5,29,3,4,16,3,6,21,22,26,2,13,22,9,16,21,19,24,25,21,6,18,15,8,13,24,24,24,25,9,12,1,4,16,6,9,17,1};
        Bech32.fromWords(words);
        fail("Non-zero padding");
    }
}

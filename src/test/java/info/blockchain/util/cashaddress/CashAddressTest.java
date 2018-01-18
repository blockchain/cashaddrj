package info.blockchain.util.cashaddress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import info.blockchain.util.cashaddress.data.TestVector;
import info.blockchain.util.cashaddress.data.TestVectorList;
import org.junit.Test;
import org.spongycastle.util.encoders.Hex;

public class CashAddressTest {

    @Test
    public void validVectorTest() throws Exception {

        TestVectorList list = TestVectorList.getValidBech32();
        for (TestVector item : list.cases) {

            // encode
            byte[] versionAndData = Hex.decode(item.hex);
            byte[] data = new byte[versionAndData.length - 1];
            System.arraycopy(versionAndData, 1, data, 0, data.length);
            String encode = CashAddress.encode(item.prefix, item.type, data);

            assertEquals(encode, item.str.toLowerCase());

            // decode
            CashAddress decode = CashAddress.decode(item.str);
            assertEquals(item.prefix, decode.prefix);
            assertEquals(item.type, decode.scriptType);
            assertEquals(item.hex, decode.hash160);
        }
    }

    @Test
    public void decodeInvalidTest() throws Exception {

        TestVectorList list = TestVectorList.getInvalidAddress();
        for (TestVector item : list.cases) {

            try {
                CashAddress.decode(item.str);
                fail("This should not pass");
            }catch (Exception e) {
                assertTrue(true);
            }
        }
    }

    @Test
    public void decodeInvalidScriptTypeTest() throws Exception {

        try {
            byte[] versionAndData = Hex.decode("0076a04053bda0a88bda5177b86a15c3b29f559873");
            byte[] data = new byte[versionAndData.length - 1];
            System.arraycopy(versionAndData, 1, data, 0, data.length);
            CashAddress.encode("bitcoincash", "segwit", data);
            fail("This should not pass");
        }catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void decodeInvalidHashTest() throws Exception {

        try {
            byte[] versionAndData = Hex.decode("asljdhgasljdhfasldjhfaslhjdgasdasd");
            byte[] data = new byte[versionAndData.length - 1];
            System.arraycopy(versionAndData, 1, data, 0, data.length);
            CashAddress.encode("bitcoincash", "pubkeyhash", data);
            fail("This should not pass");
        }catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void decodeMismatchHashTest() throws Exception {

        try {
            byte[] versionAndData = Hex.decode("0076a04053bda0aa15c3b29f559873");
            byte[] data = new byte[versionAndData.length - 1];
            System.arraycopy(versionAndData, 1, data, 0, data.length);
            CashAddress.encode("bitcoincash", CashAddress.P2SH, data);
            fail("This should not pass");
        }catch (Exception e) {
            assertTrue(true);
        }
    }
}

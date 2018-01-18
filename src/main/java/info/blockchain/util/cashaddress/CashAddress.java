package info.blockchain.util.cashaddress;

import com.google.common.collect.ImmutableBiMap;
import java.math.BigInteger;
import org.spongycastle.util.encoders.Hex;

public class CashAddress {

    public static final String P2SH = "scripthash";
    public static final String P2PKH = "pubkeyhash";

    private static final ImmutableBiMap<Integer, BigInteger> hashBitMap =
        new ImmutableBiMap.Builder<Integer, BigInteger>()
            .put(160, BigInteger.valueOf(0))
            .put(192, BigInteger.valueOf(1))
            .put(224, BigInteger.valueOf(2))
            .put(256, BigInteger.valueOf(3))
            .put(320, BigInteger.valueOf(4))
            .put(384, BigInteger.valueOf(5))
            .put(448, BigInteger.valueOf(6))
            .put(512, BigInteger.valueOf(7))
            .build();

    private static final ImmutableBiMap<String, BigInteger> versionBitMap =
        new ImmutableBiMap.Builder<String, BigInteger>()
            .put(P2PKH, BigInteger.valueOf(0))
            .put(P2SH, BigInteger.valueOf(1))
            .build();

    public String scriptType;
    public String prefix;
    public String hash160;

    public CashAddress(String scriptType, String prefix, String hash160) {
        this.scriptType = scriptType;
        this.prefix = prefix;
        this.hash160 = hash160;
    }

    private static BigInteger createVersion(String scriptType, int hashLengthBits) {
        if ((scriptType.equals(P2PKH) || scriptType.equals(P2SH)) && hashLengthBits != 160) {
            throw new AddressFormatException("Invalid hash length for scriptType");
        }

        return versionBitMap.get(scriptType).shiftLeft(3).or(hashBitMap.get(hashLengthBits));
    }

    private static byte[] encodePayload(String scriptType, byte[] hash) {
        int hashLength = hash.length;
        BigInteger version = createVersion(scriptType, hashLength * 8);

        byte[] payload = new byte[hash.length + 1];
        payload[0] = version.byteValue();

        System.arraycopy(hash, 0, payload, 1, payload.length - 1);

        return payload;
    }

    private static VersionPayload decodeVersion(BigInteger version) {
        BigInteger last = version.shiftRight(7);
        if (last.and(BigInteger.ONE).intValue() > 0 || last.compareTo(BigInteger.ZERO) == 1) {
            throw new AddressFormatException("Invalid version, most significant bit is reserved");
        }

        BigInteger versionValue = version.shiftRight(3).and(new BigInteger("0f", 16));
        String scriptType = versionBitMap.inverse().get(versionValue);
        if (scriptType == null) {
            throw new AddressFormatException("Invalid script type");
        }
        // all possible values return
        BigInteger sizeBit = version.and(new BigInteger("07", 16));
        Integer hashSize = hashBitMap.inverse().get(sizeBit);
        if ((scriptType.equals(P2PKH) || scriptType.equals(P2SH)) && hashSize != 160) {
            throw new AddressFormatException("Mismatch between script type and hash length");
        }

        return new VersionPayload(scriptType, hashSize);
    }

    public static String encode(String prefix, String scriptType, byte[] hash160) {

        if (!versionBitMap.containsKey(scriptType)) {
            throw new AddressFormatException("Unsupported script type");
        }

        byte[] words = Bech32.toWords(encodePayload(scriptType, hash160));
        return Bech32.encode(prefix, words);
    }

    public static CashAddress decode(String address) {
        Bech32 result = Bech32.decode(address);

        byte[] data = Bech32.fromWords(result.words);
        if (data.length < 1) {
            throw new AddressFormatException("Empty payload in address");
        }

        VersionPayload versionInfo = decodeVersion(BigInteger.valueOf(data[0]));

        if ((1 + versionInfo.hashSize / 8) != data.length) {
            throw new AddressFormatException("Hash length does not match version");
        }

        byte[] hash = new byte[data.length - 1];
        System.arraycopy(data, 1, hash, 0, hash.length - 1);

        return new CashAddress(
            versionInfo.scriptType,
            result.prefix,
            Hex.toHexString(data)
        );
    }

    public static class VersionPayload {

        public String scriptType;
        public int hashSize;

        public VersionPayload(String scriptType, int hashSize) {
            this.scriptType = scriptType;
            this.hashSize = hashSize;
        }
    }
}

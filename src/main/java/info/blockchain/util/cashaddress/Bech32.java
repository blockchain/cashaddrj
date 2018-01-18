package info.blockchain.util.cashaddress;

import com.google.common.primitives.Bytes;
import java.math.BigInteger;
import java.util.ArrayList;

public class Bech32 {

    private static final String CHARSET = "qpzry9x8gf2tvdw0s3jn54khce6mua7l";
    private static final int CSLEN = 8;

    public String prefix;
    public byte[] words;

    public Bech32(String prefix, byte[] words) {
        this.prefix = prefix;
        this.words = words;
    }

    private static BigInteger polymod (BigInteger pre) {
        BigInteger b = pre.shiftRight(35);
        BigInteger mask = new BigInteger("07ffffffff", 16);

        BigInteger v = pre.and(mask).shiftLeft(5);

        if (b.and(BigInteger.valueOf(1)).intValue() > 0) {
            v = v.xor(new BigInteger("98f2bc8e61", 16));
        }

        if (b.and(BigInteger.valueOf(2)).intValue() > 0) {
            v = v.xor(new BigInteger("79b76d99e2", 16));
        }

        if (b.and(BigInteger.valueOf(4)).intValue() > 0) {
            v = v.xor(new BigInteger("f33e5fb3c4", 16));
        }

        if (b.and(BigInteger.valueOf(8)).intValue() > 0) {
            v = v.xor(new BigInteger("ae2eabe2a8", 16));
        }

        if (b.and(BigInteger.valueOf(16)).intValue() > 0) {
            v = v.xor(new BigInteger("1e4f43e470", 16));
        }

        return v;
    }

    public static Bech32 decode(String str) {
        if (str.length() < 8) {
            throw new AddressFormatException("bech32 input too short");
        }
        if (str.length() > 90) {
            throw new AddressFormatException("bech32 input too long");
        }

        String lowered = str.toLowerCase();
        String uppered = str.toUpperCase();
        if (!str.equals(lowered) && !str.equals(uppered)) {
            throw new AddressFormatException("bech32 cannot mix upper and lower case");
        }

        str = lowered;

        int split = str.lastIndexOf(":");
        if (split < 1) {
            throw new AddressFormatException("bech32 missing separator");
        }
        if (split == 0) {
            throw new AddressFormatException("bech32 missing prefix");
        }

        String prefix = str.substring(0, split);
        String wordChars = str.substring(split + 1);

        if (wordChars.length() < 6) {
            throw new AddressFormatException("bech32 data too short");
        }

        BigInteger chk = prefixChk(prefix);
        ArrayList<BigInteger> words = new ArrayList<>();

        for(int i = 0; i < wordChars.length(); i++) {

            int c = wordChars.charAt(i);
            byte v = (byte) CHARSET.indexOf(c);
            if (CHARSET.indexOf(wordChars.charAt(v)) == -1) {
                throw new AddressFormatException("bech32 characters  out of range");
            }

            chk = polymod(chk).xor(BigInteger.valueOf(v));
            // not in the checksum?
            if (i + CSLEN >= wordChars.length()) {
                continue;
            }

            words.add(BigInteger.valueOf(v));
        }

        if (chk.intValue() != 1) {
            throw new AddressFormatException("invalid bech32 checksum");
        }

        return new Bech32(prefix, Bytes.toArray(words));
    }

    public static String encode(String prefix, byte[] words) {
        // too long?
        if ((prefix.length() + CSLEN + 1 + words.length) > 90) {
            throw new AddressFormatException("Exceeds Bech32 maximum length");
        }

        prefix = prefix.toLowerCase();

        // determine chk mod
        BigInteger chk = prefixChk(prefix);
        String result = prefix + ":";

        for (int i = 0; i < words.length; i++) {
            byte x = words[i];
            if ((x >> 5) != 0) {
                throw new AddressFormatException("Non 5-bit word");
            }

            chk = polymod(chk).xor(BigInteger.valueOf(x));
            result += CHARSET.charAt(x);
        }

        for (int i = 0; i < CSLEN; i ++) {
            chk = polymod(chk);
        }
        chk = chk.xor(BigInteger.valueOf(1));
        for (int i = 0; i < CSLEN; i ++) {
            int pos = 5 * (CSLEN - 1 - i);
            BigInteger v2 = chk.shiftRight(pos).and(new BigInteger("1f", 16));
            result += CHARSET.charAt(v2.intValue());
        }

        return result;
    }

    private static BigInteger prefixChk(String prefix) {
        BigInteger chk = BigInteger.valueOf(1);
        for (int i = 0; i < prefix.length(); i++) {
            BigInteger c = BigInteger.valueOf(Character.codePointAt(prefix, i));
            BigInteger mixwith = c.and(new BigInteger("1f", 16));
            chk = polymod(chk).xor(mixwith);
        }

        chk = polymod(chk);
        return chk;
    }

    public static byte[] convert(byte[] data, int inBits, int outBits, boolean pad) {

        BigInteger value = BigInteger.valueOf(0);
        int bits = 0;
        BigInteger maxV = BigInteger.valueOf((1 << outBits) - 1);
        ArrayList<Byte> result = new ArrayList<>();

        for (int i = 0; i < data.length; i ++) {

            int unsigned = data[i] & 0xFF;

            value = value.shiftLeft(inBits).or(BigInteger.valueOf(unsigned));
            bits += inBits;
            while (bits >= outBits) {
                bits -= outBits;
                result.add(value.shiftRight(bits).and(maxV).byteValue());
            }
        }

        if (pad) {
            if (bits > 0) {
                result.add(value.shiftLeft(outBits - bits).and(maxV).byteValue());
            }
        } else {
            if (bits >= inBits) {
                throw new AddressFormatException("Excess padding");
            }
            if (value.shiftLeft(outBits - bits).and(maxV).intValue() > 0) {
                throw new AddressFormatException("Non-zero padding");
            }
        }

        return Bytes.toArray(result);
    }

    public static byte[] toWords(byte[] bytes) {
        return convert(bytes, 8, 5, true);
    }

    public static byte[] fromWords(byte[] words) {
        return convert(words, 5, 8, false);
    }
}
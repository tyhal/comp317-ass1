import java.io.*;

public class Binarizer {
    private static BufferedWriter bw;
    private long phrase_count = 1;
    private long bits_written = 0;
    private boolean is_first = true;

    public static void main(String[] args) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        bw = new BufferedWriter(new OutputStreamWriter(System.out, "UTF-8"));
        Binarizer binarizer = new Binarizer();

        for (String line = br.readLine(); line != null; line = br.readLine()) {
            String[] tokens = line.split(",", 2); // Limit to 2 for case where value is ','
            long phrase_num = Long.parseLong(tokens[0]);
            char prefix = tokens.length == 2 ? (char) Integer.parseInt(tokens[1], 16) : '\0' /* can be anything */;

            binarizer.binarize(phrase_num, prefix, tokens.length == 2);
        }
        binarizer.padEOF();

        br.close();
        bw.close();
    }

    /**
     * Converts and writes binary number to a binary string of a specified length.
     *
     * @param value    The binary number to convert & write to stdout
     * @param num_bits The length of the binary string.
     * @throws IOException
     */
    private void writeInBinary(long value, int num_bits) throws IOException {
        bits_written += num_bits;
        assert (num_bits != 0 && num_bits <= 8);
        for (int i = num_bits - 1; i >= 0; i--) {
            long bit = (value >> i) & 1;
            bw.write((bit == 1) ? "1" : "0");
        }
    }

    /**
     * Pads necessary bits to the end of the file such that it is a multiple of 8
     * bits so that it can be bit packed.
     *
     * @throws IOException
     */
    public void padEOF() throws IOException {
        int padding = 16 - (int) (bits_written % 8);
        long value = 1 << (padding - 9); // If padding=3 then append "100", if padding 0 then append "10000000"
        writeInBinary(value, padding - 8);
    }

    /**
     * Converts the phrase number and prefix to binary in the most efficient form.
     * Uses 8 bits per symbol/prefix and log2(current_phrase_count) per phrase number.
     *
     * @param phrase_num   The phrase number in the pair to binarize
     * @param prefix       The mismatch character in the pair to binarize.
     * @param has_mismatch This is only used for the last pair to indicate whether there is a mismatch byte
     *                     as there are cases where the symbol exists in the dictionary and there is no
     *                     mismatch.
     * @throws IOException
     */
    public void binarize(long phrase_num, char prefix, boolean has_mismatch) throws IOException {
        int num_bits = Long.SIZE - Long.numberOfLeadingZeros(phrase_count);

        writeInBinary(phrase_num, num_bits);

        if (has_mismatch) {
            writeInBinary(prefix, 8);
            phrase_count++;
        }

        if (phrase_num == 0) {
            phrase_count = 1;
        }

        is_first = false;
    }
}

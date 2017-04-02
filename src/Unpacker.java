import java.io.*;

public class Unpacker {
    private static BufferedWriter bw;
    private static BufferedReader br;

    Unpacker(InputStreamReader in, OutputStreamWriter out) {
        br = new BufferedReader(in);
        bw = new BufferedWriter(out);
    }

    public static void main(String[] args) throws IOException {
        Unpacker unpck = new Unpacker(
                new InputStreamReader(System.in, "UTF-8"),
                new OutputStreamWriter(System.out, "UTF-8"));
        unpck.pack();
    }

    private char[] unpackNspend(int dat) {
        char[] c = new char[8];
        for (int i = 0; i < 8; i++)
            c[7 - i] = (char) ((dat >> (i) & 0x1) + 0x30);
        return c;
    }

    private void pack() throws IOException {
        try {
            int i = 0, c;
            // Read each byte
            while ((c = br.read()) != -1) {
                bw.write(unpackNspend(c), 0, 8);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
            bw.close();
        }
    }
}

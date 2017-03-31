import java.io.*;

public class Packer {
    private static BufferedWriter bw;
    private static BufferedReader br;

    Packer(InputStreamReader in, OutputStreamWriter out) {
        br = new BufferedReader(in);
        bw = new BufferedWriter(out);
    }

    private int packNsave(char[] c)
    {
        int dat = 0;
        for (int i = 0; i <8 ; i++)
            dat += (c[i]&0x1)<<(7-i);
        return dat;
    }

    private void pack() throws IOException
    {
        try {
            int i = 0, c;
            char[] cbuf = new char[8];
            while ((c = br.read(cbuf,0,8)) != -1) {
                assert (c == 8) ;
                bw.write(packNsave(cbuf));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
            bw.close();
        }
    }

    public static void main(String[] args) throws IOException {
        Packer pck =  new Packer(
                new InputStreamReader(System.in, "UTF-8"),
                new OutputStreamWriter(System.out, "UTF-8"));
        pck.pack();
    }
}

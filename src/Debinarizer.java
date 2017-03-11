import java.io.*;


public class Debinarizer {
    private static BufferedWriter bw;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(args[1]));
        bw = new BufferedWriter(new FileWriter(args[0]));

        try {
            int i = 0, c;
            while( ( c = br.read() ) != -1 ) {
                bw.write( c );
            }
        } catch( IOException e ) {
            e.printStackTrace();
        } finally {
            br.close();
            bw.close();
        }

    }
}


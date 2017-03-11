import java.io.*;


public class Binarizer {
    private static BufferedWriter bw;

    public Binarizer() {
    }

    public void binarize(String line) throws IOException {
        bw.write( line + "\n" );
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(args[1]));
        bw = new BufferedWriter(new FileWriter(args[0]));

        Binarizer binarizer = new Binarizer();

        try {
            String line;
            while ((line = br.readLine()) != null) {
                binarizer.binarize(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
            bw.close();
        }

    }
}


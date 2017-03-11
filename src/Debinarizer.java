import java.io.*;


public class Debinarizer {
    private static BufferedWriter bw;
    private long phrase_count = 0;
    public final long reset_phrase_num = 0; // Ensured to work with any dictionary limit

    public void debinarize( int bit ) {
    	
    }
    
    public static void main(String[] args) throws IOException {

    	BufferedReader br = new BufferedReader( new InputStreamReader( System.in, "UTF-8" ) );
    	bw = new BufferedWriter( new OutputStreamWriter( System.out, "UTF-8" ) );

    	Debinarizer debinarizer = new Debinarizer();

        try {
            int bit;
            while( ( bit = br.read() ) != -1 ) {
            	debinarizer.debinarize( bit );
            }
        } catch( IOException e ) {
            e.printStackTrace();
        } finally {
            br.close();
            bw.close();
        }

    }
}


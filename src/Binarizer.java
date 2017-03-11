import java.io.*;

public class Binarizer {
    private static BufferedWriter bw;
    private long phrase_count = 0;
    public final long reset_phrase_num = 0; // Ensured to work with any dictionary limit
    
    private char parseMismatchByte( String token ) {
		return (char)Integer.parseInt( token, 16 );
	}
    
    private String convert( long value, int bits ) {
    	String expression = "%" + Integer.toString( bits ) + "s";
    	return String.format( expression, Long.toBinaryString( value ) ).replace(" ", "0");
    }

    public void binarize(String line) throws IOException {
    	
    	String[] tokens = line.split( ",", 2 ); // Limit to 2 for case where value is ','
    	
    	boolean has_mismatch = tokens.length == 2;
    	long phrase_num = Long.parseLong( tokens[ 0 ] );
    	
    	if( phrase_count != 0 ){
    		// Calculate minimum number of bits needed to represent number
    		int bits = Long.SIZE - Long.numberOfLeadingZeros( phrase_count );
        	bw.write( convert( phrase_num, bits ) );
    	}
    	
    	phrase_count = phrase_num == reset_phrase_num ? 0 : phrase_count + 1;
    	
    	if( has_mismatch ) {
    		char phrase_prefix = parseMismatchByte( tokens[ 1 ] );
    		bw.write( convert( (long)phrase_prefix, 8 ) );
    	}
    }

    public static void main(String[] args) throws IOException {

    	BufferedReader br = new BufferedReader( new InputStreamReader( System.in, "UTF-8" ) );
    	bw = new BufferedWriter( new OutputStreamWriter( System.out, "UTF-8" ) );

        Binarizer binarizer = new Binarizer();
        
        try {
            String line;
    		while( ( line = br.readLine() ) != null ) {
    			binarizer.binarize( line );
    		}
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
            bw.close();
        }

    }
}


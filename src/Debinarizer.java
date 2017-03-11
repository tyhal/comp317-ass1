import java.io.*;


public class Debinarizer {
    private static BufferedWriter bw;
    
    private long phrase_count = 0;
    private long bit_string_count = 0;
    private long phrase_num_length = 0;
    
    private long binary_string = 0;
//    private int consecutive_byte_debinarize_count = 1;
    private boolean debinarize_byte = true;
    
    public final long reset_phrase_num = 0; // Ensured to work with any dictionary limit
    
    private String format( char c ) {
		return Integer.toHexString( (int)c );
	}
    
    public void debinarize( int bit, boolean last ) throws IOException {
    	
    	binary_string ^= bit;
    	bit_string_count++;
    	
//    	System.out.println( bit + " " + phrase_count + " " + Long.toBinaryString(binary_string));
    	
//    	if( consecutive_byte_debinarize_count > 0 ) {
    	if( debinarize_byte ) {
    		if( bit_string_count == 8 ) {
    			if( /*consecutive_byte_debinarize_count == 1 &&*/ phrase_count == 0 ) {
	    			bw.write( Long.toString( 1 ) );
	    		}
    		
	    		bw.write( "," + format( (char)binary_string ) + "\n" ); // Write mismatch byte
	    		
//	    		if( --consecutive_byte_debinarize_count == 0 )
	    		{
	    			phrase_count++;
	    			phrase_num_length = Long.SIZE - Long.numberOfLeadingZeros( phrase_count );
	    		}
	    		
	    		bit_string_count = 0;
	    		binary_string = 0;
	    		debinarize_byte = false;
    		}
    	} else if ( bit_string_count == phrase_num_length ) {
    		bw.write( Long.toString( binary_string ) + ( last ? "\n" : "" ) );
    		debinarize_byte = true;
//    		if( binary_string == reset_phrase_num ) {
//    			phrase_count = 0;
//    			consecutive_byte_debinarize_count = 2;
//    		} else {
//    			consecutive_byte_debinarize_count = 1;
//    		}

    		bit_string_count = 0;
    		binary_string = 0;
    		phrase_num_length = 0;
    	}
    	
    	binary_string <<= 1; 
    }
    
    public static void main(String[] args) throws IOException {

    	BufferedReader br = new BufferedReader( new InputStreamReader( System.in, "UTF-8" ) );
    	bw = new BufferedWriter( new OutputStreamWriter( System.out, "UTF-8" ) );

    	Debinarizer debinarizer = new Debinarizer();

        try {
        	int next_c, c = br.read();
    		for( boolean last = ( c == -1 ); !last; c = next_c ) {
    			last = ( ( next_c = br.read() ) == -1 );
    			debinarizer.debinarize( Character.getNumericValue( (char)c ), last );
    		}

        } catch( IOException e ) {
            e.printStackTrace();
        } finally {
            br.close();
            bw.close();
        }

    }
}

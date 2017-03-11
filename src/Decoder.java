import java.util.*;
import java.io.*;

public class Decoder {
	private static BufferedWriter bw;
	private ArrayList<  Map.Entry< Long, Character > > dictionary;
	public final long reset_phrase_num = 0; // Ensured to work with any dictionary limit
	
	public Decoder() {
		dictionary = new ArrayList< Map.Entry< Long, Character > >();
	}
	
	public void decode( long phrase_num, char c, boolean add, boolean has_mismatch ) throws IOException {
		if( phrase_num == reset_phrase_num ) { // Dictionary full
			dictionary.clear();
		} else if( add ) { // Add each row in encoded file to dictionary
			dictionary.add( new AbstractMap.SimpleEntry< Long, Character >( phrase_num, c ) );
		}
		long phrase_idx = phrase_num - 2; // Subtract 2 since we skip RESET and we don't store a blank element for (0,'')
		if( phrase_idx >= 0 ) { // Phrase exists in dictionary
			Map.Entry< Long, Character > pair = dictionary.get( (int) phrase_idx );
			// Phrase # is > 0 so we need to traverse to the root first and then build up original phrase from there
			decode( pair.getKey(), pair.getValue(), false, true );
		}
		if( has_mismatch ) { // This will only be false in the case where the last pair does not have a value
			bw.write( c );
		}
	}
	
	public char revFormat( String token ) {
		return (char)Integer.parseInt( token, 16 );
	}
	
	public static void main( String[] args ) throws IOException {
    	BufferedReader br = new BufferedReader( new InputStreamReader( System.in, "UTF-8" ) );
    	bw = new BufferedWriter( new FileWriter( args[ 0 ] ) );
    	Decoder decoder = new Decoder();
    	try {
    		String line;
    		while( ( line = br.readLine() ) != null ) {
    			String[] tokens = line.split( ",", 2 ); // Limit to 2 for case where value is ','
    			boolean has_mismatch = tokens.length == 2; // else 1
    			char c = has_mismatch ? decoder.revFormat( tokens[ 1 ] ) : '\0' /*can be anything*/;
    			decoder.decode( Long.parseLong( tokens[ 0 ] ), c, true, has_mismatch );
    		}
		} catch( IOException e ) {
			e.printStackTrace();
		} finally {
    		br.close();
    		bw.close();
    	}
    }
}

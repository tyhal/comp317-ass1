import java.util.*;
import java.io.*;

public class Encoder {
	private static BufferedWriter bw;
	private Trie head, pointer;
	private long phrase_count;
	private long dict_limit;
	private final long reset_phrase_num = 0; // Ensured to work with any dictionary limit

    private class Trie {
		private TreeMap< Character, Trie > phrases;
		private long phrase_num;

		public Trie( long key ) {
			phrase_num = key;
			phrases = new TreeMap< Character, Trie >();
		}

		public boolean phraseMatch( char c ) {
			if( phrases.containsKey( c ) ) {
				pointer = phrases.get( c ); // Traverse phrase
				return true;
			}
			return false; // Wasn't an existing phrase
		}

		public long insert( char c ) {
			pointer.phrases.put( c, new Trie( ++phrase_count ) ); // Add to dictionary and increment phrase count
			return pointer.phrase_num;
		}
	}

	public Encoder( int max_num_bits ) {
		dict_limit = 1 << max_num_bits; // 2 ^ max_num_bits
		resetDictionary();
	}

	private void resetDictionary() {
		phrase_count = 1; // Starts at 1 since 0 is taken by RESET phrase number
		head = new Trie( phrase_count );
		pointer = head;
	}

	private String format( char c ) {
		return Integer.toHexString( (int)c );
	}

	public void encode( char c, boolean last_symbol ) throws IOException {
		boolean in_dict;
		if( phrase_count == dict_limit ) { // Dictionary full
			// Write RESET phrase number followed by mismatch symbol
			bw.write( Long.toString( reset_phrase_num ) + "," + format( c ) + "\n" );
			resetDictionary();
		} else if( !( in_dict = pointer.phraseMatch( c ) ) || last_symbol ) {
			// If the last symbol phrase is already in dictionary then there is no mismatch so don't output any symbol value
			String mismatch_char = ( last_symbol && in_dict ) ? "\n" : "," + format( c ) + "\n";
			long phrase_num = head.insert( c );
			bw.write( Long.toString( phrase_num ) + mismatch_char ); // Write < phrase number, mismatched byte in hex >
			pointer = head; // Go back to start to build up phrase again
		}
	}

    public static void main( String[] args ) throws IOException {
    	BufferedReader br = new BufferedReader( new InputStreamReader( System.in, "UTF-8" ) );
    	bw = new BufferedWriter( new FileWriter( args[ 1 ] ) );
    	Encoder encoder = new Encoder( Integer.parseInt( args[ 0 ] ) );
    	try {
    		int next_c, c = br.read();
    		for( boolean last = ( c == -1 ); !last; c = next_c ) {
    			last = ( ( next_c = br.read() ) == -1 );
    			encoder.encode( (char)c, last );
    		}
		} catch( IOException e ) {
			e.printStackTrace();
		} finally {
    		br.close();
    		bw.close();
    	}
    }
}

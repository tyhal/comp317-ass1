import java.util.*;
import java.io.*;
public class EncoderShort {
    private class Trie {
        public long phrase_num = phrase_count++; // Sets phrase_num and increments phrase count on construction
        public TreeMap< Character, Trie > trie = new TreeMap< Character, Trie >();
    };
    private long phrase_count = 1;
    private Trie dict = new Trie(), dict_ptr = dict;
    public static void main( String[] args ) throws IOException {
    	BufferedReader br = new BufferedReader( new InputStreamReader( System.in, "UTF-8" ) );
        BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out, "UTF-8" ) );
        EncoderShort enc = new EncoderShort();
    	for( int next_c, c = br.read(); c != -1; c = next_c ) {
			boolean last = ( ( next_c = br.read() ) == -1 ), in_dict = enc.dict_ptr.trie.containsKey( (char)c );
			if( in_dict ) {
				enc.dict_ptr = enc.dict_ptr.trie.get( (char)c );
			}
            if( !in_dict || last ) {
                enc.dict_ptr.trie.put( (char)c, enc.new Trie() ); // Add to dictionary
                bw.write( Long.toString( enc.dict_ptr.phrase_num ) + ((last && in_dict) ? "\n" : "," + Integer.toHexString( c ) + "\n" ) );
                enc.dict_ptr = enc.dict; // Reset head back to start
            }
        }
		br.close();
		bw.close();
    }
}
